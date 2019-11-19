package com.software.pro.client.FightClient.event;

import com.software.pro.client.FightClient.ClientContains;
import com.software_pro.common.entity.WebData;
import com.software_pro.common.enums.ClientEventCode;
import com.software_pro.common.printer.SimplePrinter;
import com.software_pro.common.printer.SimpleWriter;
import com.software_pro.common.utils.OptionsUtils;
import io.netty.channel.Channel;

public class ClientEventListener_CODE_SHOW_OPTIONS extends ClientEventListener{

	@Override
	public void call(Channel channel, String data) {
		SimplePrinter.printNotice("Options: ");
		SimplePrinter.printNotice("1. PvP");                //玩家对战
		SimplePrinter.printNotice("2. PvR");                //人机对战
		SimplePrinter.printNotice("Please enter the number of options !!!enter EXIT 退出)");
		String line = "";
		try{
			WebData webData = ClientContains.webDatas.take();
			if(webData.getKey().equalsIgnoreCase("hall_Select"))
				line = (String)webData.getValue();
		}
		catch (Exception e){
			System.out.println(e.getMessage());
		}
		if(line.equalsIgnoreCase("EXIT")) {
			System.exit(0);
		}else {
			int choose = OptionsUtils.getOptions(line);            //前后端的第二次交互,大厅有四个选项
			
			if(choose == 1) {
				get(ClientEventCode.CODE_SHOW_OPTIONS_PVP).call(channel, data);
			}else if(choose == 2){
				//人机对战
			}
			else if(choose == 3){
				//查看自己的战绩
			}
			else if(choose == 4){
				//可以增加设置
			}
			else {
				SimplePrinter.printNotice("Invalid option, please choose again：");
				call(channel, data);
			}
		}
	}



}
