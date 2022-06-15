package web.domain.room;

import org.springframework.data.jpa.repository.JpaRepository;
import web.domain.room.RoomEntity;

public interface RoomRepository extends JpaRepository<RoomEntity, Integer> {
}
