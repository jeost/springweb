package web.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginDto { // 로그인 세션에 넣을 DTO
    private int mno;
    private String mid;
    private String mname;
}
