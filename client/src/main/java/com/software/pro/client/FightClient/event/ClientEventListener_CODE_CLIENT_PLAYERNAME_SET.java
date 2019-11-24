package com.software.pro.client.FightClient.event;

import com.software.pro.client.FightClient.ClientContains;
import com.software_pro.common.entity.WebData;
import com.software_pro.common.enums.ClientEventCode;
import com.software_pro.common.enums.ServerEventCode;
import com.software_pro.common.helper.MapHelper;
import com.software_pro.common.printer.SimplePrinter;
import com.software_pro.common.printer.SimpleWriter;
import io.netty.channel.Channel;
import org.nico.noson.util.string.StringUtils;

import java.util.Map;

public class ClientEventListener_CODE_CLIENT_PLAYERNAME_SET extends ClientEventListener{

	public static final int NAME_MAX_LENGTH = 10;
	
	@Override
	public void call(Channel channel, String data) {

		// If it is not the first time that the user is prompted to enter nickname
		// If first time, data = null or "" otherwise not empty
		if (StringUtils.isNotBlank(data)) {
			Map<String, Object> dataMap = MapHelper.parser(data);
			if (dataMap.containsKey("invalidLength")) {
				SimplePrinter.printNotice("Your name length was invalid: " + dataMap.get("invalidLength"));
			}
		}
		SimplePrinter.printNotice("Please set your name (upto " + NAME_MAX_LENGTH + " characters)");
		//String name = SimpleWriter.write("your_name");
		//取值
		try {
			WebData webData = ClientContains.webDatas.take();
			String name_key = webData.getKey();
			if (name_key.equalsIgnoreCase("player_name")) {
				String name = webData.getValue().toString();
				System.out.println(name);                                           //用户与服务器的第一次交互: 输入用户名,也可以加密码
				// If the length of nickname is more that NICKNAME_MAX_LENGTH
				if (name.trim().length() > NAME_MAX_LENGTH) {
					String result = MapHelper.newInstance().put("invalidLength", name.trim().length()).json();
					get(ClientEventCode.CODE_CLIENT_PLAYERNAME_SET).call(channel, result);
				} else {
					pushToServer(channel, ServerEventCode.CODE_CLIENT_PLAYERNAME_SET, name);
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

//		// If the length of nickname is more that NICKNAME_MAX_LENGTH
//		if (name.trim().length() > NAME_MAX_LENGTH) {
//			String result = MapHelper.newInstance().put("invalidLength", name.trim().length()).json();
//			get(ClientEventCode.CODE_CLIENT_PLAYERNAME_SET).call(channel, result);
//		}else{
//			pushToServer(channel, ServerEventCode.CODE_CLIENT_PLAYERNAME_SET, name);
//		}
	}



}
