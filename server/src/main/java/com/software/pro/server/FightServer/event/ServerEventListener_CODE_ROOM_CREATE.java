package com.software.pro.server.FightServer.event;

import com.software.pro.server.FightServer.ServerContains;
import com.software_pro.common.channel.ChannelUtils;
import com.software_pro.common.entity.ClientSide;
import com.software_pro.common.entity.Room;
import com.software_pro.common.enums.ClientEventCode;
import com.software_pro.common.enums.RoomStatus;
import com.software_pro.common.enums.RoomType;
import org.nico.noson.Noson;

public class ServerEventListener_CODE_ROOM_CREATE implements ServerEventListener{

	@Override
	public void call(ClientSide clientSide, String data) {

		//创建一个新的房间
		Room room = new Room(ServerContains.getServerId());
		room.setStatus(RoomStatus.BLANK);                                //空闲
		room.setType(RoomType.PVP);
		room.setRoomOwner(clientSide.getOwner_name());                   //房主
		room.getClientSideMap().put(clientSide.getId(), clientSide);     //存map
		room.getClientSideList().add(clientSide);             //房主加入房间
		room.setCurrentSellClient(clientSide.getId());        //当前庄主
		room.setCreateTime(System.currentTimeMillis());
		room.setLastFlushTime(System.currentTimeMillis());
		
		clientSide.setRoomId(room.getId());
		ServerContains.addRoom(room);

		//将room推回客户端
		System.out.println(clientSide.getId()+"创建了房间:"+room.getId());
		ChannelUtils.pushToClient(clientSide.getChannel(), ClientEventCode.CODE_ROOM_CREATE_SUCCESS, Noson.reversal(room));
	}

	



}
