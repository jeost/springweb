package web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import web.domain.member.MemberEntity;
import web.domain.member.Role;
import web.dto.LoginDto;
import web.dto.Memberdto;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {
    @Autowired
    MockMvc mvc;

    @Test
    void login() throws Exception{
        mvc.perform(get("/member/login")).andExpect(status().isOk()).andDo(print());
    }

    @Test
    void testLogin() throws Exception{
        mvc.perform(post("/member/login").param("mid","admin").param("mpassword","admin")).andExpect(status().isOk()).andDo(print());
    }

    @Test
    void signup() throws Exception{
        mvc.perform(get("/member/signup")).andExpect(status().isOk()).andDo(print());
    }

    @Test
    void testSignup() throws Exception{
        mvc.perform(post("/member/signup").param("mid","testid").param("mpassword","testpw").param("mname","testname")).andExpect(status().isOk()).andDo(print());
    }

    @Test
    void logout() throws Exception{
        mvc.perform(get("/member/logout")).andExpect(status().isOk()).andDo(print());
    }

    @Test
    void update() throws Exception{
        mvc.perform(get("/member/update")).andExpect(status().isOk()).andDo(print());
    }

    @Test
    void testUpdate() throws Exception{
        MockHttpSession session=new MockHttpSession();
        LoginDto dto=new LoginDto(MemberEntity.builder().mid("testid").mpassword("testpw").roomEntityList(new ArrayList<>()).boardEntityList(new ArrayList<>()).role(Role.USER).mname("testname").build(), );
        session.setAttribute("login",dto);
        mvc.perform(put("/member/update").param("mname","changedname").session(session)).andExpect(status().isOk()).andDo(print());
    }

    @Test
    void info() throws Exception{
        mvc.perform(get("/member/info")).andDo(print());
    }

    @Test
    void myroom() throws Exception{
        mvc.perform(get("/member/myroom")).andDo(print());
    }

    @Test
    void deletetest() throws Exception{
        mvc.perform(get("/member/delete")).andDo(print());
    }

    @Test
    void testDelete() throws Exception{
        MockHttpSession session=new MockHttpSession();
        LoginDto dto= LoginDto.builder().mname("testname").mid("testid").mno(2).build();
        session.setAttribute("login",dto);
        mvc.perform(delete("/member/delete").session(session).param("mpassword","testpw")).andDo(print());
    }
}