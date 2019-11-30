package com.software.pro.server.FightServer.servlet;

import com.software_pro.common.entity.ClientSide;
import com.software_pro.common.entity.Room;
import com.software_pro.common.entity.WebData;
import javafx.util.Pair;

import javax.websocket.Session;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;

//存放房间服务器发给全局服务器的信息
public class RoomsContains {
    public final static Map<Integer, BlockingQueue<WebData>> Rooms_Messages_MAP = new ConcurrentSkipListMap<>();


    public final static Map<String, CopyOnWriteArrayList<Room_servlet>> Rooms_Sessions = new HashMap<String, CopyOnWriteArrayList<Room_servlet>>();      //所有房间的连接信息
    public final static Map<String,Integer>Rooms_OnLineCount = new HashMap<>();
    public final static Map<String,Integer>Rooms_Players_Status = new HashMap<>();
    public final static Map<String, LinkedList<ClientSide>>Rooms_ClientSides = new HashMap<>();    //保存即将开始游戏的客户端列表
    public final static Map<String, Map<String,ClientSide>>Rooms_ClientsMap = new HashMap<>();     //一个房间对应的clientside map
    public final static Map<String, Room>Rooms = new HashMap<>();            //保存即将开始游戏的房间
    public final static Map<String, Session>Clients_Sessions = new HashMap<>();
    public final static Map<String,Map<Integer,Integer>>Rooms_Clients_Index=new HashMap<>();       //客户端连接在房间中的序号
    public final static Map<String,Integer>Rooms_Index_Of_Elect = new HashMap<>();                 //叫地主的玩家 只能为0 1 2
    public final static Map<String,Integer>Rooms_Index_Of_NOT_Elect = new HashMap<>();             //不叫地主的玩家 按位运算,如果玩家不叫地主在这里会有检测
                                                                                                   //0没有叫地主, 1不叫地主
    public final static Map<String,Integer>Rooms_INDEX_Of_Grab = new HashMap<>();                     //最后一个抢地主的玩家

    public final static void removeRoomMessage(int id){
        Rooms_Messages_MAP.remove(id);
    }

    public final static void addRoom(Room room){
        Rooms_Messages_MAP.put(room.getId(),new ArrayBlockingQueue<WebData>(41));      //房间里所有的交互信息
    }
}
