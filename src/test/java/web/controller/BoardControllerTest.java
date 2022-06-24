package web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc // mvc 테스트중 Controller, Service, Repository, Model 사용 가능
class BoardControllerTest {
    @Autowired
    MockMvc mvc;

    @Test
    public void list() throws Exception{
        mvc.perform(get("/board/list")).andExpect(status().isOk()).andDo(print());
    }

    @Test
    void view() throws Exception{
        mvc.perform(get("/board/view/1")).andExpect(status().isOk()).andDo(print());
    }

    @Test
    void getboard() throws Exception{
        MockHttpSession session=new MockHttpSession();
        session.setAttribute("bno",1);
        mvc.perform(get("/board/getboard").session(session)).andDo(print());
    }

    @Test
    void update() throws Exception{
        mvc.perform(get("/board/update")).andExpect(status().isOk()).andDo(print());
    }

    @Test
    void save() throws Exception{
        mvc.perform(get("/board/save"));
    }

    @Test
    void categorylist() throws Exception{
        mvc.perform(get("/board/getcategorylist")).andExpect(status().isOk()).andDo(print());
    }

    //변수 전달 : .param("필드명",데이터)
    //세션 전달 : MockHttpSession 클래스 변수 생성 후 .session(변수)
    @Test
    void testSave() throws Exception{
        MockHttpSession session=new MockHttpSession();
        session.setAttribute("login",true);
        mvc.perform(post("/board/save").param("btitle","테스트제목").param("bcontent","테스트내용")
                .param("category","자유").session(session)).andDo(print());
    }

    @Test
    void getBoardList() throws Exception{
        mvc.perform(get("/board/getboardlist").param("cno","1").param("key","btitle")
                .param("keyword","t").param("page","1")).andDo(print());
    }

    @Test
    void testUpdate() throws Exception{
        MockHttpSession session=new MockHttpSession();
        session.setAttribute("bno",1);
        mvc.perform(put("/board/update").session(session).param("btitle","수정된제목").param("bcontent","수정된제목")).andDo(print());
    }

    @Test
    void deletetest() throws Exception{
        mvc.perform(delete("/board/delete").param("bno","1")).andDo(print());
    }
}