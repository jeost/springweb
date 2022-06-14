package web.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import web.dto.RoomDto;
import web.service.RoomService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller // 해당 클래스를 템플릿영역으로 사용
@RequestMapping("/room") // 요청 매핑(room)
public class RoomController {
    @GetMapping("/write") // 1. 등록 페이지 이동
    public String write(){
        return "room/write"; // templates->room->write 경로로 접근
    }

    @Autowired
    private RoomService roomService;

    @PutMapping("/write") // 2. 등록 처리
    @ResponseBody
    public boolean write_save(RoomDto roomDto){
        try{
            roomService.room_save(roomDto); // 서비스에 전달
            //Mapping 사용시에는 요청 변수의 이름과 DTO 필드명이 동일할 경우 자동으로 주입 가능
            //DTO 없으면 @RequestParam 같은 거 사용
            return true;
        }catch (Exception e){e.printStackTrace();}
        return false;
    }
    //3. 방 목록 페이지 이동
    @GetMapping("/list")
    public String list(){
        return "room/list";
    }
    @GetMapping("/roomlist")
    public void roomlist(HttpServletResponse response){
        JSONArray jsonArray=roomService.room_list();
        JSONObject object=new JSONObject();
        object.put("positions",jsonArray);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        try {
            response.getWriter().print(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
