package web.dto;

import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import web.domain.member.MemberEntity;
import web.domain.member.Role;
import web.domain.room.RoomEntity;

import java.util.ArrayList;

@Getter@Setter@ToString
@NoArgsConstructor@AllArgsConstructor
@Builder
public class Memberdto {
    private int mno;
    private String mid;
    private String mpassword;
    private String mname;

    //DTO -> ENTITY
    public MemberEntity toEntity(){

        //패스워드 암호화
            //비크립트
        BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

        return MemberEntity.builder().mid(this.mid)
                .mpassword(passwordEncoder.encode(this.mpassword)).mname(this.mname).role(Role.USER).roomEntityList(new ArrayList<RoomEntity>()).build();
    }
}
