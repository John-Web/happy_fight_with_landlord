package com.software.pro.client.FightClient.Controller;

import com.software_pro.common.entity.ClientSide;
import com.software_pro.common.entity.Poker;
import com.software_pro.common.entity.Room;
import com.software_pro.common.helper.MapHelper;
import org.nico.noson.Noson;
import org.nico.noson.entity.NoType;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
@RequestMapping("/")
public class ClientPagecontroller {

    @RequestMapping("/login")
    public String tologinpage(){
        return "login_page";
    }

    @RequestMapping("/hall")
    public String tohallpage(){
        return "hall_page";
    }

    @RequestMapping("/pvp_page")
    public String topvp_page(){return "pvp_page";}

    @RequestMapping("/pve_page")
    public String topve_page(){return "pve_page";}

    @RequestMapping("/room_page")
    public ModelAndView toRoom_page(@RequestParam(name = "room_id") int room_id) {                      //房间id 房主用户名, 房间人数, 当前玩家信息
        //
        ModelAndView modelAndView=new ModelAndView();
        //需要服务器的api获取指定房间的信息
        MultiValueMap<String, Object> request = new LinkedMultiValueMap<>();
        request.add("room_id", room_id);

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.postForObject("http://localhost:8090/landlord_servlet/room_servlet",request,String.class);
        System.out.println(result);
        Map<String, Object> map = MapHelper.parser(result);  //房主, 客户端s

        if(map.containsKey("clientsideList")){
            ArrayList<String>list = new ArrayList<>();
            LinkedList<ClientSide> clientSideLinkedList = Noson.convert(map.get("clientsideList"), new NoType<LinkedList<ClientSide>>() {});
            for(ClientSide clientSide:clientSideLinkedList) {
                System.out.println("id: " + clientSide.getId() + " name: " + clientSide.getOwner_name());
                list.add(clientSide.getRole().toString());
                list.add(String.valueOf(clientSide.getId()));
                list.add(clientSide.getOwner_name());
            }
            modelAndView.addObject("room_id",room_id);
            modelAndView.addObject("client_id",clientSideLinkedList.get(clientSideLinkedList.size()-1).getId());
            modelAndView.addObject("roomClientCount",clientSideLinkedList.size());
            modelAndView.addObject("clients_data",list);
        }
        //根据room_id获取房间信息 ,存map返回room_page
        modelAndView.setViewName("room_page");
        return modelAndView;
    }
}
