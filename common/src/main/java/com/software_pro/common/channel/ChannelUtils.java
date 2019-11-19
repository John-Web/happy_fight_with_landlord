package com.software_pro.common.channel;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

import com.software_pro.common.entity.ClientTransferData;
import com.software_pro.common.entity.ServerTransferData;
import com.software_pro.common.enums.ClientEventCode;
import com.software_pro.common.enums.ServerEventCode;

public class ChannelUtils {
    public ChannelUtils() {
    }

    public static void pushToClient(Channel channel, ClientEventCode code, String data) {
        pushToClient(channel, code, data, (String)null);
    }

    public static void pushToClient(Channel channel, ClientEventCode code, String data, String info) {
        if (channel != null) {
            ClientTransferData.ClientTransferDataProtoc.Builder clientTransferData = ClientTransferData.ClientTransferDataProtoc.newBuilder();
            if (code != null) {
                clientTransferData.setCode(code.toString());
            }

            if (data != null) {
                clientTransferData.setData(data);
            }

            if (info != null) {
                clientTransferData.setInfo(info);
            }
            channel.writeAndFlush(clientTransferData);
        }

    }

    public static ChannelFuture pushToServer(Channel channel, ServerEventCode code, String data) {
        ServerTransferData.ServerTransferDataProtoc.Builder serverTransferData = ServerTransferData.ServerTransferDataProtoc.newBuilder();
        if (code != null) {
            serverTransferData.setCode(code.toString());
        }

        if (data != null) {
            serverTransferData.setData(data);
        }
        System.out.println(serverTransferData);
        return channel.writeAndFlush(serverTransferData);
    }
}
