package web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import web.dto.Hellodto;

@Controller
public class IndexController {
    @GetMapping("/")
    public String Index(Model model){
        //Dto 생성
        Hellodto hellodto=Hellodto.builder().
        name("엄").amount(1234).build();
        //해당 템플릿에 데이터 보내기
        model.addAttribute("data",hellodto);
        return "main"; // html 파일명
    }
}
