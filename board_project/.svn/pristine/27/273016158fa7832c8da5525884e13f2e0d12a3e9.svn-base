package com.javateam.board_project.controller;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.javateam.board_project.domain.BoardVO;
// import com.javateam.board_project.service.BoardService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class BoardController {
	
	// @Autowired
	// private BoardService boardService;
	
	@GetMapping("/")
	public String home(Model model) {
		
		log.info("home");
		
		model.addAttribute("boardVO", new BoardVO());
		
		return "redirect:/getAllBoards.do/1";
	}
	
	/*
	@PostMapping("writeBoardAction.do")
	public String writeAction(@ModelAttribute("boardVO") BoardVO boardVO) {
		
		log.info("게시글 등록 처리");
		String result = "success";
		
		log.info("boardVO : "+boardVO);
		
		boardService.writeBoard(boardVO);
		
		return result;
	} */

}