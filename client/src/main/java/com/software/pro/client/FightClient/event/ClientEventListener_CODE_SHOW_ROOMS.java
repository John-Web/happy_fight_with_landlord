package com.software.pro.client.FightClient.event;

import com.software_pro.common.enums.ClientEventCode;
import com.software_pro.common.printer.FormatPrinter;
import com.software_pro.common.printer.SimplePrinter;
import io.netty.channel.Channel;
import org.nico.noson.Noson;
import org.nico.noson.entity.NoType;

import java.util.List;
import java.util.Map;

import static com.software.pro.client.FightClient.event.ClientEventListener_CODE_CLIENT_PLAYERNAME_SET.NAME_MAX_LENGTH;

public class ClientEventListener_CODE_SHOW_ROOMS extends ClientEventListener{

	@Override
	public void call(Channel channel, String data) {
		
		List<Map<String, Object>> roomList = Noson.convert(data, new NoType<List<Map<String, Object>>>() {});
		if(roomList != null && ! roomList.isEmpty()){
			// "COUNT" begins after NICKNAME_MAX_LENGTH characters. The dash means that the string is left-justified.
			String format = "##\t%s\t|\t%-" + NAME_MAX_LENGTH + "s\t|\t%s\t|\t%s\t#\n";
			FormatPrinter.printNotice(format, "ID", "OWNER", "COUNT", "TYPE");
			for(Map<String, Object> room: roomList) {
				FormatPrinter.printNotice(format, room.get("roomId"), room.get("roomOwner"), room.get("roomClientCount"), room.get("roomType"));
			}
			//将roomlist写回客户端
			SimplePrinter.printNotice("");
		}else {
			SimplePrinter.printNotice("No available room, please create a room ！");
		}
		get(ClientEventCode.CODE_SHOW_OPTIONS_PVP).call(channel, data);
	}



}
