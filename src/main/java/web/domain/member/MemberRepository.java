package web.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;
import web.domain.member.MemberEntity;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Integer> {

    //아이디로 엔티티 검색
    Optional<MemberEntity> findByMid(String mid);

}
