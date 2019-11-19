package com.software.pro.server.FightServer;

import com.software.pro.server.FightServer.event.ServerEventListener;
import com.software_pro.common.channel.ChannelUtils;
import com.software_pro.common.entity.ServerTransferData;
import com.software_pro.common.enums.ClientEventCode;
import com.software_pro.common.enums.ClientRole;
import com.software_pro.common.enums.ClientStatus;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.GlobalEventExecutor;

import com.software_pro.common.entity.ClientSide;
import com.software_pro.common.enums.ServerEventCode;
import com.software_pro.common.printer.SimplePrinter;

public class FightServerHandler extends ChannelInboundHandlerAdapter {
    //保留所有与服务器建立连接的channel对象，这边的GlobalEventExecutor在写博客的时候解释一下，看其doc
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 服务器端收到任何一个客户端的消息都会触发这个方法
     * 连接的客户端向服务器端发送消息，那么其他客户端都收到此消息，自己收到【自己】+消息
     */
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();

        channelGroup.forEach(ch -> {
            if(channel !=ch){
                ch.writeAndFlush(channel.remoteAddress() +" 发送的消息:" +msg+" \n");
            }else{
                ch.writeAndFlush(" 【自己】"+msg +" \n");
            }
        });
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{
        if(msg instanceof ServerTransferData.ServerTransferDataProtoc) {
            ServerTransferData.ServerTransferDataProtoc serverTransferData = (ServerTransferData.ServerTransferDataProtoc) msg;
            ServerEventCode code = ServerEventCode.valueOf(serverTransferData.getCode());
            if(code != null && code != ServerEventCode.CODE_CLIENT_HEAD_BEAT) {   //向客户端发送心跳
                ClientSide client = ServerContains.CLIENT_SIDE_MAP.get(getId(ctx.channel()));
                SimplePrinter.serverLog(client.getId() + " | " + client.getOwner_name() + " do:" + code.getMsg());
                ServerEventListener.get(code).call(client, serverTransferData.getData());
            }
        }
    }

    //表示服务端与客户端连接建立
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();  //其实相当于一个connection

        /**
         * 调用channelGroup的writeAndFlush其实就相当于channelGroup中的每个channel都writeAndFlush
         *
         * 先去广播，再将自己加入到channelGroup中
         */
        channelGroup.writeAndFlush(" 【服务器】 -" +channel.remoteAddress() +" 加入\n");
        channelGroup.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush(" 【服务器】 -" +channel.remoteAddress() +" 离开\n");

        //验证一下每次客户端断开连接，连接自动地从channelGroup中删除调。
        System.out.println(channelGroup.size());
        //当客户端和服务端断开连接的时候，下面的那段代码netty会自动调用，所以不需要人为的去调用它
        //channelGroup.remove(channel);
    }

    //连接处于活动状态
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() +" 上线了");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() +" 下线了");
    }

    //*****************************************************************************************************************//
    public void channelRegistered (ChannelHandlerContext ctx) throws Exception{
        //检测到连接时从这里开始

        Channel channel = ctx.channel();
        //初始化客户端
        ClientSide clientSide = new ClientSide(getId(channel), ClientStatus.TO_CHOOSE, channel);
        clientSide.setOwner_name(String.valueOf(clientSide.getId()));
        clientSide.setRole(ClientRole.PLAYER);

        ServerContains.CLIENT_SIDE_MAP.put(clientSide.getId(), clientSide);
        SimplePrinter.serverLog("Client connect to the server：" + clientSide.getId());


        //开始
        ChannelUtils.pushToClient(channel, ClientEventCode.CODE_CLIENT_CONNECT, String.valueOf(clientSide.getId()));
        ChannelUtils.pushToClient(channel, ClientEventCode.CODE_CLIENT_PLAYERNAME_SET, null);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if(cause instanceof java.io.IOException) {
            clientOfflineEvent(ctx.channel());
        }else {
            SimplePrinter.serverLog("ERROR：" + cause.getMessage());
            cause.printStackTrace();
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                try{
                    clientOfflineEvent(ctx.channel());
                    ctx.channel().close();
                }catch(Exception e){
                }
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    private int getId(Channel channel){
        String longId = channel.id().asLongText();
        Integer clientId = ServerContains.CHANNEL_ID_MAP.get(longId);
        if(null == clientId){
            clientId = ServerContains.getClientId();
            ServerContains.CHANNEL_ID_MAP.put(longId, clientId);
        }
        return clientId;
    }

    private void clientOfflineEvent(Channel channel){
        int clientId = getId(channel);
        ClientSide client = ServerContains.CLIENT_SIDE_MAP.get(clientId);
        if(client != null) {
            SimplePrinter.serverLog("Has client exit to the server：" + clientId + " | " + client.getOwner_name());
            ServerEventListener.get(ServerEventCode.CODE_CLIENT_EXIT).call(client, null);
        }
    }
}
