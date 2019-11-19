package com.software_pro.common.entity;

import io.netty.channel.Channel;

import com.software_pro.common.enums.ClientRole;
import com.software_pro.common.enums.ClientStatus;
import com.software_pro.common.enums.ClientType;

import java.util.List;

public class ClientSide {
    private String owner_name;
    private int id;
    private int roomId;
    private List<Poker> pokers;
    private ClientStatus status;
    private ClientRole role;
    private ClientType type;
    private ClientSide next;
    private ClientSide pre;
    private transient Channel channel;


    public ClientSide() {}

    public ClientSide(int id, ClientStatus status, Channel channel) {
        this.id = id;
        this.status = status;
        this.channel = channel;
    }

    public void init() {
        roomId = 0;
        pokers = null;
        status = ClientStatus.TO_CHOOSE;
        type = null;
        next = null;
        pre = null;
    }


    public String getOwner_name() {
        return owner_name;
    }
    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Channel getChannel() {
        return channel;
    }
    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public ClientRole getRole() {
        return role;
    }
    public void setRole(ClientRole role) {
        this.role = role;
    }

    public ClientSide getNext() {
        return next;
    }
    public void setNext(ClientSide next) {
        this.next = next;
    }

    public ClientSide getPre() {
        return pre;
    }
    public void setPre(ClientSide pre) {
        this.pre = pre;
    }

    public ClientStatus getStatus() {
        return status;
    }
    public void setStatus(ClientStatus status) {
        this.status = status;
    }

    public List<Poker> getPokers() {
        return pokers;
    }
    public void setPokers(List<Poker> pokers) {
        this.pokers = pokers;
    }

    public ClientType getType() {
        return type;
    }
    public void setType(ClientType type) {
        this.type = type;
    }

    public int getRoomId() {
        return roomId;
    }
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
}
