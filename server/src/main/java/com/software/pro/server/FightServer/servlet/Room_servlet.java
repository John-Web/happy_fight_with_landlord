package com.software.pro.server.FightServer.servlet;

import com.software.pro.server.FightServer.ServerContains;
import com.software.pro.server.FightServer.utils.ServletEncoder;
import com.software_pro.common.entity.WebData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

//只是一个websocket服务的监听入口, 在外部可以使用该类的变量
@Controller
@Component
@ServerEndpoint(value = "/room_page/{room_id}",encoders = {ServletEncoder.class})
@Slf4j
@RequestMapping("/landlord_servlet")
public class Room_servlet {

    public Session session;
    private String room_id="";
    public int player_state = 0;              //00 01 10 11 当房主开始游戏时检查这个值是否为3
    public static Map<String,CopyOnWriteArraySet<Room_servlet>>Rooms_Sessions = new HashMap<>();      //所有房间的连接信息
    public static Map<String,Integer>Rooms_OnLineCount = new HashMap<>();
    public static Map<String,Map<String,String>>Rooms_Operators = new HashMap<>(); //房间号, 操作类型, 操作数
    public static Map<String,Integer>Rooms_Players_Status = new HashMap<>();

    //先群发新玩家加入的消息,然后再群发当前房间人数以更新房间
    @OnOpen
    public void onOpen(Session session, @PathParam("room_id") String room_id){          //加入后第一时间群发自己加入的消息
        this.session = session;
        this.room_id = room_id;

        if(!Rooms_Sessions.containsKey(room_id)){
            CopyOnWriteArraySet<Room_servlet>Room_Sessions = new CopyOnWriteArraySet<>();                //为房间创建安全数组
            Room_Sessions.add(this);
            Rooms_Sessions.put(room_id,Room_Sessions);

            int onlineCount = 1;
            Rooms_OnLineCount.put(room_id,onlineCount);

            int flag = 0;
            Rooms_Players_Status.put(room_id,flag);      //房间刚创建时flag=0表示不能开始游戏 直到00 -> 11
        }
        else{
            CopyOnWriteArraySet<Room_servlet>Room_Sessions = Rooms_Sessions.get(room_id);
            Room_Sessions.add(this);
            Rooms_Sessions.put(room_id,Room_Sessions);
            addOnlineCount(room_id);
        }

        log.info("新玩家加入"+this.room_id+"号房间");
        log.info("你加入房间");

        try {
            Map<String,Object> joinMap = new HashMap<String,Object>();
            joinMap.put("join_result","加入成功");
            sendMessage(joinMap);
        } catch (IOException e) {
            log.error("websocket IO异常");
        }
        System.out.println("房间"+room_id+"连接数: "+Rooms_OnLineCount.get(room_id));

        //取自己加入的消息然后发给别人
        String message = "";
        try {                     //先加入的玩家阻塞等待,也不一定会阻塞多长时间, 几乎是瞬时的
            WebData join_message = ServerContains.Server_Room_Data.get(Integer.valueOf(room_id)).take();
            int join_type = Integer.valueOf(join_message.getValue().toString());
            if (join_type == 1||join_type == 0) {
                Map<String,Object> resultMap = new HashMap<String,Object>();

                WebData join_Id = ServerContains.Server_Room_Data.get(Integer.valueOf(room_id)).take();
                WebData join_Name = ServerContains.Server_Room_Data.get(Integer.valueOf(room_id)).take();
                WebData join_Role = ServerContains.Server_Room_Data.get(Integer.valueOf(room_id)).take();
                message = "ID为: " + join_Id.getValue().toString() + "的玩家 " + join_Name.getValue().toString() + " "
                        + join_Role.getValue().toString() + "加入了房间";
                System.out.println(message);
                resultMap.put("join_Id",join_Id.getValue().toString());
                resultMap.put("join_Name",join_Name.getValue().toString());
                resultMap.put("join_Role",join_Role.getValue().toString());
                resultMap.put("onlineCount",Rooms_OnLineCount.get(room_id));
                resultMap.put("join_type",join_type);

                sendAll(room_id,resultMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //可以和房间里的玩家发消息聊天

    }

    @OnClose
    public void onClose(@PathParam("room_id") String room_id) {
        //从set中删除
        Rooms_Sessions.get(room_id).remove(this);
        //在线数减1
        subOnlineCount(room_id);
        log.info("有玩家退出"+this.room_id+"号房间");
    }
    @OnMessage
    public void onMessage(@PathParam("room_id")String room_id, String message, Session session){
        log.info("收到玩家的操作: " +message);
        for (Room_servlet item : Rooms_Sessions.get(room_id)) {
            if(!item.session.equals(session)) {
                try {
                    item.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void sendMessage(Object message) throws IOException{
        try {
            this.session.getBasicRemote().sendObject(message);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }
    public void sendAll(String room_id, String message) throws IOException{
        for (Room_servlet item : Rooms_Sessions.get(room_id)) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void sendAll(String room_id, Object message) throws IOException{
        for (Room_servlet item : Rooms_Sessions.get(room_id)) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static synchronized int getOnlineCount(String room_id) {
        return Rooms_OnLineCount.get(room_id);
    }
    public static synchronized void addOnlineCount(String room_id) {
        Rooms_OnLineCount.put(room_id,Rooms_OnLineCount.get(room_id)+1);
    }
    public static synchronized void subOnlineCount( String room_id) {
        Rooms_OnLineCount.put(room_id,Rooms_OnLineCount.get(room_id)-1);
    }


    @CrossOrigin
    @RequestMapping(value = "/room_servlet/owner_begingame_click_check",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Integer> gameBeginCheck(@RequestParam("room_id") String room_id){
        int flag = Rooms_Players_Status.get(room_id);
        System.out.println("flag: "+flag);
        Map<String,Object> resultMap = new HashMap<String,Object>();
        if(flag < 3){
            try {
                resultMap.put("game_begin_result","还有玩家尚未准备,请准备后再开始游戏");
                sendAll(room_id, resultMap);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        else{
            //开始游戏
            try {
                resultMap.put("game_begin_result","开始游戏");
                sendAll(room_id,resultMap);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        Map<String,Integer>map = new HashMap<>();
        map.put("state_code", 200);
        return map;
    }

    @CrossOrigin
    @RequestMapping("/room_servlet/player_confirm_disconfirm_click")
    @ResponseBody
    public Map<String,Integer> confirm_disconfirm_click(@RequestParam("room_id")String room_id, @RequestParam("index")int index, @RequestParam("op")int op){
        int flag = Rooms_Players_Status.get(room_id);
        if(index == 1){      //将flag的第index位变成op
            flag = flag&0;
            flag += 1;
            System.out.println(flag);
            Rooms_Players_Status.put(room_id,flag);
        }
        else if(index == 2){
            flag = flag&1;
            flag += 2*op;
            Rooms_Players_Status.put(room_id,flag);
        }
        Map<String,Integer>map = new HashMap<>();
        map.put("state_code", 200);
        return map;
    }
}
