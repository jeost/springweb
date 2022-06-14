package web.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.domain.RoomEntity;
import web.domain.RoomRepository;
import web.dto.RoomDto;

import java.util.List;

@Service
public class RoomService {
    @Autowired
    RoomRepository roomRepository;
            public boolean room_save(RoomDto roomDto){
                RoomEntity roomEntity= RoomEntity.builder().roomname(roomDto.getRoomname()).x(roomDto.getX()).y(roomDto.getY()).build();
                roomRepository.save(roomEntity);
                return true;
            }
    public JSONArray room_list(){
        JSONArray jsonArray=new JSONArray();
        //모든 엔티티 호출
        List<RoomEntity> rEList = roomRepository.findAll();
        //모든 엔티티->JSON 변환
        for(RoomEntity roomEntity:rEList){
            JSONObject object=new JSONObject();
            object.put("rname",roomEntity.getRoomname());
            object.put("lng",roomEntity.getX());
            object.put("lat",roomEntity.getY());
            jsonArray.put(object);
        }
        return jsonArray;
    }
}
