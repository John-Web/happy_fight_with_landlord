package com.software.pro.server.FightServer.servlet.event;

import com.software_pro.common.entity.Room;
import com.software_pro.common.enums.RoomEventCode;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public interface RoomEventListener {
    public void call(int client_id, String room_id, String data);

    public void call(String room_id);

    public final static Map<RoomEventCode, RoomEventListener> LISTENER_MAP = new HashMap<>();

    final static String LISTENER_PREFIX = "com.software.pro.server.FightServer.servlet.event.RoomEventListener_";

    @SuppressWarnings("unchecked")
    public static RoomEventListener get(RoomEventCode code){
        RoomEventListener listener = null;
        try {
            if(RoomEventListener.LISTENER_MAP.containsKey(code)){
                listener = RoomEventListener.LISTENER_MAP.get(code);
            }else{
                String eventListener = LISTENER_PREFIX + code.name();
                Class<RoomEventListener> listenerClass = (Class<RoomEventListener>) Class.forName(eventListener);
                try {
                    listener = listenerClass.getDeclaredConstructor().newInstance();
                } catch (InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
                RoomEventListener.LISTENER_MAP.put(code, listener);
            }
            return listener;
        }catch(ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return listener;
    }
}
