/*bnoinput();
function bnoinput(){
let bno=sessionStorage.getItem("bno");
console.log(bno);
$("#bno").html(bno);
}*/

//수정 처리(U)
function board_update(){
    let form=$("#updateform")[0];
    let formdata=new FormData(form);
    $.ajax({
        url:"/board/update",
        data:formdata,
        method:"PUT",
        processData:false,
        contentType:false,
        success:function(re){
        }
    });
}