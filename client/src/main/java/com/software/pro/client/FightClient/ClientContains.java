package com.software.pro.client.FightClient;


import com.software_pro.common.entity.WebData;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

//客户端有一个控制器,用于接收用户所有的操作数据
//监听事件会从容器中取值
//接收页面操作,阻塞队列
public class ClientContains {

    public final static BlockingQueue<WebData> webDatas = new ArrayBlockingQueue<WebData>(11);
}
