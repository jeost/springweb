package web.dto;

import lombok.*;
import web.domain.board.BoardEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class BoardDto {
    private int bno;
    private String btitle;
    private String bcontent;
    private int bview;
    private int blike;

    private String category;
    //DTO -> Entity
    public BoardEntity toEntity(){
        return BoardEntity.builder().bno(this.bno).btitle(this.btitle).bcontent(this.bcontent).bview(this.bview).blike(this.blike).build();
    }
}
