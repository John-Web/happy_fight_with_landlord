package com.software.pro.client.FightClient;

import com.software_pro.common.printer.SimplePrinter;
import com.software_pro.common.printer.SimpleWriter;
import com.software_pro.common.utils.StreamUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.nico.noson.Noson;
import org.nico.noson.entity.NoType;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

//从前端接收用户参数
@Controller
@Component
@RequestMapping("/fightclient")
public class FightClient implements ApplicationRunner {

    public static int id = -1;

    public static String serverAddress = "localhost";

    public static int port = 8080;

    public void ClientStart() throws Exception{
//        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
//
//        try{
//            Bootstrap bootstrap = new Bootstrap();
//            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
//                    .handler(new FightClientInializer());
//
//            Channel channel = bootstrap.connect("localhost",8080).sync().channel();
//
//            //标准输入
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//
//            //利用死循环，不断读取客户端在控制台上的输入内容
//            //读取用户的前端操作
//            for (;;){
//                String message = bufferedReader.readLine();
//                if(!message.equals("CLIENT_CLOSE"))
//                    channel.writeAndFlush(message +"\r\n");
//                else
//                    channel.close();
//            }
//
//        }finally {
//            eventLoopGroup.shutdownGracefully();
//        }





//        if(args != null && args.length > 0) {
//            for(int index = 0; index < args.length; index = index + 2) {
//                if(index + 1 < args.length) {
//                    if(args[index].equalsIgnoreCase("-p") || args[index].equalsIgnoreCase("-port")) {
//                        port = Integer.valueOf(args[index + 1]);
//                    }
//                    if(args[index].equalsIgnoreCase("-h") || args[index].equalsIgnoreCase("-host")) {
//                        serverAddress = args[index + 1];
//                    }
//                }
//            }
//        }
//
//        if(serverAddress == null || port == 0){
//            String serverInfo = StreamUtils.convertToString(new URL("https://raw.githubusercontent.com/ainilili/ratel/master/serverlist.json"));
//            List<String> serverAddressList = Noson.convert(serverInfo, new NoType<List<String>>() {});
//            SimplePrinter.printNotice("Please select a server:");
//            for(int i = 0; i < serverAddressList.size(); i++) {
//                SimplePrinter.printNotice((i+1) + ". " + serverAddressList.get(i));
//            }
//            int serverPick = Integer.parseInt(SimpleWriter.write("option"));
//            while(serverPick<1 || serverPick>serverAddressList.size()){
//                try {
//                    SimplePrinter.printNotice("The server address does not exist!");
//                    serverPick = Integer.parseInt(SimpleWriter.write("option"));
//                }catch(NumberFormatException e){}
//            }
//            serverAddress = serverAddressList.get(serverPick-1);
//            String[] elements = serverAddress.split(":");
//            serverAddress = elements[0];
//            port = Integer.parseInt(elements[1]);
//        }

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new FightClientInializer());
            SimplePrinter.printNotice("Connecting to " + serverAddress + ":" + port);
            Channel channel = bootstrap.connect(serverAddress, port).sync().channel();
            channel.closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }

    }

    public void run(ApplicationArguments args) throws Exception {
        ClientStart();
    }
}
