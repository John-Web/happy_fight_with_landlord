package com.software.pro.client.FightClient.event;

import com.software.pro.client.FightClient.FightClient;
import com.software_pro.common.enums.ServerEventCode;
import com.software_pro.common.helper.MapHelper;
import com.software_pro.common.printer.SimplePrinter;
import com.software_pro.common.printer.SimpleWriter;
import io.netty.channel.Channel;

import java.util.Map;

public class ClientEventListener_CODE_GAME_LANDLORD_ELECT extends ClientEventListener{

	@Override
	public void call(Channel channel, String data) {
		Map<String, Object> map = MapHelper.parser(data);
		//当前第一个决定是否抢地主的人
		int turnClientId = (int) map.get("nextClientId");

		//肯定不包含
		if(map.containsKey("preClientPlayername")) {
			SimplePrinter.printNotice(map.get("preClientPlayername") + " don't rob the landlord!");
		}
		//就是当前客户端
		if(turnClientId == FightClient.id) {                                        //和用户的第四次交互
			//将此消息推送给web 并接收用户的操作
			SimplePrinter.printNotice("It's your turn. Do you want to rob the landlord? Y/N enter EXIT 退出当前房间");
			//取值用户的操作
			String line = SimpleWriter.write("Y/N");
			//从clientcontains中取数值

			if(line.equalsIgnoreCase("EXIT")) {
				pushToServer(channel, ServerEventCode.CODE_CLIENT_EXIT);
			}else if(line.equalsIgnoreCase("Y")){
				pushToServer(channel, ServerEventCode.CODE_GAME_LANDLORD_ELECT, "TRUE");
			}else if(line.equalsIgnoreCase("N")){
				pushToServer(channel, ServerEventCode.CODE_GAME_LANDLORD_ELECT, "FALSE");
			}else{
				SimplePrinter.printNotice("Invalid options");
				call(channel, data);
			}
		}else { //在次卡住还没轮到的用户
			SimplePrinter.printNotice("It's " + map.get("nextClientNickname") + "'s turn. Please wait patiently for his/her confirmation !");
			//将此消息推送给web
		}
		
	}

}
