async function getIpClient() {
  try {
    const response = await axios.get('https://api.ipify.org?format=json');
    console.log(response);
  } catch (error) {
    console.error(error);
  }
}

getIpClient();


// 출력 처리(R)
function board_view(){
}

//삭제 처리(D)
function board_delete(bno){
    $.ajax({
    url:"/board/delete",
    data:{"bno":bno},
    success:function(re){
    }
    });
}
