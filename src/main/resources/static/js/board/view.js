board_get();
function board_get(){
$.ajax({
url:"/board/getboard",
success:function(board){
console.log(board);
let html=
        '<div>게시물번호:'+board.bno+'</div>'+
        '<div>게시물제목:'+board.btitle+'</div>'+
        '<div>게시물내용:'+board.bcontent+'</div>'+
        '<div>게시물작성자:'+board.bwriter+'</div>'+
        '<div>게시물작성일:'+board.bindate+'</div>'+
        '<div>게시물최근수정일:'+board.bmodate+'</div>'+
        '<div>게시물조회수:'+board.bview+'</div>'+
        '<div>게시물추천수:'+board.blike+'</div>'+
        '<div>작성자:'+board.mid+'</div>'+
        '<button onclick="board_delete('+board.bno+')">삭제</button>';
$("#boarddiv").html(html);
}
});
}
//삭제 처리 메소드
function board_delete(bno){
    $.ajax({
    url:"/board/delete",
    data:{"bno":bno},
    method:"delete",
    success:function(re){
    alert(re);
    }
    });
}