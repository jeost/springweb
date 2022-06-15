package web.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;
import web.domain.member.MemberEntity;

public interface MemberRepository extends JpaRepository<MemberEntity, Integer> {
}
