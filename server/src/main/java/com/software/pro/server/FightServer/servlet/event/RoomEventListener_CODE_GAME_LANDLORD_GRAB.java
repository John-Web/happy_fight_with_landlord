package com.software.pro.server.FightServer.servlet.event;

import com.software.pro.server.FightServer.servlet.Room_servlet;
import com.software.pro.server.FightServer.servlet.RoomsContains;
import com.software_pro.common.entity.ClientSide;
import com.software_pro.common.entity.Room;

import java.util.HashMap;
import java.util.Map;


public class RoomEventListener_CODE_GAME_LANDLORD_GRAB implements RoomEventListener {

	@Override
	public void call(int client_id, String room_id,String data) {
		int elect_index = RoomsContains.Rooms_Index_Of_Elect.get(room_id);

		Room room = RoomsContains.Rooms.get(room_id);
		room.setLastSellClient(client_id);

		int grabIndex = RoomsContains.Rooms_Clients_Index.get(room_id).get(client_id);       //当前抢地主玩家的index
		RoomsContains.Rooms_INDEX_Of_Grab.put(room_id,grabIndex);        //最后一个抢地主玩家的id

		Map<String, Object> map = new HashMap<String, Object>(); //要发送的消息
		if(elect_index!=grabIndex) {
			int not_elect_index = RoomsContains.Rooms_Index_Of_NOT_Elect.get(room_id);           //当前房间不叫地主玩家的序号
			//一定存在下一个决定是否抢地主的玩家
			//叫地主的玩家为第一个
			int nextGrabIndex = (grabIndex + 1) % 3;
			int testIndex = 0;
			testIndex += Math.pow(2, nextGrabIndex);

			if ((testIndex & not_elect_index) != 0) {
				nextGrabIndex = (nextGrabIndex + 1) % 3;
			}
			map.put("NEXT_LANDLORD_GRAB_PLAYER_INDEX", nextGrabIndex);
		}
		else{
			map.put("LANDLORD_INDEX",grabIndex);         //最长的一种情况, 叫地主的玩家抢了地主
		}
		map.put("LAST_ELECT_OR_GRAB_PLAYER_INDEX",grabIndex);
		try {
			Room_servlet.sendAll(room_id, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void call(String room_id){

	}
}
