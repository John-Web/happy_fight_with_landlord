package com.software.pro.server.FightServer.servlet.event;

import com.software.pro.server.FightServer.servlet.Room_servlet;
import com.software.pro.server.FightServer.servlet.RoomsContains;
import com.software_pro.common.entity.ClientSide;
import com.software_pro.common.entity.Room;

import java.util.HashMap;
import java.util.Map;

public class RoomEventListener_CODE_GAME_LANDLORD_NOT_GRAB implements RoomEventListener {

	@Override
	public void call(int client_id, String room_id,String data) {
		Room room = RoomsContains.Rooms.get(room_id);
		room.setLastSellClient(client_id);
		Map<String, Object> map = new HashMap<String, Object>(); //要发送的消息

		int elect_index = RoomsContains.Rooms_Index_Of_Elect.get(room_id);
		int not_grab_index = RoomsContains.Rooms_Clients_Index.get(room_id).get(client_id);
		if(elect_index!=not_grab_index){        //轮询下一个决策抢地主的玩家,并且该玩家是肯定存在的(叫地主的玩家) //但其实不然
			int next_grab_index = (not_grab_index + 1)%3;
			int testIndex = 0;
			testIndex += Math.pow(2, next_grab_index);

			if((testIndex&next_grab_index)==0){
				if(next_grab_index!=elect_index) {
					map.put("NEXT_LANDLORD_GRAB_PLAYER_INDEX", next_grab_index);
				}
				else{
					if(RoomsContains.Rooms_INDEX_Of_Grab.get(room_id)==null){
						map.put("LANDLORD_INDEX",elect_index);
					}
					else{
						map.put("LANDLORD_INDEX",RoomsContains.Rooms_INDEX_Of_Grab.get(room_id));
					}
				}
			}
			else{             //不叫, 叫地主, 不抢
				map.put("LANDLORD_INDEX",elect_index);
			}

		}
		else{         //叫地主的玩家不抢地主,就可以直接从两个map中取出数据对比发送
			map.put("LANDLORD_INDEX",RoomsContains.Rooms_INDEX_Of_Grab.get(room_id));
		}
		map.put("LAST_ELECT_OR_GRAB_PLAYER_INDEX",not_grab_index);
		try{
			Room_servlet.sendAll(room_id,map);
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	public void call(String room_id){

	}
}
