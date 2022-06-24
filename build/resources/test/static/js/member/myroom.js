myroomlist();
function myroomlist(){
$.ajax({
    url:"/room/myroomlist",
    method:'get',
    success:function(re){
        let html="";
        for(let i=0; i<re.length; i++){
            html+=
            '<tr>' +
            '                <td>' +
            '                    <img src="/upload/'+re[i].rimg+'" width="100%">' +
            '                </td>' +
            '                <td>' +
            '                    <span>'+re[i].rname+'</span>' +
            '                </td>' +
            '                <td>' +
            '                    <span>'+re[i].rdate+'</span>' +
            '                </td>' +
            '                <td><button type="button" onclick="room_update('+re[i].rno+')">수정</button><button type="button" onclick="room_delete('+re[i].rno+')">삭제</button></td>' +
            '            </tr>'
        }
        $("#myroomtable").html(html);
    }
});
}
function room_delete(rno){
    $.ajax({
    url:"/room/delete",
    method:"delete",
    data:{"rno":rno},
    success:function(re){
        if(re==true){alert("삭제 성공"); myroomlist();
        }else{alert("삭제 실패");
        }
     }
    });
}
function room_update(rno){

}