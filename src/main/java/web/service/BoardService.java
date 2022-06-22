package web.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import web.domain.board.BoardEntity;
import web.domain.board.BoardRepository;
import web.domain.board.CategoryEntity;
import web.domain.board.CategoryRepository;
import web.domain.member.MemberEntity;
import web.domain.member.MemberRepository;
import web.dto.BoardDto;
import web.dto.LoginDto;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class BoardService {
    @Autowired // 자동 빈 생성(new 대체한 메모리할당)
    private BoardRepository boardRepository;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    //C
    @Transactional
    public boolean save(BoardDto boardDto){

        LoginDto l=(LoginDto) request.getSession().getAttribute("login");

        if(l!=null){
            Optional<MemberEntity> o=memberRepository.findById(l.getMno());
            if(o.isPresent()){ // null이 아니면
                //만약 기존에 있는 카테고리라면
                List<CategoryEntity> c=categoryRepository.findAll();
                boolean sw=false;
                int cno=0;
                for(CategoryEntity e:c){
                    if(e.getCname().equals(boardDto.getCategory())){
                        sw=true;
                        cno=e.getCno();
                    }
                }
                CategoryEntity category=null;
                if(!sw) {
                    //카테고리 생성
                    category = CategoryEntity.builder().cname(boardDto.getCategory()).build();
                    categoryRepository.save(category);
                }else{
                    category=categoryRepository.findById(cno).get();
                }
                BoardEntity boardEntity =boardRepository.save(boardDto.toEntity());

                boardEntity.setMemberEntity(o.get()); // 작성자 추가
                boardEntity.setCategoryEntity(category);

                //카테고리 엔티티에 게시물 연결
                category.getBoardEntityList().add(boardEntity);
                //회원 엔티티에 게시물 연결
                o.get().getBoardEntityList().add(boardEntity);
                return true;
            }
        }return false;
    }
    //R(전체 조회)
    public JSONObject getBoardList(int cno,String key, String keyword,int page){
        JSONObject object=new JSONObject();
        JSONArray jsonArray=new JSONArray();
        Page<BoardEntity> boardEntities=null; // 일단 선언만하고 나중에 넣기

        Pageable pageable= PageRequest.of(page,5, Sort.by(Sort.Direction.DESC, "bno")); // 페이지 처리 관련 인터페이스 제공. 0부터 시작하는 페이지에 몇개 보여줄지 사이즈 설정
        if(key.equals("btitle")){ // 제목 검색                              //Sort로 내림차순으로 설정. 만약 필드명에 언더바 들어가면 오류생기니 orderBy 사용
            boardEntities=boardRepository.findAllByBtitle(cno,keyword,pageable);
        }else if(key.equals("bcontent")){ // 내용 검색
            boardEntities=boardRepository.findAllByBcontent(cno,keyword,pageable);
        }else if(key.equals("mid")){ // 작성자 검색
            //입력받은 mid -> mno 엔티티 변환
            Optional<MemberEntity> o=memberRepository.findByMid(keyword);
            MemberEntity memberEntity=null;
            if(o.isPresent()){ //존재하는 아이디를 검색했다면
                memberEntity=o.get();
                boardEntities=boardRepository.findAllByMemberEntity(cno,memberEntity,pageable);
            }else{ // 없는 아이디를 검색했다면
                return object;
            }
        }else{
            boardEntities=boardRepository.findAllByBtitle(cno, keyword, pageable);
        }
        //페이지에 표시할 총 페이징 버튼 개수
        int btncount=5;
        //시작하는 번호
        int startbtn=(page/btncount)*btncount+1;
        //끝나는 번호(시작버튼+표시할버튼수-1)
        int endbtn=startbtn+btncount-1;
        //만약 끝번호가 마지막 페이지보다 크면, 마지막 페이지값을 끝번호에 대입
        if(endbtn>boardEntities.getTotalPages()){endbtn=boardEntities.getTotalPages();}
//        System.out.println("검색된 총 게시물 수 : "+boardEntities.getTotalElements());
        System.out.println("검색된 총 페이지 수 : "+boardEntities.getTotalPages());
//        System.out.println("검색된 게시물 정보 : "+boardEntities.getContent());
//        System.out.println("현재 페이지수 : "+boardEntities.getNumberOfElements());
//        System.out.println("현재 페이지의 게시물 수 : "+boardEntities.getPageable());

        //모든 엔티티 -> JSON변환
        for(BoardEntity e:boardEntities){
            if(e.getCategoryEntity().getCno()==cno){
                JSONObject o=new JSONObject();
                o.put("bno",e.getBno());
                o.put("btitle",e.getBtitle());
                o.put("bdate",e.getCreatedate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                o.put("bview",e.getBview());
                o.put("blike",e.getBlike());
                o.put("mid",e.getMemberEntity().getMid());
                o.put("category",e.getCategoryEntity().getCname());
                jsonArray.put(o);
            }
        }
        object.put("startbtn",startbtn);
        object.put("endbtn",endbtn);
        object.put("totalpages",boardEntities.getTotalPages());
        object.put("data",jsonArray);
        return object;
    }
    //게시물 개별 조회
    @Transactional
    public JSONObject getBoard(int bno){
        Optional<BoardEntity> o=boardRepository.findById(bno);
        BoardEntity e=o.get();
        //조회수 증가 처리(ip 기준 24시간)
        String ip=request.getRemoteAddr(); // 현재 사용자의 ip 가져오기
        Object session=request.getSession().getAttribute(ip+bno);
        if(session!=null){

        }else{
            request.getSession().setAttribute(ip+bno,true);//세션 부여하기
            request.getSession().setMaxInactiveInterval(24*60*60);
            e.setBview(e.getBview()+1);
        }
        JSONObject object=new JSONObject();
        object.put("bno",e.getBno());
        object.put("btitle",e.getBtitle());
        object.put("bcontent",e.getBcontent());
        object.put("bview",e.getBview());
        object.put("blike",e.getBlike());
        object.put("bindate",e.getCreatedate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        object.put("bmodate",e.getModifiedate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        object.put("mid",e.getMemberEntity().getMid());
        return object;
    }
    //U
    @Transactional
    public boolean boardUpdate(BoardDto boardDto){
        Optional<BoardEntity> dto=boardRepository.findById(boardDto.getBno());
        BoardEntity entity=dto.get();
        entity.setBtitle(boardDto.getBtitle());
        entity.setBcontent(boardDto.getBcontent());
        return true;
    }
    //D
    @Transactional
    public boolean delete(int bno){
        BoardEntity entity=boardRepository.findById(bno).get();
        boardRepository.delete(entity);
        return true;
    }
    public JSONArray getcategorylist(){
        List<CategoryEntity> categoryEntityList = categoryRepository.findAll();
        JSONArray array=new JSONArray();
        for(CategoryEntity e:categoryEntityList){
            JSONObject object=new JSONObject();
            object.put("cno",e.getCno());
            object.put("cname",e.getCname());
            array.put(object);
        }
        return array;
    }
}