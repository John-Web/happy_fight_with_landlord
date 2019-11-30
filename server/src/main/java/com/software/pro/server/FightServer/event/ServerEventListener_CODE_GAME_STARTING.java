package com.software.pro.server.FightServer.event;

import com.software.pro.server.FightServer.ServerContains;
import com.software_pro.common.channel.ChannelUtils;
import com.software_pro.common.entity.ClientSide;
import com.software_pro.common.entity.Poker;
import com.software_pro.common.entity.Room;
import com.software_pro.common.entity.WebData;
import com.software_pro.common.enums.ClientEventCode;
import com.software_pro.common.enums.ClientRole;
import com.software_pro.common.enums.ClientType;
import com.software_pro.common.enums.RoomStatus;
import com.software_pro.common.helper.MapHelper;
import com.software_pro.common.helper.PokerHelper;

import java.util.LinkedList;
import java.util.List;

public class ServerEventListener_CODE_GAME_STARTING implements ServerEventListener{

	@Override
	public void call(ClientSide clientSide, String data) {   //data room_id
		Room room = ServerContains.getRoom(clientSide.getRoomId());

		LinkedList<ClientSide> roomClientList = room.getClientSideList();

		// 服务器发牌到房间服务
		List<List<Poker>> pokersList = PokerHelper.distributePoker();
		int cursor = 0;
		for(ClientSide client: roomClientList){
			client.setPokers(pokersList.get(cursor ++));
		}
		room.setLandlordPokers(pokersList.get(3));   //pokerList的最后一项为地主额外的三张牌

		// Push information about the robber
		//抓阄产生首个庄主
		int startGrabIndex = (int)(Math.random() * 3);
		ClientSide startGrabClient = roomClientList.get(startGrabIndex);
		room.setCurrentSellClient(startGrabClient.getId());    //第一个决策的玩家
		
		// Push start game messages
		room.setStatus(RoomStatus.STARTING);

		//三个客户端初始化,下一个都设置为抓阄结果
		for(ClientSide client: roomClientList) {
			client.setType(ClientType.PEASANT);

			String result = MapHelper.newInstance()
					.put("roomId", room.getId())
					.put("roomOwner", room.getRoomOwner())
					.put("roomClientCount", room.getClientSideList().size())
					.put("nextClientPlayername", startGrabClient.getOwner_name())
					.put("nextClientId", startGrabClient.getId())
					.put("pokers", client.getPokers())
					.json();

			//三个客户端同时开始游戏
			if(client.getRole() == ClientRole.PLAYER) {
				ChannelUtils.pushToClient(client.getChannel(), ClientEventCode.CODE_GAME_STARTING, result);
			}else {
				//机 器 人 
			}

		}
		//向房间服务器发送全局服务器设置完成的消息       然后房间服务器就可以快乐的使用clientside的牌了
		//接入房间服务器
		try{
			ServerContains.Server_Room_Data.get(room.getId()).put(new WebData("Setup_completed",1));
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
}
