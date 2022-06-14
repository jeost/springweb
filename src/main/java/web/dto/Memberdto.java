package web.dto;

import lombok.*;

@Getter@Setter@ToString
@NoArgsConstructor@AllArgsConstructor
@Builder
public class Memberdto {
    private int mno;
    private String mid;
    private String mpassword;
    private String mname;
}
