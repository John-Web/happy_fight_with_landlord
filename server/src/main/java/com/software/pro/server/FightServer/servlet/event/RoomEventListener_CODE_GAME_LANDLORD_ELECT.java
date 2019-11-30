package com.software.pro.server.FightServer.servlet.event;

import com.software.pro.server.FightServer.ServerContains;
import com.software.pro.server.FightServer.event.ServerEventListener;
import com.software.pro.server.FightServer.servlet.Room_servlet;
import com.software.pro.server.FightServer.servlet.RoomsContains;
import com.software_pro.common.channel.ChannelUtils;
import com.software_pro.common.entity.ClientSide;
import com.software_pro.common.entity.Room;
import com.software_pro.common.enums.ClientEventCode;
import com.software_pro.common.enums.ClientRole;
import com.software_pro.common.enums.ClientType;
import com.software_pro.common.enums.ServerEventCode;
import com.software_pro.common.helper.MapHelper;
import com.software_pro.common.helper.PokerHelper;

import java.util.HashMap;
import java.util.Map;

// 如果当前玩家是最后一个玩家, 则直接确认地主,通过
// 如果当前玩家不是最后一个玩家,则call抢地主
//

public class RoomEventListener_CODE_GAME_LANDLORD_ELECT implements RoomEventListener {

	@Override
	public void call(int client_id, String room_id,String data) {
		//该id的玩家叫地主,更新数据后直接进入抢地主
		Room room = RoomsContains.Rooms.get(room_id);
		room.setLastSellClient(client_id);

		int elect_index = RoomsContains.Rooms_Clients_Index.get(room_id).get(client_id);
		RoomsContains.Rooms_Index_Of_Elect.put(room_id,elect_index);
		//************************************************************************************************************//
		Map<String,Object> map = new HashMap<String,Object>(); //要发送的消息
		//判断当前不叫地主的人的个数是否为2
		int not_elect_index = RoomsContains.Rooms_Index_Of_NOT_Elect.get(room_id);
		if(check(not_elect_index)){
			//当前玩家就是地主
			room.setLandlordId(client_id);
			//直接到出牌阶段
			room.setCurrentSellClient(client_id);           //这个阶段是地主的阶段
			map.put("LANDLORD_INDEX",elect_index);
			map.put("LAST_ELECT_OR_GRAB_PLAYER_INDEX",elect_index);
		}
		else { //有一个或两个未决策的玩家,一定都在该玩家的后面,并且最近的哪个玩家一定是一个未决策的玩家
			//要一直轮询到 elect_index
			int nextGrabIndex = (elect_index + 1) % 3;      //这个玩家一定是一个未决策的玩家
			room.setCurrentSellClient(nextGrabIndex);
			//直接发送nextGrabIndex
			map.put("NEXT_LANDLORD_GRAB_PLAYER_INDEX",nextGrabIndex);
			map.put("LAST_ELECT_OR_GRAB_PLAYER_INDEX",elect_index);
		}
		try{
			Room_servlet.sendAll(room_id,map);
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	public void call(String room_id){}
	boolean check(int not_elect_index){    //检测是否有两个玩家不叫地主
		for(int i=0;i<3; i++){
			int testIndex = 0;
			testIndex += 7 - Math.pow(2,i);
			if((testIndex&not_elect_index)==testIndex){
				return true;
			}
		}
		return false;
	}
}
