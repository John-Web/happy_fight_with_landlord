package com.software.pro.server.FightServer;


import com.software.pro.server.FightServer.servlet.RoomsContains;
import com.software_pro.common.entity.ClientSide;
import com.software_pro.common.entity.Room;
import com.software_pro.common.entity.WebData;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ServerContains {    // 内存

    public static int port = 8080;

    private final static Map<Integer, Room> ROOM_MAP = new ConcurrentSkipListMap<>();

    public final static Map<Integer, ClientSide> CLIENT_SIDE_MAP = new ConcurrentSkipListMap<>();

    public final static Map<String, Integer> CHANNEL_ID_MAP = new ConcurrentHashMap<>();

    //该变量包含服务器中所有房间里人员交互产生的 WebData
    public final static Map<Integer,BlockingQueue<WebData>>Server_Room_Data = new ConcurrentSkipListMap<>();

    private final static AtomicInteger CLIENT_ATOMIC_ID = new AtomicInteger(1);

    private final static AtomicInteger SERVER_ATOMIC_ID = new AtomicInteger(1);

    public final static int getClientId() {
        return CLIENT_ATOMIC_ID.getAndIncrement();
    }

    public final static int getServerId() {
        return SERVER_ATOMIC_ID.getAndIncrement();
    }

    public final static ThreadPoolExecutor THREAD_EXCUTER = new ThreadPoolExecutor(500, 500, 0, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>());


    public final static Room getRoom(int id){
        Room room = ROOM_MAP.get(id);
        if(room != null){
            room.setLastFlushTime(System.currentTimeMillis());
        }
        return room;
    }

    public final static Map<Integer, Room> getRoomMap(){
        return ROOM_MAP;
    }

    public final static Room removeRoom(int id){
        Server_Room_Data.remove(id);
        ROOM_MAP.remove(id);
        RoomsContains.removeRoomMessage(id);
        return ROOM_MAP.remove(id);
    }

    public final static Room addRoom(Room room){
        Server_Room_Data.put(room.getId(),new ArrayBlockingQueue<WebData>(27));      //房间里所有的交互信息
        ROOM_MAP.put(room.getId(),room);
        RoomsContains.addRoom(room);
        return ROOM_MAP.put(room.getId(), room);
    }

}
