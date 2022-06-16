myinfo();
function myinfo(){
    $.ajax({
        url:"/member/myinfo",
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
            $("#myinfotable").html(html);
        }
    });
}