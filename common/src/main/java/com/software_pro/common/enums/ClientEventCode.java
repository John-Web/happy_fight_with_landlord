package com.software_pro.common.enums;

import java.io.Serializable;

public enum ClientEventCode implements Serializable{

	CODE_CLIENT_PLAYERNAME_SET("设置昵称"),
	
	CODE_CLIENT_EXIT("客户端退出"),

	CODE_CLIENT_CONNECT("客户端加入成功"),
	
	CODE_SHOW_OPTIONS("全局选项列表"),
	
	CODE_SHOW_OPTIONS_PVP("玩家对战选项"),
	
	CODE_SHOW_OPTIONS_PVE("人机对战选项"),
	
	CODE_SHOW_ROOMS("展示房间列表"),
	
	CODE_SHOW_POKERS("展示Poker"),
	
	CODE_ROOM_CREATE_SUCCESS("创建房间成功"),
	
	CODE_ROOM_JOIN_SUCCESS("加入房间成功"),
	
	CODE_ROOM_JOIN_FAIL_BY_FULL("房间人数已满"),
	
	CODE_ROOM_JOIN_FAIL_BY_INEXIST("加入-房间不存在"),

	CODE_GAME_STARTING("开始游戏"),
	
	CODE_GAME_LANDLORD_ELECT("抢地主"),
	
	CODE_GAME_LANDLORD_CONFIRM("地主确认"),          //确认地主
	
	CODE_GAME_LANDLORD_CYCLE("地主一轮确认结束"),
	;
	
	private String msg;

	private ClientEventCode(String msg) {
		this.msg = msg;
	}

	public final String getMsg() {
		return msg;
	}

	public final void setMsg(String msg) {
		this.msg = msg;
	}
	
}
