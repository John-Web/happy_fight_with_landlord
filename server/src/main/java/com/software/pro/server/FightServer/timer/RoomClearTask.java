package com.software.pro.server.FightServer.timer;

import java.util.Map;
import java.util.TimerTask;

public class RoomClearTask extends TimerTask {
    //The room wait time of after create is 100s
    private static long waitingStatusInterval = 1000 * 100;

    //The room starting destroy time is 100s
    private static long startingStatusInterval = 1000 * 100;

    //The room live  time is 600s
    private static long liveTime = 1000 * 60 * 20;

    public void doing(){
        System.out.println("GGGGGGGG");
        System.out.println("JJJJJJJJ");
    }



    public void run(){
        try {
            doing();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
