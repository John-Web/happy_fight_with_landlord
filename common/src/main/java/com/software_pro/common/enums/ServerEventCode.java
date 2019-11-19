package com.software_pro.common.enums;

import java.io.Serializable;

public enum ServerEventCode implements Serializable{

	CODE_CLIENT_EXIT("玩家退出"),
	
	CODE_CLIENT_PLAYERNAME_SET("设置昵称"),
	
	CODE_CLIENT_HEAD_BEAT("不出"),
	
	CODE_ROOM_CREATE("创建PVP房间"),
	
	CODE_GET_ROOMS("获取房间列表"),

	CODE_ROOM_JOIN("加入房间"),
	
	CODE_GAME_STARTING("游戏开始"),
	
	CODE_GAME_LANDLORD_ELECT("抢地主"),

	//出牌环节
	
	;
	
	private String msg;

	private ServerEventCode(String msg) {
		this.msg = msg;
	}

	public final String getMsg() {
		return msg;
	}

	public final void setMsg(String msg) {
		this.msg = msg;
	}
	
}
