package web.domain.member;

import lombok.*;
import web.domain.BaseTime;
import web.domain.board.BoardEntity;
import web.domain.room.RoomEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name="member")
@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberEntity extends BaseTime {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int mno;
    private String mid;
    private String mpassword;
    private String mname;
    @Enumerated(EnumType.STRING) // DB의 enum 타입을 String 으로 변경, 기본값은 인덱스 번호(ORDINAL)가 들어감
    private Role role;

    //권한(role) 중에서 키값만 반환(시큐리티에서 인증 허가된 리스트에 보관용)
    public String getRoleKey(){
        return role.getKey();
    }

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.ALL)
    private List<RoomEntity> roomEntityList;

    @Builder.Default
    @OneToMany(mappedBy = "memberEntity",cascade = CascadeType.ALL)
    private List<BoardEntity> boardEntityList=new ArrayList<>();
}
