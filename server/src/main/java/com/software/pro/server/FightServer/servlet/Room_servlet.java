package com.software.pro.server.FightServer.servlet;

import com.software.pro.server.FightServer.ServerContains;
import com.software.pro.server.FightServer.servlet.event.RoomEventListener;
import com.software.pro.server.FightServer.utils.ServletEncoder;
import com.software_pro.common.entity.ClientSide;
import com.software_pro.common.entity.Poker;
import com.software_pro.common.entity.Room;
import com.software_pro.common.entity.WebData;
import com.software_pro.common.enums.RoomEventCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

//只是一个websocket服务的监听入口, 在外部可以使用该类的变量
@Controller
@Component
@ServerEndpoint(value = "/room_page/{room_id}/{client_id}",encoders = {ServletEncoder.class})
@Slf4j
@RequestMapping("/landlord_servlet")
public class Room_servlet {

    public Session session;
    private String room_id="";
    private String client_id="";

    //先群发新玩家加入的消息,然后再群发当前房间人数以更新房间
    @OnOpen
    public void onOpen(Session session, @PathParam("room_id") String room_id, @PathParam("client_id") String client_id){          //加入后第一时间群发自己加入的消息
        this.session = session;
        this.room_id = room_id;
        this.client_id = client_id;
        RoomsContains.Clients_Sessions.put(client_id,session);

        if(!RoomsContains.Rooms_Sessions.containsKey(room_id)){
            CopyOnWriteArrayList<Room_servlet>Room_Sessions = new CopyOnWriteArrayList<>();                //为房间创建安全数组
            Room_Sessions.add(this);
            RoomsContains.Rooms_Sessions.put(room_id,Room_Sessions);

            int onlineCount = 1;
            RoomsContains.Rooms_OnLineCount.put(room_id,onlineCount);

            int flag = 0;
            RoomsContains.Rooms_Players_Status.put(room_id,flag);      //房间刚创建时flag=0表示不能开始游戏 直到00 -> 11
        }
        else{
            CopyOnWriteArrayList<Room_servlet>Room_Sessions = RoomsContains.Rooms_Sessions.get(room_id);
            Room_Sessions.add(this);
            RoomsContains.Rooms_Sessions.put(room_id,Room_Sessions);
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
        System.out.println("房间"+room_id+"连接数: "+RoomsContains.Rooms_OnLineCount.get(room_id));

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

                message = join_Role.getValue().toString() + " " +join_Id.getValue().toString() + " " +
                        join_Name.getValue().toString();
                System.out.println(message);
                resultMap.put("join_Id",join_Id.getValue().toString());
                resultMap.put("join_Name",join_Name.getValue().toString());
                resultMap.put("join_Role",join_Role.getValue().toString());
                resultMap.put("onlineCount",RoomsContains.Rooms_OnLineCount.get(room_id));
                resultMap.put("join_type",join_type);
                resultMap.put("join_message",message);

                sendAll(room_id,resultMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(@PathParam("room_id") String room_id, @PathParam("client_id")String client_id) {
        //从map删除
        RoomsContains.Clients_Sessions.remove(client_id);
        //从set中删除
        RoomsContains.Rooms_Sessions.get(room_id).remove(this);
        //在线数减1
        subOnlineCount(room_id);
        log.info("有玩家退出"+this.room_id+"号房间");
    }
    @OnMessage
    public void onMessage(@PathParam("room_id")String room_id, @PathParam("client_id")String client_id,String message, Session session){
        if(message.equalsIgnoreCase("CODE_GAME_LANDLORD_NOT_ELECT")){
            RoomEventListener.get(RoomEventCode.CODE_GAME_LANDLORD_NOT_ELECT).call(Integer.valueOf(client_id),room_id,null);
        }
        else if(message.equalsIgnoreCase("CODE_GAME_LANDLORD_ELECT")){
            RoomEventListener.get(RoomEventCode.CODE_GAME_LANDLORD_ELECT).call(Integer.valueOf(client_id),room_id,null);
        }
        else if(message.equalsIgnoreCase("CODE_GAME_LANDLORD_NOT_GRAB")){
            RoomEventListener.get(RoomEventCode.CODE_GAME_LANDLORD_NOT_GRAB).call(Integer.valueOf(client_id),room_id,null);
        }
        else if(message.equalsIgnoreCase("CODE_GAME_LANDLORD_GRAB")){
            RoomEventListener.get(RoomEventCode.CODE_GAME_LANDLORD_GRAB).call(Integer.valueOf(client_id),room_id,null);
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
    public static void sendAll(String room_id, String message) throws IOException{
        for (Room_servlet item : RoomsContains.Rooms_Sessions.get(room_id)) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void sendAll(String room_id, Object message) throws IOException{
        for (Room_servlet item : RoomsContains.Rooms_Sessions.get(room_id)) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static synchronized int getOnlineCount(String room_id) {
        return RoomsContains.Rooms_OnLineCount.get(room_id);
    }
    public static synchronized void addOnlineCount(String room_id) {
        RoomsContains.Rooms_OnLineCount.put(room_id,RoomsContains.Rooms_OnLineCount.get(room_id)+1);
    }
    public static synchronized void subOnlineCount( String room_id) {
        RoomsContains.Rooms_OnLineCount.put(room_id,RoomsContains.Rooms_OnLineCount.get(room_id)-1);
    }


    @CrossOrigin
    @RequestMapping(value = "/room_servlet/owner_begingame_click_check",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Integer> gameBeginCheck(@RequestParam("room_id") String room_id){
        int flag = RoomsContains.Rooms_Players_Status.get(room_id);
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
                resultMap.put("game_begin_result","游戏开始了");
                sendAll(room_id,resultMap);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            //向全局服务器发送游戏开始的命令
            try{
                BlockingQueue<WebData>room_message = RoomsContains.Rooms_Messages_MAP.get(Integer.valueOf(room_id));
                room_message.put(new WebData("message_form_roomservlet_game_begin",1));
                RoomsContains.Rooms_Messages_MAP.put(Integer.valueOf(room_id),room_message);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            RoomEventListener.get(RoomEventCode.CODE_GAME_STARTING).call(room_id);
        }
        Map<String,Integer>map = new HashMap<>();
        map.put("state_code", 200);
        return map;
    }

    @CrossOrigin
    @RequestMapping("/room_servlet/player_confirm_disconfirm_click")
    @ResponseBody
    public Map<String,Integer> confirm_disconfirm_click(@RequestParam("room_id")String room_id, @RequestParam("index")int index, @RequestParam("op")int op){
        int flag = RoomsContains.Rooms_Players_Status.get(room_id);
        flag = flag&(index - 1);
        flag += index * op;
        RoomsContains.Rooms_Players_Status.put(room_id,flag);

        Map<String,Integer>map = new HashMap<>();
        map.put("state_code", 200);
        return map;
    }

    public static void gameStart(String room_id){
        show_pokers(room_id);
        landlord_elect(room_id);
    }
    public static void show_pokers(String room_id){
        List<List<Poker>>room_pokers = new ArrayList<>();
        for (ClientSide clientSide : RoomsContains.Rooms_ClientSides.get(room_id)){
            room_pokers.add(clientSide.getPokers());
        }
        List<Poker>landlord_pokers = ServerContains.getRoomMap().get(Integer.valueOf(room_id)).getLandlordPokers();
        CopyOnWriteArrayList<Room_servlet>room_sessions = RoomsContains.Rooms_Sessions.get(room_id);
        //推牌以 顺时针旋转
        for(int i=0; i<3 ;i++){
            Map<String,Object> roompokers_Map = new HashMap<String,Object>();
            roompokers_Map.put("your_pokers",room_pokers.get(i));
            roompokers_Map.put("your_left_pokers",room_pokers.get((i+1)%3));
            roompokers_Map.put("your_right_pokers",room_pokers.get((i+2)%3));
            roompokers_Map.put("landlord_pokers",landlord_pokers);
            //发牌
            try {
                room_sessions.get(i).sendMessage(roompokers_Map);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public static void landlord_elect(String room_id){
        Room room = RoomsContains.Rooms.get(room_id);
        //幸运玩家先叫地主
        int currentsellplayerId = room.getCurrentSellClient();
        Map<String,Object> elect_landlord_order = new HashMap<String,Object>();
        int currentsellplayerindex = RoomsContains.Rooms_Clients_Index.get(room_id).get(currentsellplayerId);
        //发送index用于比较
        elect_landlord_order.put("current_elect_player_index",currentsellplayerindex);
        try {
            sendAll(room_id, elect_landlord_order);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
