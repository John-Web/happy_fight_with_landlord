package com.software.pro.server.FightServer.api;

import com.software.pro.server.FightServer.ServerContains;
import com.software_pro.common.entity.Room;
import com.software_pro.common.helper.MapHelper;
import org.nico.noson.Noson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/landlord_servlet")
public class Landlord_Servlet {

    @RequestMapping(value = "/room_servlet")
    @ResponseBody
    public String getRoom_Info(HttpServletRequest request, @RequestParam(name = "room_id") int room_id){
        Room room = ServerContains.getRoom(room_id);
        String result = MapHelper.newInstance()
                .put("roomOwner", room.getRoomOwner())
                .put("clientsideList",room.getClientSideList())
                .put("roomClientCount",room.getClientSideList().size())
                .json();
        return result;
    }

    @RequestMapping(value = "/roomlist_servlet")
    @ResponseBody
    public String getRoomList_Info(HttpServletRequest request){
        List<Map<String, Object>> roomList = new ArrayList<>(ServerContains.getRoomMap().size());
        for(Map.Entry<Integer, Room> entry: ServerContains.getRoomMap().entrySet()) {
            Room room = entry.getValue();
            roomList.add(MapHelper.newInstance()
                    .put("roomId", room.getId())
                    .put("roomOwner", room.getRoomOwner())
                    .put("roomClientCount", room.getClientSideList().size())
                    .put("roomType", room.getType())
                    .map());
        }
        return Noson.reversal(roomList);
    }
}
