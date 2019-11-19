package com.software.pro.client.FightClient.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
