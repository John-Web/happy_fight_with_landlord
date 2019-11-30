package com.software.pro.server.FightServer.event;



import com.software.pro.server.FightServer.ServerContains;
import com.software.pro.server.FightServer.servlet.RoomsContains;
import com.software_pro.common.channel.ChannelUtils;
import com.software_pro.common.entity.ClientSide;
import com.software_pro.common.entity.Room;
import com.software_pro.common.entity.WebData;
import com.software_pro.common.enums.ClientEventCode;
import com.software_pro.common.enums.RoomStatus;
import com.software_pro.common.enums.ServerEventCode;
import com.software_pro.common.helper.MapHelper;

import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentSkipListMap;

public class ServerEventListener_CODE_ROOM_JOIN implements ServerEventListener {

	@Override                //data房间号
	public void call(ClientSide clientSide, String data) {

		Room room = ServerContains.getRoom(Integer.valueOf(data));

		if (room == null) {
			String result = MapHelper.newInstance()
					.put("roomId", data)
					.json();
			ChannelUtils.pushToClient(clientSide.getChannel(), ClientEventCode.CODE_ROOM_JOIN_FAIL_BY_INEXIST, result);//没
		} else {   //房间存在
			if (room.getClientSideList().size() == 3) {
				String result = MapHelper.newInstance()
						.put("roomId", room.getId())
						.put("roomOwner", room.getRoomOwner())
						.json();
				//向web发送消息,房间已满//暂且可以忽略
			} else {
				clientSide.setRoomId(room.getId());

				ConcurrentSkipListMap<Integer, ClientSide> roomClientMap = (ConcurrentSkipListMap<Integer, ClientSide>) room.getClientSideMap();
				LinkedList<ClientSide> roomClientList = room.getClientSideList();

				if (roomClientList.size() > 0) {      //连接上下游
					ClientSide pre = roomClientList.getLast();
					pre.setNext(clientSide);
					clientSide.setPre(pre);
				}
				roomClientList.add(clientSide);
				roomClientMap.put(clientSide.getId(), clientSide);

				room.setStatus(RoomStatus.WAIT);          //房间半满 , 等待
				String result = MapHelper.newInstance()
						.put("clientId", clientSide.getId())
						.put("clientPlayername", clientSide.getOwner_name())
						.put("roomId", room.getId())
						.put("roomOwner", room.getRoomOwner())
						.put("roomClientCount", room.getClientSideList().size())
						.put("clientRole", clientSide.getRole().toString())
						.json();
				for (ClientSide client : roomClientMap.values()) {
					ChannelUtils.pushToClient(client.getChannel(), ClientEventCode.CODE_ROOM_JOIN_SUCCESS, result);
				}
				//这边加入后直接在房间服务器取出群发, 所以不用每个客户端都塞入
				try {
					ServerContains.Server_Room_Data.get(room.getId()).put(new WebData("join_room_type", 0)); //表示非房主加入房间
					ServerContains.Server_Room_Data.get(room.getId()).put(new WebData("join_room_client_id", String.valueOf(clientSide.getId())));
					ServerContains.Server_Room_Data.get(room.getId()).put(new WebData("join_room_client_name", clientSide.getOwner_name()));
					ServerContains.Server_Room_Data.get(room.getId()).put(new WebData("join_room_client_role", clientSide.getRole().toString()));
				}
				//以上内容为补充
				catch (Exception e) {
					e.printStackTrace();
				}
				if (roomClientMap.size() == 3) {
					clientSide.setNext(roomClientList.getFirst());
					roomClientList.getFirst().setPre(clientSide);
					//尝试从房间服务器接收游戏开始的讯号, 以room_id为标识的 从RoomsContains里取值 这里为第一次
					try{
						BlockingQueue<WebData>room_message = RoomsContains.Rooms_Messages_MAP.get(room.getId());
						WebData game_begin_message = room_message.take();
						if(game_begin_message.getKey().equalsIgnoreCase("message_form_roomservlet_game_begin")){
							ServerEventListener.get(ServerEventCode.CODE_GAME_STARTING).call(clientSide, String.valueOf(room.getId()));
						}
					}
					catch (Exception e){
						e.printStackTrace();
					}
				}
			}
		}
	}
}
