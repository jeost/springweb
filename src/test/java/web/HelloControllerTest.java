package web;


import org.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import web.controller.BoardController;
import web.controller.HelloController;
import web.dto.BoardDto;

@WebMvcTest(controllers = HelloController.class)
public class HelloControllerTest{
@Autowired
private BoardController boardController;

@Autowired
private MockMvc mvc; //get, post 매핑 API 테스트 사용

@Test
public void helloreturn() throws Exception{
    String hello="hello";
    mvc.perform(MockMvcRequestBuilders.get("hello")).andExpect(MockMvcResultMatchers.status.isOk()).andExpect(content().string(hello));
}

@Test
public void boardsavetest(){
        BoardDto boardDto= BoardDto.builder().btitle("제목").bcontent("내용").build();
        boolean result=boardController.save(boardDto);
        System.out.println("저장 여부 확인 : "+result);
}

@Test
public void boardlistTest(){
    //JSONArray jsonArray=boardController.getBoardList();
    //System.out.println("호출 여부 확인 : "+jsonArray);
 }
public void boardUpdate(){
    BoardDto boardDto= BoardDto.builder().bno(1).btitle("수정제목").bcontent("수정내용").build();
    boolean result= boardController.update(boardDto);
    System.out.println("수정 여부 확인 : "+result);
}
 public void boardDelete(){
    int bno=1;
    boolean result= boardController.delete(bno);
    System.out.println("삭제 여부 확인 : "+result);
 }
}