package com.software.pro.client.FightClient.event;

import com.software.pro.client.FightClient.WebContains;
import com.software_pro.common.entity.Room;
import com.software_pro.common.entity.WebData;
import com.software_pro.common.printer.SimplePrinter;
import io.netty.channel.Channel;
import org.nico.noson.Noson;


public class ClientEventListener_CODE_ROOM_CREATE_SUCCESS extends ClientEventListener{

	@Override
	public void call(Channel channel, String data) {
		
		Room room = Noson.convert(data, Room.class);
		
		initLastSellInfo();
		
		SimplePrinter.printNotice("You have created a room with id " + room.getId());
		SimplePrinter.printNotice("Please wait for other players to join !");
		try {
			WebContains.ServerDatas.put(new WebData("Create_room_result", true));
			WebContains.ServerDatas.put(new WebData("Create_room_result_message","You have created a room with id"
					+ room.getId() + "Please wait for other players to join !"));
		}
		catch (Exception e){
			System.out.println(e.getMessage());
		}
		//将 创建房间成功的消息写回网页
	}

}
