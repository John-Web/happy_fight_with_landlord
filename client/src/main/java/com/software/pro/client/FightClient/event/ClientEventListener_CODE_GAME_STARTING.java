package com.software.pro.client.FightClient.event;

import com.software.pro.client.FightClient.ClientContains;
import com.software.pro.client.FightClient.WebContains;
import com.software_pro.common.entity.Poker;
import com.software_pro.common.entity.WebData;
import com.software_pro.common.enums.ClientEventCode;
import com.software_pro.common.helper.MapHelper;
import com.software_pro.common.printer.SimplePrinter;
import io.netty.channel.Channel;
import org.nico.noson.Noson;
import org.nico.noson.entity.NoType;

import java.util.List;
import java.util.Map;

public class ClientEventListener_CODE_GAME_STARTING extends ClientEventListener{

	@Override
	public void call(Channel channel, String data) {
		
		Map<String, Object> map = MapHelper.parser(data);
		
		SimplePrinter.printNotice("Game starting !!");
		
		List<Poker> pokers = Noson.convert(map.get("pokers"), new NoType<List<Poker>>() {});
		
		SimplePrinter.printNotice("");
		SimplePrinter.printNotice("Your pokers are");
		//将牌发送给web页面
		SimplePrinter.printPokers(pokers);
		//可持久化存储在redis中
		try {
			WebContains.ServerDatas.put(new WebData("Game starting message", "Game starting !! Your pokers are"));
			WebContains.ServerDatas.put(new WebData("pokers",pokers));
		}
		catch (Exception e){
			System.out.println(e.getMessage());
		}
		get(ClientEventCode.CODE_GAME_LANDLORD_ELECT).call(channel, data);
	}

}
