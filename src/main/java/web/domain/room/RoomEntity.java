package web.domain.room;

import lombok.*;
import web.domain.BaseTime;
import web.domain.member.MemberEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="room")
@Builder
public class RoomEntity extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rno;
    private String rname;
    private String lat;
    private String lng;
    private String rdealsystem;
    private String rprice;
    private String rarea;
    private String rmanage;
    private String rstruc;
    private String rfinishdate;
    private boolean rparking;
    private boolean relevate;
    private String renterdate;
    private int rfloor;
    private int rfloorall;
    private String rbuilding;
    private String raddress;
    @Column(columnDefinition = "TEXT")
    private String rcontent;
    @ManyToOne
    @JoinColumn(name="mno")
    private MemberEntity memberEntity;
    @OneToMany(mappedBy = "roomEntity", cascade = CascadeType.ALL) // 하나로 다수 연결
    private List<RoomImgEntity> roomImgEntityList;
}