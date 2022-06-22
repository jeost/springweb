category_list();
board_list(1,0,"",""); // cno, page, key, keyword
let current_cno=2; // 기본 카테고리값
let current_key=""; // 현재 검색된 키 [ 없을경우 공백 ]
let current_keyword=""; // 현재 검색된 키워드 [ 없을경우 공백 ]
let current_page=0;
function board_list(cno, page, key, keyword){
this.current_cno=cno;
this.current_page=page;
if(key!=undefined){this.current_key=key}
if(keyword!=undefined){this.current_keyword=keyword}

    //this.전역변수
        $.ajax({
            url:"/board/getboardlist",
            method:"GET",
            data:{"cno":this.current_cno, "key":this.current_key, "keyword":this.current_keyword,"page":this.current_page},
            success:function(re){
            console.log(re);
            let html='<tr><th>번호</th><th>제목</th><th>작성일</th><th>조회수</th><th>추천수</th><th>작성자</th></tr>';
            if(re.data.length==0){
            html+='<tr><td colspan=6>검색 결과가 존재하지 않습니다</td></tr>';
            }
            for(let i=0; i<re.data.length; i++){
            html+=
            '<tr>'+
                '<td>'+re.data[i].bno+'</td><td><a href="/board/view/'+re.data[i].bno+'">'+re.data[i].btitle+'<a></td><td>'+re.data[i].bdate+'</td><td>'+re.data[i].bview+'</td><td>'+re.data[i].blike+'</td><td>'+re.data[i].mid+'</td>'+
            '</tr>';
             }
             let html2="";
             if(page==0){
             }else{
             html2+='<li class="page-item"><button class="page-link" onclick="board_list('+cno+','+(page-1)+')"> 이전 </button></li>';
             }
             for(let i=re.startbtn-1; i<re.endbtn; i++){
             html2+=
             '<li class="page-item"><button class="page-link" onclick="board_list('+cno+','+i+')"> '+(i+1)+' </button></li>';
             }
             if(page==re.totalpages-1){ // 현재 페이지가 마지막 페이지면
              //다음버튼 표시 x
             }else{
             html2+='<li class="page-item"><button class="page-link" onclick="board_list('+cno+','+(page+1)+')"> 다음 </button></li>';
             }
             $("#boardTable").html(html);
             $("#pagebtnbox").html(html2);
            }
        });
}
function category_list(){
    $.ajax({
    url:"/board/getcategorylist",
    success:function(re){
        let html="";
        for(let i=0; i<re.length; i++){
        html+=
        '<button onclick="categorybtn('+re[i].cno+')">'+re[i].cname+'</button>'
        }
        $("#categorybox").html(html);
    }
    });
}
//카테고리 버튼 눌렀을때
function categorybtn(cno){
this.current_cno=cno;
    board_list(  cno , 0 ,  "" , "" ); // 검색 없을경우 공백 전달(안 넣으면 undefined)
}
//검색 버튼 눌렀을때
function search(){
let key=$("#key").val();
let keyword=$("#keyword").val();
 board_list(  this.current_cno , 0 ,  key , keyword );
}