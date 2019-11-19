package com.software.pro.client.FightClient.event;

import com.software.pro.client.FightClient.FightClient;
import com.software_pro.common.printer.SimplePrinter;
import io.netty.channel.Channel;

public class ClientEventListener_CODE_CLIENT_CONNECT extends ClientEventListener{

	@Override
	public void call(Channel channel, String data) {
		SimplePrinter.printNotice("Connection to server is successful. Welcome to landlords!!");

		FightClient.id = Integer.parseInt(data);
	}

}
