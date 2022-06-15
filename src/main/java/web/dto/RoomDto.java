package web.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import web.domain.room.RoomEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomDto {
    private int rno;
    private String rname;
    private String lat;
    private String lng;
    private List<MultipartFile> rimg;
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
    private String rcontent;

    //DTO -> Entity 메소드
    public RoomEntity toEntity(){
        return RoomEntity.builder().rno(this.rno).rname(this.rname).lat(this.lat).lng(this.lng).rdealsystem(this.rdealsystem)
                .rprice(this.rprice).rarea(this.rarea).rmanage(this.rmanage).rstruc(this.rstruc).rfinishdate(this.rfinishdate)
                .rparking(this.rparking).relevate(this.relevate).renterdate(this.renterdate).rfloor(this.rfloor).rfloorall(this.rfloorall)
                .rbuilding(this.rbuilding).raddress(this.raddress).rcontent(this.rcontent).roomImgEntityList(new ArrayList<>()).build();
    }
}
