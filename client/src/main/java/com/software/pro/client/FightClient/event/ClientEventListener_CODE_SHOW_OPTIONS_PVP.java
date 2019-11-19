package com.software.pro.client.FightClient.event;

import com.software.pro.client.FightClient.ClientContains;
import com.software_pro.common.entity.WebData;
import com.software_pro.common.enums.ClientEventCode;
import com.software_pro.common.enums.ServerEventCode;
import com.software_pro.common.printer.SimplePrinter;
import com.software_pro.common.printer.SimpleWriter;
import com.software_pro.common.utils.OptionsUtils;
import io.netty.channel.Channel;

public class ClientEventListener_CODE_SHOW_OPTIONS_PVP extends ClientEventListener{

	@Override
	public void call(Channel channel, String data) {
		SimplePrinter.printNotice("PVP: ");
		SimplePrinter.printNotice("1. Create Room");
		SimplePrinter.printNotice("2. Room List");
		SimplePrinter.printNotice("3. Join Room");
		SimplePrinter.printNotice("Please enter the number of options enter BACK 退出当前选项返回大厅");
		//String line = SimpleWriter.write("pvp");
		//第三次取值
		String pvp_select = "";
		String result = "";
		try{
			WebData webData = ClientContains.webDatas.take();
			pvp_select = webData.getKey();
			result = (String) webData.getValue();
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}

		if(result.equalsIgnoreCase("BACK")) {
			get(ClientEventCode.CODE_SHOW_OPTIONS).call(channel, data);
		}else {
			int choose = OptionsUtils.getOptions(result);                        //客户端和服务器的第三次交互对战选项
			
			if(choose == 1) {
				pushToServer(channel, ServerEventCode.CODE_ROOM_CREATE, null);
			}else if(choose == 2){
				pushToServer(channel, ServerEventCode.CODE_GET_ROOMS, null);
			}else if(choose == 3){    //表示加入一个房间
				SimplePrinter.printNotice("Please enter the room id you want to join (enter [BACK] return options list)");
				String line = "";
				try{
					WebData webData = ClientContains.webDatas.take();       //roomid  继续消费
					if(webData.getKey().equalsIgnoreCase("roomid"))
						line = (String)webData.getValue();
				}
				catch (Exception e){
					System.out.println(e.getMessage());
				}
				//line = SimpleWriter.write("roomid");
				
				if(line.equalsIgnoreCase("BACK")) {
					call(channel, data);
				}else {
					int option = OptionsUtils.getOptions(line);
					if(line == null || option < 1) {
						SimplePrinter.printNotice("Invalid options, please choose again：");
						call(channel, data);
					}else{
						//要加入的房间号
						pushToServer(channel, ServerEventCode.CODE_ROOM_JOIN, String.valueOf(option));
					}
				}
			}else {
				SimplePrinter.printNotice("Invalid option, please choose again：");
				call(channel, data);
			}
		}
		
	}



}
