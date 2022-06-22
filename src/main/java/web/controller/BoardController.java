package web.controller;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.dto.BoardDto;
import web.service.BoardService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetAddress;

@Controller
@RequestMapping("/board")
public class BoardController {
    @Autowired
    private HttpServletRequest request;
@Autowired
    private BoardService boardService;
////////////view 열기 매핑
    //게시판 페이지 열기
    @GetMapping("/list")
    public String list(HttpServletRequest request) throws Exception{
        String ipAddress = request.getRemoteAddr();
        if (ipAddress.equalsIgnoreCase("0:0:0:0:0:0:0:1")) {
            InetAddress inetAddress = InetAddress.getLocalHost();
            ipAddress = inetAddress.getHostAddress();
        }
        System.out.println("client ip : " + ipAddress);
        return "board/list";}
    //게시물 개별 조회
    @GetMapping("/view/{bno}")
    public String view( @PathVariable("bno") int bno ) {        // { } 안에서 선언된 변수는 밖에 사용불가
        // 1. 내가 보고 있는 게시물의 번호를 세션 저장
        request.getSession().setAttribute("bno", bno);
        return "board/view";
    }
    @GetMapping("/getboard")
    public void getboard( HttpServletResponse response){
        int bno =  (Integer) request.getSession().getAttribute("bno");
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().print(boardService.getBoard(  bno   ));
        }catch( Exception e ){
            System.out.println( e );
        }
    }
    //게시물 수정
    @GetMapping("/update")
    public String update(){
        return "board/update";}
    //게시물 쓰기
    @GetMapping("/save")
    public String save(){return "board/save";}

    //카테고리 출력
    @GetMapping("/getcategorylist")
    public void categorylist(HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        try {
            response.getWriter().print(boardService.getcategorylist());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
/////////////////////service 처리 매핑
    //C
    @PostMapping("/save")
    @ResponseBody
    public boolean save(BoardDto boardDto) {
        return boardService.save(boardDto);
    }
    //R
    @GetMapping("/getboardlist")
    public void getBoardList(HttpServletResponse response, @RequestParam("cno") int cno,
                             @RequestParam("key") String key, @RequestParam("keyword") String keyword,
                             @RequestParam("page") int page){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        try {
            response.getWriter().print(boardService.getBoardList(cno,key,keyword,page));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //U
    @PutMapping("/update")
    @ResponseBody
    public boolean update( BoardDto boardDto ){
        int bno =  (Integer) request.getSession().getAttribute("bno");
        System.out.println(bno);
        boardDto.setBno( bno );
        return boardService.boardUpdate(boardDto);
    }
    //D
    @DeleteMapping("/delete")
    @ResponseBody
    public boolean delete(@RequestParam("bno") int bno){
        return boardService.delete(bno);
    }

}
