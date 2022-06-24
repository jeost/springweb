package web.domain.board;

import lombok.*;
import web.domain.BaseTime;
import web.domain.member.MemberEntity;
import web.domain.member.Role;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@ToString(exclude = {"memberEntity","categoryEntity"})
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Board")
public class BoardEntity extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bno;
    private String btitle;
    private String bcontent;
    private int bview;
    private int blike;

    //작성자 : 연관관계
    @ManyToOne
    @JoinColumn(name="mno")
    private MemberEntity memberEntity;
    //첨부파일 : 연관관계
    //카테고리 : 연관관계
    @ManyToOne
    @JoinColumn( name = "cno")
    private CategoryEntity categoryEntity;
    //댓글 : 연관관계
}
