package com.software.pro.server.FightServer.event;



import com.software.pro.server.FightServer.ServerContains;
import com.software_pro.common.channel.ChannelUtils;
import com.software_pro.common.entity.ClientSide;
import com.software_pro.common.entity.Room;
import com.software_pro.common.enums.ClientEventCode;
import com.software_pro.common.enums.RoomStatus;
import com.software_pro.common.enums.ServerEventCode;
import com.software_pro.common.helper.MapHelper;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentSkipListMap;

public class ServerEventListener_CODE_ROOM_JOIN implements ServerEventListener{

	@Override                //data房间号
	public void call(ClientSide clientSide, String data) {

		Room room = ServerContains.getRoom(Integer.valueOf(data));
		
		if(room == null) {
			String result = MapHelper.newInstance()
								.put("roomId", data)
								.json();
			ChannelUtils.pushToClient(clientSide.getChannel(), ClientEventCode.CODE_ROOM_JOIN_FAIL_BY_INEXIST, result);//没
		}else {   //房间存在
			if(room.getClientSideList().size() == 3) {
				String result = MapHelper.newInstance()
						.put("roomId", room.getId())
						.put("roomOwner", room.getRoomOwner())
						.json();
				//向web发送消息,房间已满
			}else {
				clientSide.setRoomId(room.getId());

				ConcurrentSkipListMap<Integer, ClientSide> roomClientMap = (ConcurrentSkipListMap<Integer, ClientSide>) room.getClientSideMap();
				LinkedList<ClientSide> roomClientList = room.getClientSideList();

				if(roomClientList.size() > 0){      //连接上下游
					ClientSide pre = roomClientList.getLast();
					pre.setNext(clientSide);
					clientSide.setPre(pre);
				}

				roomClientList.add(clientSide);
				roomClientMap.put(clientSide.getId(), clientSide);

				if(roomClientMap.size() == 3) {
					clientSide.setNext(roomClientList.getFirst());
					roomClientList.getFirst().setPre(clientSide);

					//充当最后一个加入的玩家
					ServerEventListener.get(ServerEventCode.CODE_GAME_STARTING).call(clientSide, String.valueOf(room.getId()));
				}else {
					room.setStatus(RoomStatus.WAIT);          //房间半满 , 等待

					String result = MapHelper.newInstance()
							.put("clientId", clientSide.getId())
							.put("clientPlayername", clientSide.getOwner_name())
							.put("roomId", room.getId())
							.put("roomOwner", room.getRoomOwner())
							.put("roomClientCount", room.getClientSideList().size())
							.json();
					for(ClientSide client: roomClientMap.values()) {
						ChannelUtils.pushToClient(client.getChannel(), ClientEventCode.CODE_ROOM_JOIN_SUCCESS, result);
					}
				}
			}


		}
	}





}
