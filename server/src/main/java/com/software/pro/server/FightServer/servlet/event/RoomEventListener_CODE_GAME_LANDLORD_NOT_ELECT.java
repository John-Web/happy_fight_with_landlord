package com.software.pro.server.FightServer.servlet.event;

import com.software.pro.server.FightServer.event.ServerEventListener;
import com.software.pro.server.FightServer.servlet.Room_servlet;
import com.software.pro.server.FightServer.servlet.RoomsContains;
import com.software_pro.common.entity.ClientSide;
import com.software_pro.common.entity.Room;
import com.software_pro.common.entity.WebData;
import com.software_pro.common.enums.RoomEventCode;
import com.software_pro.common.enums.ServerEventCode;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class RoomEventListener_CODE_GAME_LANDLORD_NOT_ELECT implements RoomEventListener {
    public void call(String room_id){}
    public void call(int client_id, String room_id, String data){
        //交换牌权, 设置上下游
        Room room = RoomsContains.Rooms.get(room_id);
        ClientSide clientSide = RoomsContains.Rooms_ClientsMap.get(room_id).get(String.valueOf(client_id));
        ClientSide turnClientSide = clientSide.getNext();
        room.setCurrentSellClient(turnClientSide.getId());
        room.setLastSellClient(client_id);
        //要发送的数据
        int lastIndex = RoomsContains.Rooms_Clients_Index.get(room_id).get(client_id);
        int turnedIndex = RoomsContains.Rooms_Clients_Index.get(room_id).get(turnClientSide.getId());
        String lastName = clientSide.getOwner_name();
        //获取房间不叫地主的情况
        int message = RoomsContains.Rooms_Index_Of_NOT_Elect.get(room_id);
        message += Math.pow(2,lastIndex);

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("LAST_NOTELECT_LANDLORD_PALYER_INDEX",lastIndex);
        map.put("LAST_NOTELECT_LANDLORD_PLAYER_NAME",lastName);
        if (message == 7)
            map.put("TURNED_DECIDE_LANDLORD_ELECT_PLAYER_INDEX",-1);            //发送-1提示重新开始游戏
        else
            map.put("TURNED_DECIDE_LANDLORD_ELECT_PLAYER_INDEX",turnedIndex);
        try {
            Room_servlet.sendAll(room_id, map);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        //修改房间状态
        RoomsContains.Rooms_Index_Of_NOT_Elect.put(room_id,message);
        if(message == 7){
            RoomsContains.Rooms_Index_Of_NOT_Elect.put(room_id,0);
            ServerEventListener.get(ServerEventCode.CODE_GAME_STARTING).call(RoomsContains.Rooms_ClientsMap
                    .get(room_id)
                    .get(String.valueOf(client_id)),room_id);
            //然后等待结果
            RoomEventListener.get(RoomEventCode.CODE_GAME_STARTING).call(room_id);
        }
    }
}
