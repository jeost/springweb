package web.domain.member;

import lombok.*;
import web.domain.BaseTime;
import web.domain.room.RoomEntity;

import javax.persistence.*;
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

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.ALL)
    List<RoomEntity> roomEntityList;
}
