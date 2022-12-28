package com.spring.view.board;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.spring.biz.board.BoardService;
import com.spring.biz.board.BoardVO;
import com.spring.biz.board.impl.BoardDAO;
//Controller annotation의 사용 효과
//1. @Controller로 선언된 class의 내부 method마다 URL을 지정 가능
// - method위에 @RequestMapping을 선언하고, parameter로 URL을 지정하면 
//   해당 url을 client가 요청하면, 해당 method 내용이 실행됨
//   .예를 들면, @RequestMapping(value="insertBoard.do")를 선언하면, 
//    /insertBoard.do URL이 요청오면 insertBoard() method가 실행됨

//2. @RequestMapping이 달려있는 method에 대한 여러가지 편리한 기능
// - method 이름은 자유롭게 생성가능
//	  - method의 parameter로 넘긴 argument에 대해선 spring이 자동으로 인스턴스를 생성해줌 (parameter의 갯수, parameter의 class)
//		.예를 들면, insertBoard(Board vo, BoardDAO boardDAO)선언하면, vo 인슽너스와 baordDAO 인스터스를 spring이 자동으로 만들어 줌 
//		 => 개발자들이 BoardVO vo = new BoardVO(); 명령어 사용할 필요 없음
// - method의 return type이 String 인 경우, return값은 원칙적으로 jsp나, do 등 path를 의미함 => spring이 자동으로 jsp 파일이나, .do path

@Controller
//@SessionAttributes("board") : board를 session 객체에 등록하여 사용하라는 의미
@SessionAttributes("board")
public class BoardController {
	@Autowired
	private BoardService boardService;

	@RequestMapping(value="/dataTransform.do")
	@ResponseBody
	public List<BoardVO> dataTransform(BoardVO vo) {
		
		vo.setSearchCondition("TITLE");
		vo.setSearchKeyword("");
		List<BoardVO> boardList = boardService.getBoardList(vo);
		
//		BoardListVO boardListVO = new BoardListVO();
//		boardListVO.setBoardList(boardList);
		
		return boardList;
	}
	
	// 글 등록
	// parameter BoardVO vo에서, vo에 client에서 parameter로 넘긴 data들을 자동으로 넎어줌
		// Spring이 자동으로 하는 일
		// 1. BoardVO vo = new BoardVO();
		// 2. String t = request.getParameter("title");
		// 3. vo.setTitle(t);
	@RequestMapping(value="insertBoard.do")
	public String insertBoard(BoardVO vo, BoardDAO boardDAO) throws IOException {

		System.out.println("글 등록 처리");

		// 파일 업로드 처리
		MultipartFile uploadFile = vo.getUploadFile();
		if (!uploadFile.isEmpty()) {
			String filename = uploadFile.getOriginalFilename();
			uploadFile.transferTo(new File("C:/" + filename));
		}
		
		boardService.insertBoard(vo);
		
		return "getBoardList.do";
	}

	// 글 수정
	@RequestMapping(value="updateBoard.do")
	public String updateBoard(@ModelAttribute("board") BoardVO vo, BoardDAO boardDAO) {

		System.out.println("글 수정 처리");

		System.out.println("번호 : " + vo.getSeq());
		System.out.println("제목 : " + vo.getTitle());
		System.out.println("작성자 : " + vo.getWriter());
		System.out.println("내용 : " + vo.getContent());
		System.out.println("등록일 : " + vo.getRegDate());
		System.out.println("조회수 : " + vo.getCnt());
		boardService.updateBoard(vo);
		
		return "getBoardList.do";
	}

	// 글 삭제
	@RequestMapping(value="deleteBoard.do")
	public String deleteBoard(BoardVO vo, BoardDAO boardDAO) {

		System.out.println("글 삭제 처리");
		boardService.deleteBoard(vo);
		
		return "getBoardList.do";
	}
	
	// 검색 조건 목록 설정
	@ModelAttribute("conditionMap")
	public Map<String, String> searchConditionMap() {
		Map<String, String> conditionMap = new HashMap<String, String>();
		conditionMap.put("제목", "TITLE");
		conditionMap.put("내용", "CONTENT");
		return conditionMap;
	}

	// 글 상세 조회
	@RequestMapping(value="/getBoard.do")
	public String getBoard(BoardVO vo, BoardDAO boardDAO, Model model) {

		System.out.println("글 상세 조회 처리");
	
		// 검색 결과를 세션에 저장하고 목록 화면으로 이동한다.
		model.addAttribute("board", boardService.getBoard(vo));
		
		return "getBoard.jsp";
	}

	// 글 목록 검색
	@RequestMapping(value="getBoardList.do")
//	public String getBoardList(@RequestParam(value="searchCondition", 
//			defaultValue="TITLE", required=false) String condition,
//			@RequestParam(value="searchKeyword", defaultValue="", required=false) String keyword,
//			BoardVO vo, BoardDAO boardDAO, Model model) {
	public String getBoardList(BoardVO vo, BoardDAO boardDAO, Model model) {
		
//		System.out.println("검색 조건 : " + condition);
		System.out.println("검색 단어 : " + vo.getSearchKeyword());
		
		// NULL Check
		if (vo.getSearchCondition() == null) vo.setSearchCondition("TITLE");
		if (vo.getSearchKeyword() == null) vo.setSearchKeyword("");
		
		// Model 정보 저장
		model.addAttribute("boardList", boardService.getBoardList(vo));	// Model 정보 저장
		return "getBoardList.jsp";
	}

}
