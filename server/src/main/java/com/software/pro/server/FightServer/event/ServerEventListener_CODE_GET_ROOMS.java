package com.software.pro.server.FightServer.event;

import com.software.pro.server.FightServer.ServerContains;
import com.software_pro.common.channel.ChannelUtils;
import com.software_pro.common.entity.ClientSide;
import com.software_pro.common.entity.Room;
import com.software_pro.common.enums.ClientEventCode;
import com.software_pro.common.helper.MapHelper;
import org.nico.noson.Noson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ServerEventListener_CODE_GET_ROOMS implements ServerEventListener{

	@Override
	public void call(ClientSide clientSide, String data) {
		List<Map<String, Object>> roomList = new ArrayList<>(ServerContains.getRoomMap().size());
		for(Entry<Integer, Room> entry: ServerContains.getRoomMap().entrySet()) {
			Room room = entry.getValue();
			roomList.add(MapHelper.newInstance()
					.put("roomId", room.getId())
					.put("roomOwner", room.getRoomOwner())
					.put("roomClientCount", room.getClientSideList().size())
					.put("roomType", room.getType())
					.map());
		}

		//将room列表推回客户端
		ChannelUtils.pushToClient(clientSide.getChannel(), ClientEventCode.CODE_SHOW_ROOMS, Noson.reversal(roomList));
	}

}
