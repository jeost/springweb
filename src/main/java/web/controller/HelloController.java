package web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import web.dto.Hellodto;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public Hellodto hello(){
        //Dto 생성
        Hellodto hellodto=Hellodto.builder().name("이름").amount(4321).build();
        return hellodto;
    }
}
