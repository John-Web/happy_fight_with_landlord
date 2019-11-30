package com.software.pro.client.FightClient.Controller;

import com.software.pro.client.FightClient.ClientContains;
import com.software.pro.client.FightClient.WebContains;
import com.software_pro.common.entity.ClientSide;
import com.software_pro.common.entity.WebData;
import com.software_pro.common.helper.MapHelper;
import com.software_pro.common.printer.FormatPrinter;
import org.nico.noson.Noson;
import org.nico.noson.entity.NoType;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/DataCollection")
public class DataCollection {

    @RequestMapping("/client_service")
    @ResponseBody
    public Map<String, Object> sent_to_contains(@RequestParam("key") String key, @RequestParam("value") String value){
        //先向服务器推用户的操作,然后再获取!!! 核心!!!
        WebData webData = new WebData(key,value);
        try {
            ClientContains.webDatas.put(webData);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        //容器
        Map<String, Object> map = new HashMap<>();

        //***************************************pvp_page 页面操作簇**********************************************8*****
        if(key.equalsIgnoreCase("pvp_select")&&Integer.valueOf(value)==1) {
            //返回创建房间结果
            try {
                WebData webData_select_result = WebContains.ServerDatas.take();
                WebData webData_select_result_message = WebContains.ServerDatas.take();
                map.put("Create_room_result", webData_select_result.getValue());                  //房间id
                map.put("Create_room_result_message", webData_select_result_message.getValue());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        else if(key.equalsIgnoreCase("pvp_select")&&Integer.valueOf(value)==2){
            //返回查询所有房间结果
            try {
                WebData webData_select_result = WebContains.ServerDatas.take();        //获取操作结果
                if ((int)webData_select_result.getValue()>0){              //成功
                    map.put("Show_room_result",1);
                    WebData webData_select_result_message = WebContains.ServerDatas.take();

                    List<Map<String, Object>> roomList = (List<Map<String, Object>>)webData_select_result_message.getValue();
                    List<String>list = new LinkedList<String>();
                    for(Map<String, Object> room: roomList) {
                        list.add(String.valueOf(room.get("roomId")));
                        list.add(room.get("roomOwner").toString());
                        list.add(String.valueOf(room.get("roomClientCount")));
                        list.add(room.get("roomType").toString());
                    }
                    map.put("Show_room_result_message",list);
                }
                else{
                    map.put("Show_room_result",0);
                    map.put("Show_room_result_message","No available room, please create a room ！");
                }
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        else if(key.equalsIgnoreCase("join_room_select")){
            //向服务器发送加入房间的消息
            //从服务器提供的api查询指定id房间的人员信息
            try{     //先尝试获取加入房间的结果, 如果成功则向服务器开放api请求当前房间信息
                WebData webData_select_result = WebContains.ServerDatas.take();
                if(Integer.valueOf(webData_select_result.getValue().toString())==0){      //请求加入房间成功,主动请求加入房间为ajax
                    //调用api获取当前房间信息
                    MultiValueMap<String, Object> request = new LinkedMultiValueMap<>();
                    request.add("room_id",Integer.valueOf(value));
                    RestTemplate restTemplate = new RestTemplate();
                    String result = restTemplate.postForObject("http://localhost:8090/landlord_servlet/room_servlet",request,String.class);

                    Map<String, Object> clientmap = MapHelper.parser(result);  //房主, 客户端s
                    List<String>clientlist = new LinkedList<String>();
                    if(clientmap.containsKey("clientsideList")){
                        LinkedList<ClientSide> clientSideLinkedList = Noson.convert(clientmap.get("clientsideList"), new NoType<LinkedList<ClientSide>>() {});
                        for(ClientSide clientSide:clientSideLinkedList) {
                            clientlist.add(String.valueOf(clientSide.getId()));
                            clientlist.add(clientSide.getOwner_name());
                            clientlist.add("\""+clientSide.getRole().toString()+"\"");
                        }
                        map.put("join_room_result",clientlist);//长度至少为6
                    }
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }

        else{
            map.put("state_code",200);
        }
        return map;
    }
}
