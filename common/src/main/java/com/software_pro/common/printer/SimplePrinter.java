package com.software_pro.common.printer;

import com.software_pro.common.entity.Poker;
import com.software_pro.common.helper.PokerHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimplePrinter {
    private final static SimpleDateFormat FORMAT = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

    public static void serverLog(String msg) {
        System.out.println(FORMAT.format(new Date()) + "-> " + msg);
    }

    public static void printNotice(String msg) {
        System.out.println(msg);
    }

    public static void printNotice(String msgKey, String locale) {
        //TODO : read locale
        Map<String, Map<String, String>> map = new HashMap<String,Map<String, String>>();
        map.put("english", new HashMap<String, String>());
        map.get("eng").put("caterpillar", "caterpillar's message!!");

        System.out.println(map.get(locale).get(msgKey));
    }
    public static void printPokers(List<Poker>pokers){
        PokerHelper.printPoker(pokers);
    }
}
