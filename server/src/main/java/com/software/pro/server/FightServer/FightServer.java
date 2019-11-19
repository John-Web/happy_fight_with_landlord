package com.software.pro.server.FightServer;

import com.software.pro.server.FightServer.timer.RoomClearTask;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.software_pro.common.printer.SimplePrinter;
import java.net.InetSocketAddress;
import java.util.Timer;

@Controller
@Component
@RequestMapping("/fightserver")
public class FightServer implements ApplicationRunner {


    public void ServerStart() throws Exception{
//        EventLoopGroup bossGroup = new NioEventLoopGroup();
//        EventLoopGroup wokerGroup = new NioEventLoopGroup();
//
//        try{
//            ServerBootstrap serverBootstrap = new ServerBootstrap();
//            serverBootstrap.group(bossGroup,wokerGroup).channel(NioServerSocketChannel.class)
//                    .childHandler(new FightServerInializer());
//
//            ChannelFuture channelFuture = serverBootstrap.bind(8080).sync();
//            channelFuture.channel().closeFuture().sync();
//        }finally {
//            bossGroup.shutdownGracefully();
//            wokerGroup.shutdownGracefully();
//        }

        EventLoopGroup parentGroup = Epoll.isAvailable() ? new EpollEventLoopGroup() : new NioEventLoopGroup();
        EventLoopGroup childGroup = Epoll.isAvailable() ? new EpollEventLoopGroup() : new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(parentGroup, childGroup)
                    .channel(Epoll.isAvailable() ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(ServerContains.port))
                    .childHandler(new FightServerInializer());

            ChannelFuture f = bootstrap .bind().sync();

            SimplePrinter.serverLog("The server was successfully started on port " + ServerContains.port);

            ServerContains.THREAD_EXCUTER.execute(() -> {
                Timer timer=new Timer();
                timer.schedule(new RoomClearTask(),0L,1000L);
            });
            f.channel().closeFuture().sync();
        } finally {
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }
    }

    public void run(ApplicationArguments args) throws Exception {
        ServerStart();
    }
}
