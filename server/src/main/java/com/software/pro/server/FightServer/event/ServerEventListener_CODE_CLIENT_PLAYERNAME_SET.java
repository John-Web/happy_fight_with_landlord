package com.software.pro.server.FightServer.event;

import com.software.pro.server.FightServer.ServerContains;
import com.software_pro.common.channel.ChannelUtils;
import com.software_pro.common.entity.ClientSide;
import com.software_pro.common.enums.ClientEventCode;
import com.software_pro.common.helper.MapHelper;

public class ServerEventListener_CODE_CLIENT_PLAYERNAME_SET implements ServerEventListener{
    public static final int NICKNAME_MAX_LENGTH = 10;

    @Override
    public void call(ClientSide client, String nickname) {

        if (nickname.trim().length() > NICKNAME_MAX_LENGTH) {
            String result = MapHelper.newInstance().put("invalidLength", nickname.trim().length()).json();
            ChannelUtils.pushToClient(client.getChannel(), ClientEventCode.CODE_CLIENT_PLAYERNAME_SET, result);
        }else{
            ServerContains.CLIENT_SIDE_MAP.get(client.getId()).setOwner_name(nickname);
            ChannelUtils.pushToClient(client.getChannel(), ClientEventCode.CODE_SHOW_OPTIONS, null);
        }
    }
}
