package com.software.pro.client.FightClient;


import com.software_pro.common.entity.WebData;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

//发向页面的数据阻塞队列, 可构建servlet
public class WebContains {
    public final static BlockingQueue<WebData> ServerDatas = new ArrayBlockingQueue<WebData>(11);
}
