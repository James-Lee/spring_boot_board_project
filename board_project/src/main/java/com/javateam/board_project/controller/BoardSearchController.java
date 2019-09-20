package com.javateam.board_project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.javateam.board_project.domain.BoardVO;
import com.javateam.board_project.domain.PageVO;
import com.javateam.board_project.service.BoardService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class BoardSearchController {
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("/board/boardListbySearch.do")
	public String listBoardbySearch(@RequestParam(value="page", defaultValue="1") int page,
								    @RequestParam("search_kind") String searchKind,
									@RequestParam("search_word") String searchWord,
									Model model) {
		
		log.info("########## 검색 게시글 보기");
		
		int limit = 10; // 페이지당 글수
		List<BoardVO> boardList;
		
		// 총 게시글 & 총 게시글 수 
		boardList= boardService.getBoardBySearch(searchKind, searchWord.trim(), limit, page);
		int listCount = boardService.getCountBySearch(searchKind, searchWord);
		
		log.info("검색 게시글 수 : {}", listCount);
		
		// 총 페이지 수
   		int maxPage=(int)((double)listCount/limit+0.95); //0.95를 더해서 올림 처리
		// 현재 페이지에 보여줄 시작 페이지 수 (1, 11, 21,...)
   		int startPage = (((int) ((double)page / 10 + 0.9)) - 1) * 10 + 1;
		// 현재 페이지에 보여줄 마지막 페이지 수(10, 20, 30, ...)
   	    int endPage = startPage + 10 - 1;
   	    
   	    if (endPage> maxPage) endPage= maxPage;
   	    
   	    PageVO pageVO = new PageVO();
		pageVO.setEndPage(endPage);
		pageVO.setListCount(listCount);
		pageVO.setMaxPage(maxPage);
		pageVO.setPage(page);
		pageVO.setStartPage(startPage);
		
		model.addAttribute("pageVO", pageVO);
		model.addAttribute("boardList", boardList);
		
		// 추가 : 페이징 부분에서 검색 페이지 주소 반영위한 플래그 변수
		// 검색어 재전송
		model.addAttribute("search_YN", "search");
		model.addAttribute("search_kind", searchKind);
		model.addAttribute("search_word", searchWord);
		
		return "/boardList";
	} //
	
}