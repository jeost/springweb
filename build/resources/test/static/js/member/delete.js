function member_delete(){
    $.ajax({
    url:"",
    method:"delete",
    data:{"mpassword" : $("#mpw").val()},
    success:function(re){
    if(re==true){
        alert("회원탈퇴 성공");
        location.href="/member/logout";
        }else{
        alert("회원탈퇴 실패");
        }
    }
    });
}