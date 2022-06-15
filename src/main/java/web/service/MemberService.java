package web.service;

import web.domain.member.MemberEntity;
import web.domain.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.dto.Memberdto;

@Service
public class MemberService {
    //로직/트랜잭션
    //로그인처리 메소드
    public boolean login(){
        return false;
    }
    @Autowired // 자동 Bean(메모리) 생성(new 비슷한거)
    private MemberRepository memberRepository; // 메모리할당

    //회원가입처리 메소드
    public boolean signup(Memberdto memberdto){
        try {
            //dto -> entity
            MemberEntity memberEntity = MemberEntity.builder().mid(memberdto.getMid()).mname(memberdto.getMname())
                    .mpassword(memberdto.getMpassword()).build();
            memberRepository.save(memberEntity);
            return true;
        }catch(Exception e){e.printStackTrace();}
        return false;
    }
}
