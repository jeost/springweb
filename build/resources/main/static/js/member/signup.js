function signup(){
 let form=$("#signupform")[0];
 let formdata=new FormData(form);
    $.ajax({
         url:"/member/signup",
         method:"POST",
         data:formdata,
         contentType:false,// 첨부파일 전송시 사용되는 속성
         processData:false,// 첨부파일 전송시 사용되는 속성
         success:function(re){
             if(re==true){alert("회원가입 성공");
             location.href="/member/login";
             }else{
             alert("회원가입 실패")
             }

         }
     });
}