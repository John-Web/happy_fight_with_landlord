package com.software.pro.client.FightClient.event;

import com.software.pro.client.FightClient.ClientContains;
import com.software.pro.client.FightClient.FightClient;
import com.software.pro.client.FightClient.WebContains;
import com.software_pro.common.entity.Room;
import com.software_pro.common.entity.WebData;
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
		if(FightClient.id == joinClientId) {         //自己加入了别人的房间,在一个房间的加入事件中只有一个玩家会触发
			SimplePrinter.printNotice("You have joined room：" + map.get("roomId") + ". There are " + map.get("roomClientCount") + " players in the room now.");
			SimplePrinter.printNotice("Please wait for other players to join, start a good game when the room player reaches three !");
			//这里只将结果写回, 如果结果为成功,则在控制器调用服务器的api获取房间的所有客户端数据
			try{
				WebData webData = new WebData("join_room_type",0);
				WebContains.ServerDatas.put(webData);
			}
			catch (Exception e){
				e.printStackTrace();
			}

		}else {                                      //房间里加入了别人 这个需要前端自动触发
			SimplePrinter.printNotice(map.get("clientPlayername") + " joined room, the current number of room player is " + map.get("roomClientCount"));
//			try{
//				//先加入房间的玩家会等这些数值
//				WebData webData = new WebData("join_room_type",1);
//				WebData webDataId = new WebData("join_room_client_id",map.get("clientId").toString());
//				WebData webDataName = new WebData("join_room_client_name",map.get("clientPlayername").toString());
//				WebData webDataRole = new WebData("join_room_client_role",map.get("clientRole").toString());
//				WebContains.ServerDatas.put(webData);
//				WebContains.ServerDatas.put(webDataId);
//				WebContains.ServerDatas.put(webDataName);
//				WebContains.ServerDatas.put(webDataRole);
//
//			}
//			catch (Exception e){
//				e.printStackTrace();
//			}
			//当前房间得显示游客加入的画面
		}
		
	}



}
