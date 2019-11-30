package com.software.pro.server.FightServer.servlet.event;

import com.software.pro.server.FightServer.ServerContains;
import com.software.pro.server.FightServer.servlet.Room_servlet;
import com.software.pro.server.FightServer.servlet.RoomsContains;
import com.software_pro.common.entity.ClientSide;
import com.software_pro.common.entity.Room;
import com.software_pro.common.entity.WebData;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class RoomEventListener_CODE_GAME_STARTING implements RoomEventListener{
    public void call(int room_id, String client_id, String data){}
    public void call(String room_id){
        //尝试从全局服务器获得牌, 然后send, 玩家的牌必须从全局服务器获取, 然后分组改变顺序发给每一个玩家
        // 房间服务器从全局服务器获取全部数据,然后在这里分发给客户端
        //先设置,成功从大厅接入
        try{
            WebData webData = ServerContains.Server_Room_Data.get(Integer.valueOf(room_id)).take();
            if(webData.getKey().equalsIgnoreCase("Setup_completed")){    //****************//这里十分重要
                //设置list
                LinkedList<ClientSide> roomClientSidelist = ServerContains.getRoom(Integer.valueOf(room_id)).getClientSideList();
                RoomsContains.Rooms_ClientSides.put(room_id,roomClientSidelist);
                //设置map
                Map<String, ClientSide> map = new HashMap<>();
                for(ClientSide clientSide:roomClientSidelist){
                    map.put(String.valueOf(clientSide.getId()),clientSide);
                }
                RoomsContains.Rooms_ClientsMap.put(room_id,map);
                //设置index
                Map<Integer,Integer>indexmap = new HashMap<>();
                for(int i=0;i<3;i++){
                    indexmap.put(roomClientSidelist.get(i).getId(),i);
                }
                RoomsContains.Rooms_Clients_Index.put(room_id,indexmap);

                //这个房间已经开始游戏
                Room room = ServerContains.getRoom(Integer.valueOf(room_id));
                RoomsContains.Rooms.put(room_id,room);

                //设置房间不叫地主的次数为0
                RoomsContains.Rooms_Index_Of_NOT_Elect.put(room_id,0);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        Room_servlet.gameStart(room_id);
    }
}
