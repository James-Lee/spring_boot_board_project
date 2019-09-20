package com.javateam.board_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.javateam.board_project.service.BoardService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("board")
@Slf4j
public class BoardDeleteController {

	@Autowired
	private BoardService boardService;
							
	@RequestMapping(value="/deleteBoardProcREST.do/{boardNum}",
					method = RequestMethod.POST,
					produces = "text/plain; charset=UTF-8")
	public ResponseEntity<String> deleteBoardProc(@PathVariable("boardNum") int boardNum) {

		log.info("############# deleteBoardProcREST.do ##################");
		
		boolean flag = false; // 글삭제 성공여부 플래그 추가
		String msg = "";
		
		/*
		 * 본 게시판에서는 게시글을 삭제하더라도 첨부 파일을 지우지 않고 
		 * 유지하는 것을 전제로 구성되었음을 공지드립니다. 가령 삭제를 시도한 사용자의 변심에 
		 * 따른 요청(클레임)에 대한 대응 복구가 용이해지는 장점이 있습니다.	
	     * 반면 지운 파일을 남겨두면	 서버 디스크의 사용량이 상대적으로 많아지는 단점도 있습니다.   		  
		 */
		flag = boardService.deleteBoard(boardNum);
		
		if (flag == false) {
			msg += "글삭제에 실패하였습니다.";
		} else {
			msg += "글삭제에 성공하였습니다.";
		}
		
		HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.add("Content-Type", "text/html; charset=UTF-8");
		
		return new ResponseEntity<String>(msg, responseHeaders, HttpStatus.OK);
	} //
	
}