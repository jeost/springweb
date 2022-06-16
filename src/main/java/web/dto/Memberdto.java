package web.dto;

import lombok.*;
import web.domain.member.MemberEntity;
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
        return MemberEntity.builder().mid(this.mid)
                .mpassword(this.mpassword).mname(this.mname).roomEntityList(new ArrayList<RoomEntity>()).build();
    }
}
