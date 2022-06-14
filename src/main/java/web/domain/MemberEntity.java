package web.domain;

import lombok.Builder;

import javax.persistence.*;

@Table(name="member")
@Entity
@Builder
public class MemberEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int mno;
    private String mid;
    private String mpassword;
    private String mname;
}
