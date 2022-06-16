package web.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import web.domain.member.MemberEntity;
import web.domain.member.MemberRepository;
import web.domain.room.RoomEntity;
import web.domain.room.RoomImgEntity;
import web.domain.room.RoomImgRepository;
import web.domain.room.RoomRepository;
import web.dto.LoginDto;
import web.dto.RoomDto;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private RoomImgRepository roomImgRepository;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private MemberRepository memberRepository;

    @Transactional
            public boolean room_save(RoomDto roomDto){
        //현재 로그인 세션 호출
        LoginDto dto=(LoginDto)request.getSession().getAttribute("login");
        //현재 로그인 된 회원의 엔티티 찾기
        MemberEntity memberEntity=memberRepository.findById(dto.getMno()).get();
//                RoomEntity roomEntity= RoomEntity.builder().roomname(roomDto.getRname()).lat(roomDto.getX()).lng(roomDto.getY())
//                        .rdealsystem(roomDto.getRdealsystem()).rprice(roomDto.getRprice()).rarea(roomDto.getRarea())
//                        .rmanage(roomDto.getRmanage()).rstruc(roomDto.getRstruc()).rfinishdate(roomDto.getRfinishdate())
//                        .rparking(roomDto.getRparking()).relevate(roomDto.getRelevate())
//                        .rfloor(roomDto.getRfloor()).rfloorall(roomDto.getRfloorall())
//                        .rbuilding(roomDto.getRbuilding()).raddress(roomDto.getRaddress())
//                        .rcontent(roomDto.getRcontent()).build();
                RoomEntity roomEntity =roomDto.toEntity(); // DTO -> Entity 변환
                roomRepository.save(roomEntity); // 우선적으로 룸 DB에 저장

                    //현재 로그인 된 엔티티들을 엔티티에 저장
                    roomEntity.setMemberEntity(memberEntity);
                    //현재 로그인 된 회원 엔티티 내 룸 리스트에 룸 엔티티 추가
                    memberEntity.getRoomEntityList().add(roomEntity);
                String uuidfile=null; // 입력받은 첨부파일을 저장하는거 여기부터 start
                if(roomDto.getRimg().size()!=0){ // 첨부파일이 1개 이상이면
                    for(MultipartFile file:roomDto.getRimg()){
                        UUID uuid=UUID.randomUUID(); // UUID 난수 생성
                        //파일명에 언더바 존재시 문제 발생하니까 하이픈으로 변경
                        uuidfile=uuid.toString()+"_"+file.getOriginalFilename().replaceAll("_","-"); // 난수와 파일명 구분 언더바
                        String dir="C:\\Users\\504\\IdeaProjects\\springweb\\src\\main\\resources\\static\\upload\\";
                        String filepath=dir+uuidfile; // 파일명 같으면 식별 불가해서 난수값추가
                        try {
                            //파일 경로 이동(업로드 처리)
                            file.transferTo(new File(filepath));
                            //엔티티에 파일명 저장
//                            roomEntity.setRimg(uuidfile);
                            //이미지 엔티티 객체 생성
                            RoomImgEntity roomImgEntity= RoomImgEntity.builder()
                                    .rimg(uuidfile).roomEntity(roomEntity).build();
                            //엔티티 세이브
                            roomImgRepository.save(roomImgEntity);
                            //위에서 생성된 이미지 엔티티를 룸 엔티티에 추가
                            roomEntity.getRoomImgEntityList().add(roomImgEntity);
                        } catch (IOException e) {
                           e.printStackTrace();
                        }
                    }
                }

                return true;
            }

            @Transactional
    public Map<String, List<Map<String, String>>> room_list(Map<String, String> location){
        /*JSONArray jsonArray=new JSONArray();
        //모든 엔티티 호출
        List<RoomEntity> rEList = roomRepository.findAll();
        //모든 엔티티->JSON 변환
        for(RoomEntity roomEntity:rEList){
            JSONObject object=new JSONObject();
            object.put("rname",roomEntity.getRoomname());
            object.put("lng",roomEntity.getX());
            object.put("lat",roomEntity.getY());
            jsonArray.put(object);
        }*/
        List<Map<String, String>> mapList=new ArrayList<>();
        List<RoomEntity> roomEntityList=roomRepository.findAll();
        double qa=Double.parseDouble(location.get("qa"));
        double ha=Double.parseDouble(location.get("ha"));
        double oa=Double.parseDouble(location.get("oa"));
        double pa=Double.parseDouble(location.get("pa"));
        for(RoomEntity entity : roomEntityList){
            //location 범위 내 좌표만 저장하기
            if(Double.parseDouble(entity.getLat())>qa && Double.parseDouble(entity.getLat())<pa
            && Double.parseDouble(entity.getLng())>ha && Double.parseDouble(entity.getLng())<oa){
                //맵에 데이터를 담고나서 리스트에 담기
                Map<String, String> map=new HashMap<String, String>();
                map.put("rname",entity.getRname());
                map.put("lng",entity.getLng());
                map.put("lat",entity.getLat());
                map.put("rno",entity.getRno()+"");
                map.put("rdealsystem",entity.getRdealsystem());
                map.put("rprice",entity.getRprice());
                map.put("rarea",entity.getRarea());
                map.put("rmanage",entity.getRmanage());
                map.put("rstruc",entity.getRstruc());
                map.put("rfinishdate",entity.getRfinishdate());
                map.put("rparking",entity.isRparking()+"");
                map.put("relevate",entity.isRelevate()+"");
                map.put("renterdate",entity.getRenterdate());
                map.put("rfloor",entity.getRfloor()+"");
                map.put("rfloorall",entity.getRfloorall()+"");
                map.put("rbuilding",entity.getRbuilding());
                map.put("raddress",entity.getRaddress());
                map.put("rcontent",entity.getRcontent());
                map.put("rimg",entity.getRoomImgEntityList().get(0).getRimg()); // 대표이미지(첫번째 이미지) 빼오기
                mapList.add(map);
            }
        }
        //담은 리스트를 맵으로
        Map<String, List<Map<String, String>>> object=new HashMap<>();
        object.put("positions",mapList);
        return object;
    }

    @Transactional
    public JSONObject getroom(int rno){
        Optional<RoomEntity> entity=roomRepository.findById(rno);
        RoomEntity roomEntity=entity.get();
        JSONObject object=new JSONObject();
        //json에 엔티티 필드 값 넣기
        object.put("rname",roomEntity.getRname());
        JSONArray jsonArray=new JSONArray();
        //img를 json으로 변환시키며 하나씩 담기
        for(RoomImgEntity e: roomEntity.getRoomImgEntityList()){
            jsonArray.put(e.getRimg());
        }
        //json 오브젝트 안에 array 형태의 json 값을 넣기
        object.put("rimglist",jsonArray);
        //반환
        return object;
    }

    //현재 로그인된 회원이 등록한 방 목록 호출
    @Transactional
    public JSONArray myroomlist(){
        JSONArray jsonArray=new JSONArray();
        LoginDto logindto = (LoginDto) request.getSession().getAttribute("login");
        MemberEntity memberEntity=memberRepository.findById(logindto.getMno()).get();
        //찾은 회원 엔티티의 방 목록을 JSON 형태로 변환하기
        for(RoomEntity e:memberEntity.getRoomEntityList()){
            JSONObject object=new JSONObject();
            object.put("rname",e.getRname());
            object.put("rimg",e.getRoomImgEntityList().get(0).getRimg());
            object.put("rdate",e.getModifiedate());
            object.put("rno",e.getRno());
            jsonArray.put(object);
        }
        return jsonArray;
    }

    @Transactional
    public boolean delete(int rno){
        RoomEntity entity=roomRepository.findById(rno).get();
        if(entity!=null){
            roomRepository.delete(entity);
            return true;
        }else{
            return false;
        }
    }
}
