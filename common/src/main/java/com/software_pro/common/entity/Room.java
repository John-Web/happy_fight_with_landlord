package com.software_pro.common.entity;

import com.software_pro.common.enums.RoomStatus;
import com.software_pro.common.enums.RoomType;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class Room {
    private int id;                            //房间号

    private String roomOwner;                  //房主

    private RoomStatus status;

    private RoomType type;

    private Map<Integer, ClientSide> clientSideMap;       //map

    private LinkedList<ClientSide> clientSideList;        //list

    private int landlordId = -1;                          //地主id --> Map<Integer, ClientSide>

    private List<Poker> landlordPokers;                   //三张扑克

    private int lastSellClient = -1;                      //上一个玩家

    private int currentSellClient = -1;                   //当前玩家

    private long lastFlushTime;                           //房间状态改变时间

    private long createTime;                              //房间创建时间

    public Room() {
    }

    public Room(int id) {
        this.id = id;
        this.clientSideMap = new ConcurrentSkipListMap<Integer, ClientSide>();
        this.clientSideList = new LinkedList<ClientSide>();
        this.status = RoomStatus.BLANK;
    }

    public final long getCreateTime() {
        return createTime;
    }

    public final void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public final RoomType getType() {
        return type;
    }

    public final void setType(RoomType type) {
        this.type = type;
    }

    public final int getCurrentSellClient() {
        return currentSellClient;
    }

    public final void setCurrentSellClient(int currentSellClient) {
        this.currentSellClient = currentSellClient;
    }

    public long getLastFlushTime() {
        return lastFlushTime;
    }

    public void setLastFlushTime(long lastFlushTime) {
        this.lastFlushTime = lastFlushTime;
    }

    public int getLastSellClient() {
        return lastSellClient;
    }

    public void setLastSellClient(int lastSellClient) {
        this.lastSellClient = lastSellClient;
    }

    public int getLandlordId() {
        return landlordId;
    }

    public void setLandlordId(int landlordId) {
        this.landlordId = landlordId;
    }

    public LinkedList<ClientSide> getClientSideList() {
        return clientSideList;
    }

    public void setClientSideList(LinkedList<ClientSide> clientSideList) {
        this.clientSideList = clientSideList;
    }

    public List<Poker> getLandlordPokers() {
        return landlordPokers;
    }

    public void setLandlordPokers(List<Poker> landlordPokers) {
        this.landlordPokers = landlordPokers;
    }

    public final String getRoomOwner() {
        return roomOwner;
    }

    public final void setRoomOwner(String roomOwner) {
        this.roomOwner = roomOwner;
    }

    public final int getId() {
        return id;
    }

    public final void setId(int id) {
        this.id = id;
    }

    public final RoomStatus getStatus() {
        return status;
    }

    public final void setStatus(RoomStatus status) {
        this.status = status;
    }

    public final Map<Integer, ClientSide> getClientSideMap() {
        return clientSideMap;
    }

    public final void setClientSideMap(Map<Integer, ClientSide> clientSideMap) {
        this.clientSideMap = clientSideMap;
    }
}
