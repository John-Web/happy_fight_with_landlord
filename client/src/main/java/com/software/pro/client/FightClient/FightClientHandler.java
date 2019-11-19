package com.software.pro.client.FightClient;

import com.software.pro.client.FightClient.event.ClientEventListener;
import com.software_pro.common.entity.ClientTransferData;
import com.software_pro.common.enums.ClientEventCode;
import com.software_pro.common.printer.SimplePrinter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;


public class FightClientHandler extends ChannelInboundHandlerAdapter {
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        //将收到的msg传递给前端
        System.out.println(msg);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof ClientTransferData.ClientTransferDataProtoc) {
            ClientTransferData.ClientTransferDataProtoc clientTransferData = (ClientTransferData.ClientTransferDataProtoc) msg;
            if(clientTransferData.getInfo() != null && ! clientTransferData.getInfo().isEmpty()) {
                SimplePrinter.printNotice(clientTransferData.getInfo());
            }
            ClientEventCode code = ClientEventCode.valueOf(clientTransferData.getCode());
            if(code != null) {
                ClientEventListener.get(code).call(ctx.channel(), clientTransferData.getData());
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
