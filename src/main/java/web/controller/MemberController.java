package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.dto.Hellodto;
import web.dto.Memberdto;
import web.service.MemberService;

@Controller
@RequestMapping("/member")
public class MemberController {

    //로그인 페이지 이동 매핑
    @GetMapping("/login")
    public String login(){
        return "member/login";
    }

    //시큐리티 사용 전 매핑
//    @PostMapping("/login")
//    @ResponseBody
//    public boolean login(@RequestParam("mid") String mid, @RequestParam("mpassword") String mpassword){
//        System.out.println(mid+mpassword);
//        return memberService.login(mid, mpassword);
//    }

    //회원가입 페이지 이동 매칭
    @GetMapping("/signup")
    public String signup(){
        return "member/write";
    }
    @Autowired
    MemberService memberService; // 메모리할당

    //회원가입 처리 매핑
    @PostMapping("/signup")
    @ResponseBody // 객체를 반환하도록 해주는 어노테이션
    public boolean signup(Memberdto memberdto){
        //서비스 호출
        //자동 주입
        boolean result = memberService.signup(memberdto);
        return result;}

    //로그아웃 처리 매핑
    //시큐리티 내 로그아웃 기능 사용
//    @GetMapping("/logout")
//    public String logout(Model model){
//        memberService.logout();
//        //Dto 생성
//        Hellodto hellodto=Hellodto.builder().
//                name("엄").amount(1234).build();
//        //해당 템플릿에 데이터 보내기
//        model.addAttribute("data",hellodto);
//        //return "main"; // 타임리프 반환
//        return "redirect:/"; // url 이동(최상위 경로로)
//    }

    //회원 수정 페이지 매핑
    @GetMapping("/update")
    public String update(){return "member/update";}

    @PutMapping("/update")
    @ResponseBody
    public boolean update(@RequestParam("mname") String mname){
        return memberService.update(mname);}

    @GetMapping("/info")
    public String info(){
        return "member/info";
    }
    @GetMapping("/myroom")
    public String myroom(){
        return "member/myroom";
    }

    @GetMapping("/delete")
    public String delete(){return "member/delete";}

    @DeleteMapping("/delete")
    @ResponseBody
    public boolean delete(@RequestParam("mpassword") String pw){
        return memberService.delete(pw);
    }
}
