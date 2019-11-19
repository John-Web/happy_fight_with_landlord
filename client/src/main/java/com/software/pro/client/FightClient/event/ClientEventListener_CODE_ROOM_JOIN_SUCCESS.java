package com.software.pro.client.FightClient.event;

import com.software.pro.client.FightClient.FightClient;
import com.software_pro.common.helper.MapHelper;
import com.software_pro.common.printer.SimplePrinter;
import io.netty.channel.Channel;

import java.util.Map;

public class ClientEventListener_CODE_ROOM_JOIN_SUCCESS extends ClientEventListener{

	@Override
	public void call(Channel channel, String data) {
		Map<String, Object> map = MapHelper.parser(data);
		
		initLastSellInfo();
		
		int joinClientId = (int) map.get("clientId");
		//在这里将消息推给web
		if(FightClient.id == joinClientId) {
			SimplePrinter.printNotice("You have joined room：" + map.get("roomId") + ". There are " + map.get("roomClientCount") + " players in the room now.");
			SimplePrinter.printNotice("Please wait for other players to join, start a good game when the room player reaches three !");
		}else {
			SimplePrinter.printNotice(map.get("clientPlayername") + " joined room, the current number of room player is " + map.get("roomClientCount"));
		}
		
	}



}
