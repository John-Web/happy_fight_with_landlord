package com.software.pro.client.FightClient.Controller;

import com.software.pro.client.FightClient.ClientContains;
import com.software.pro.client.FightClient.WebContains;
import com.software_pro.common.entity.WebData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/DataCollection")
public class DataCollection {

    @RequestMapping("/client_service")
    @ResponseBody
    public Map<String, Object> sent_to_contains(@RequestParam("key") String key, @RequestParam("value") Object value){
        WebData webData = new WebData(key,value);
        try {
            ClientContains.webDatas.put(webData);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        //这里得到用户对应操作的结果     比如请求创建房间得到 bool值和房间创建消息
        Map<String, Object> map = new HashMap<>();
        if(webData.getKey()=="pvp_select") {
            try {
                WebData webData_select_result = WebContains.ServerDatas.take();
                WebData webData_select_result_message = WebContains.ServerDatas.take();
                map.put("Create_room_result", webData_select_result.getValue());
                map.put("Create_room_result_message", webData_select_result_message.getValue());
                System.out.println("123");
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("456");
            }
            System.out.println("789");
        }
        else{
            map.put("state_code",200);
        }
        return map;
    }
}
