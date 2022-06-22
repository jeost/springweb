package web.domain.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import web.domain.member.MemberEntity;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {

    //직접 검색 메소드 선언
    @Query( value = "select * from board where cno = :cno and btitle like %:keyword%" , nativeQuery = true ) // nativeQuery를 써야 익숙한 문법 적용 가능
    Page<BoardEntity> findAllByBtitle(@Param("cno") int cno, @Param("keyword") String keyword, Pageable page); // @Param 으로 선언한 인수값을 :keyword에 전달(이름이 같아야함)
                                                                        // @Param 없이 ?1 하면 인수 1번째값 가지고옴(필드명이 인수와 같아야함)

    @Query( value = "select * from board where cno = :cno and bcontent like %:keyword%" , nativeQuery = true )// nativeQuery를 써야 익숙한 문법 적용 가능
    Page<BoardEntity> findAllByBcontent(@Param("cno") int cno,@Param("keyword") String keyword, Pageable page);

    @Query( value = "select * from board where cno = :cno and mno = :#{#memberEntity.mno}", nativeQuery = true  )
    Page<BoardEntity> findAllByMemberEntity(@Param("cno") int cno,@Param("memberEntity") MemberEntity memberEntity, Pageable page);
}
