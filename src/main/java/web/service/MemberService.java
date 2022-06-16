package web.service;

import org.springframework.web.bind.annotation.GetMapping;
import web.domain.member.MemberEntity;
import web.domain.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.dto.LoginDto;
import web.dto.Memberdto;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class MemberService {

    @Autowired // 자동 Bean(메모리) 생성(new 비슷한거)
    private MemberRepository memberRepository; // 메모리할당

    //로직/트랜잭션
    //로그인처리 메소드
    public boolean login(String mid, String mpassword) {
        List<MemberEntity> list=memberRepository.findAll();
        for(MemberEntity e:list){ // 모든 엔티티 리스트에서 입력받은 데이터와 비교
            if(e.getMid().equals(mid)&&e.getMpassword().equals(mpassword)){
                //로그인에 성공하면, 로그인 세션에 사용될 DTO 생성
                LoginDto loginDto= LoginDto.builder().mno(e.getMno()).mid(e.getMid()).mname(e.getMname()).build();
                request.getSession().setAttribute("login",loginDto);
                return true; // 아이디, 비번이 동일하면 로그인 성공
            }
        }
        return false;
    }
    @Autowired
    HttpServletRequest request;
    //회원가입처리 메소드
    public boolean signup(Memberdto memberdto){
        try {
            //dto -> entity
            MemberEntity memberEntity=memberdto.toEntity();
            //저장하고
            memberRepository.save(memberEntity).getMno();
            //저장여부 판단
            if(memberEntity.getMno()<1){
                return false;
            }else{return true;}
        }catch(Exception e){e.printStackTrace();}
        return false;
    }

    //로그아웃처리 메소드
    public void logout(){
        request.getSession().setAttribute("login",null);
    }

    //회원정보 수정 메소드
    @Transactional
    public boolean update(String mname){
        if(request.getSession().getAttribute("login")==null) {
            return false;
        }else{
            //세션 내 DTO 호출
            LoginDto dto=(LoginDto)request.getSession().getAttribute("login");
            MemberEntity entity=memberRepository.findById(dto.getMno()).get();
            entity.setMname(mname);
            return true;
        }
    }

    @Transactional
    public boolean delete(String pw){
        if(request.getSession().getAttribute("login")==null) {
            return false;
        }else{
            LoginDto loginDto=(LoginDto) request.getSession().getAttribute("login");
            MemberEntity memberEntity=memberRepository.findById(loginDto.getMno()).get();
            if(memberEntity.getMpassword().equals(pw)){
                memberRepository.delete(memberEntity);
                return true;
            }
            return false;
        }
    }
}
