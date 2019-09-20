package com.javateam.board_project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.javateam.board_project.domain.BoardVO;
import com.javateam.board_project.domain.PageVO;
import com.javateam.board_project.service.BoardService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class BoardListController {
	
	@Autowired
	private BoardService boardService;
	
	@GetMapping("getAllBoards.do/{page}")
	public String getAllBoards(@PathVariable("page") int page, Model model) {
		
		log.info("게시글 페이징 보기");
		String listPage = "boardList";
		int maxSize = boardService.count();
		
		List<BoardVO> boardList = boardService.getAll(page, 10);
		
		// 페이지 인자 생성
		// 총 페이지수
		int maxPage = (int)((double)maxSize/10 + 0.95);
		// 현재 페이지에서 보여줄 수 있는 시작 페이지 수
		int startPage = (((int) ((double)page / 10 + 0.9)) - 1) * 10 + 1;
		// 현재 페이지에서 보여줄 수 있는 마지막 페이지 수
		int endPage = startPage + 10 - 1;
		
		if (endPage > maxPage) endPage = maxPage;
		
		PageVO pageVO = new PageVO();
		pageVO.setEndPage(endPage);
		pageVO.setListCount(boardList.size());
		pageVO.setMaxPage(maxPage);
		pageVO.setPage(page);
		pageVO.setStartPage(startPage);
		
		// 
		model.addAttribute("boardList", boardList);
		model.addAttribute("pageVO", pageVO);
		
		return listPage;
	} //

}
