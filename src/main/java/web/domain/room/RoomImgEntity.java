package web.domain.room;

import lombok.*;
import web.domain.BaseTime;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@ToString( exclude="roomEntity" )
@NoArgsConstructor
@AllArgsConstructor
@Table(name="roomimg")
public class RoomImgEntity extends BaseTime {
    //pk번호
    @Id
    @GeneratedValue
    private int rimgno;
    //이미지 이름
    private String rimg;
    //방 번호(fk)
    @ManyToOne // 다수로 하나 연결
    @JoinColumn(name="rno")
    private RoomEntity roomEntity;
}
