package com.javateam.board_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javateam.board_project.domain.BoardVO;
import com.javateam.board_project.service.BoardService;
import com.javateam.board_project.service.FileUploadNamingService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("board")
public class BoardViewController {
	
	@Autowired
	private BoardService boardService;
	
	// 추가 : 파일 처리에 따른 패키 처리
	@Autowired
	private FileUploadNamingService fileService; 
	
	@RequestMapping(value="/boardDetailREST.do/{boardNum}", produces="application/json; charset=UTF-8")
	public ResponseEntity<String> boardDeailtREST(@PathVariable("boardNum") int boardNum) {
		
		log.info("개별 게시글 보기 : REST");
		
		// http header
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "application/json; charset=UTF-8");
		
		// 조회수 갱신
		boardService.updateReadCount(boardNum);
		
		String json = "";
		ObjectMapper mapper = new ObjectMapper();
		
		BoardVO boardVO = boardService.getBoard(boardNum);
		
		log.info("##########  boardVO.getBoardFileUpload() : " 
				+ (boardVO.getBoardFileUpload().equals("") ? "파일 없음" : "파일 있음"));
		
		// 첨부 파일 : 첨부 파일 + 게시글 아이디 -> 저장
		// if (boardVO.getBoardFileUpload() != null) { 
		if (!boardVO.getBoardFileUpload().equals("")) { // 추가 : 교정
			
			// 추가 : 교정
			// String fileName = boardVO.getBoardFileUpload().replaceAll("_"+boardVO.getBoardNum(), ""); // 교정
			// 가령 boardNum=107이면서 파일명이 __file_102_107_107.zip일 경우 끝의 107만 배제하도록 교정
			String fileName = fileService.getOriginalFilename(boardVO.getBoardNum(), boardVO.getBoardFileUpload());
			
			boardVO.setBoardFileUpload(fileName);
		}
		
		try {
			json = mapper.writeValueAsString(boardVO);
		} catch (JsonProcessingException e) {
			log.error("json error");
			e.printStackTrace();
		}
		
		return new ResponseEntity<String>(json, responseHeaders, HttpStatus.OK);
	}

}