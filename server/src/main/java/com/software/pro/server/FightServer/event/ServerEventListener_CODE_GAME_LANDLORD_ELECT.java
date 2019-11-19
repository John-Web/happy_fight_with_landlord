package com.software.pro.server.FightServer.event;

import com.software.pro.server.FightServer.ServerContains;
import com.software_pro.common.channel.ChannelUtils;
import com.software_pro.common.entity.ClientSide;
import com.software_pro.common.entity.Room;
import com.software_pro.common.enums.ClientEventCode;
import com.software_pro.common.enums.ClientRole;
import com.software_pro.common.enums.ClientType;
import com.software_pro.common.enums.ServerEventCode;
import com.software_pro.common.helper.MapHelper;
import com.software_pro.common.helper.PokerHelper;

public class ServerEventListener_CODE_GAME_LANDLORD_ELECT implements ServerEventListener{

	@Override
	public void call(ClientSide clientSide, String data) {
		//date true or false 表用户决策
		Room room = ServerContains.getRoom(clientSide.getRoomId());
		
		if(room != null) {
			boolean isY = Boolean.valueOf(data);
			if(isY){
				//用户抢地主, 插入地主牌然后排序
				clientSide.getPokers().addAll(room.getLandlordPokers());
				PokerHelper.sortPoker(clientSide.getPokers());
				
				int currentClientId = clientSide.getId();
				room.setLandlordId(currentClientId);       //设置房间的地主
				room.setLastSellClient(currentClientId);   //地主先出牌
				room.setCurrentSellClient(currentClientId);
				clientSide.setType(ClientType.LANDLORD);
				
				for(ClientSide client: room.getClientSideList()){
					String result = MapHelper.newInstance()
							.put("roomId", room.getId())
							.put("roomOwner", room.getRoomOwner())
							.put("roomClientCount", room.getClientSideList().size())
							.put("landlordPlayername", clientSide.getOwner_name())
							.put("landlordId", clientSide.getId())
							.put("additionalPokers", room.getLandlordPokers()).json();
					
					if(client.getRole() == ClientRole.PLAYER) {
						// 地主已经选定, 比其它人多出三张牌, 推送到web

					}else {
						//机器人 不用管
					}
				}
			}else{
				//当前用户不抢地主
				if(clientSide.getNext().getId() == room.getLandlordId()){
					for(ClientSide client: room.getClientSideList()){
						if(client.getRole() == ClientRole.PLAYER) {      //player -> landlord -> player
							ChannelUtils.pushToClient(client.getChannel(), ClientEventCode.CODE_GAME_LANDLORD_CYCLE, null);
						}
					}
					ServerEventListener.get(ServerEventCode.CODE_GAME_STARTING).call(clientSide, null);
				}else{
					ClientSide turnClientSide = clientSide.getNext();                   //当前用户不抢地主 轮询下一个
					room.setCurrentSellClient(turnClientSide.getId());
					String result = MapHelper.newInstance()
							.put("roomId", room.getId())
							.put("roomOwner", room.getRoomOwner())
							.put("roomClientCount", room.getClientSideList().size())
							.put("preClientPlayername", clientSide.getOwner_name())
							.put("nextClientPlayername", turnClientSide.getOwner_name())
							.put("nextClientId", turnClientSide.getId())
							.json();
					
					for(ClientSide client: room.getClientSideList()) {
						if(client.getRole() == ClientRole.PLAYER) {
							ChannelUtils.pushToClient(client.getChannel(), ClientEventCode.CODE_GAME_LANDLORD_ELECT, result);
						}
						else{
							//机器人逻辑 pushToClient Robat
						}
					}
				}
			}
		}else {
//			ChannelUtils.pushToClient(clientSide.getChannel(), ClientEventCode.CODE_ROOM_PLAY_FAIL_BY_INEXIST, null);
		}
	}
}
