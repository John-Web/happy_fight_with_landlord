package com.software.pro.client.FightClient;

import com.software_pro.common.entity.ClientTransferData;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

import javax.swing.*;
import java.util.concurrent.TimeUnit;

public class FightClientInializer  extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //ChannelPipeline pipeline = ch.pipeline();

//        pipeline.addLast(new DelimiterBasedFrameDecoder(4096, Delimiters.lineDelimiter()));
//        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
//        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
//        pipeline.addLast(new FightClientHandler());
        ch.pipeline()
                .addLast(new IdleStateHandler(0, 4, 0, TimeUnit.SECONDS))
                .addLast(new ProtobufVarint32FrameDecoder())
                .addLast(new ProtobufDecoder(ClientTransferData.ClientTransferDataProtoc.getDefaultInstance()))
                .addLast(new ProtobufVarint32LengthFieldPrepender())
                .addLast(new ProtobufEncoder())
                .addLast(new SecondProtobufCodec())
                .addLast(new FightClientHandler());
    }
}
