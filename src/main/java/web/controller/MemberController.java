package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import web.dto.Memberdto;
import web.service.MemberService;

@Controller
public class MemberController {
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @Autowired
    MemberService memberService; // 메모리할당

    @GetMapping("/signup")
    public String signup(){
        //DTO 생성
        Memberdto memberdto= Memberdto.builder().mid("qwe").mpassword("ert").mname("dfg").build();
        memberService.signup(memberdto);
        return "signup";}
}
