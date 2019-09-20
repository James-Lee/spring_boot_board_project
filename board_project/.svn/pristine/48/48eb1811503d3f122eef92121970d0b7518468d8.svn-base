package com.javateam.board_project.controller;

// import java.time.LocalDateTime;
// import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.javateam.board_project.domain.BoardDTO;
import com.javateam.board_project.domain.BoardVO;
import com.javateam.board_project.domain.FileVO;
import com.javateam.board_project.service.BoardService;
import com.javateam.board_project.service.FileUploadService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("board")
@Slf4j
public class BoardWriteController {

	@Autowired
	private FileUploadService fileUploadService;
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping(value="/writeBoardProcREST.do",
					method = RequestMethod.POST,
					produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String writeBoardProc(@ModelAttribute("boardDTO") BoardDTO boardDTO,
							     @RequestParam(value="boardFileUpload", required=false) MultipartFile file) {

		log.info("############# writeBoardProcREST.do ##################");
		
		log.info("VO : {}", boardDTO);
		
		String msg = "";
		int boardNum = 0;
		
        // 추가 : 신규 boardNum 값 구하기 (sequence)
	    boardNum = boardService.getBoardNumByLastSeq();
	    
	    log.info("첨부 파일 : " + file);
	    
		// 첨부 파일 처리
		// 주의) JPA 에서 hibernate_sequence 저장시 다음값(next value:nextval) 생성으로 인한 오류 패치
	    FileVO fileVO = fileUploadService.storeUploadFile(boardNum+1, file, "d:/upload/");
	    
		// 저장 VO 생성
		BoardVO boardVO = new BoardVO(boardDTO);		
		
		log.info("###### 인자 값 BoardVO : {}", boardVO);
		
		boardVO.setBoardFileUpload(!file.isEmpty() && file != null ? fileVO.getFileName() : "");
		
		boardVO.setBoardNum(boardNum+1); 
		log.info("실제 시퀀스 게시글 번호 : " + (boardNum+1));		
		// 주의) JPA 에서 hibernate_sequence 저장시 다음값(next value:nextval) 생성으로 인한 오류 패치
		
		// boardVO.setBoardDate(new Date(System.currentTimeMillis()));
		// boardVO.setBoardDate(LocalDateTime.now()); // 추가/변경
				
		try {
			boardService.writeBoard(boardVO);
			msg += "글쓰기에 성공하였습니다.";
		} catch (Exception e) {
			
			log.error("writeBoard error");
			msg += "글쓰기에 실패하였습니다.";
		} 
		
		return msg;
	} //
	
	@RequestMapping(value="/writeBoardAction.do",
					method = RequestMethod.POST,
					produces = "text/plain; charset=UTF-8")
	public String writeBoardProc(@ModelAttribute("boardDTO") BoardDTO boardDTO) {
		
		log.info("############# writeBoardAction.do ##################");
				
		log.info("VO : {}", boardDTO);
		
	    MultipartFile file = boardDTO.getBoardFileUpload(); // 업로드 파일
        int boardNum = 0;
        
        // 추가 : 신규 boardNum 값 구하기 (sequence)
	    boardNum = boardService.getBoardNumByLastSeq();
	    
	    // 첨부 파일 처리
 		// 주의) JPA 에서 hibernate_sequence 저장시 다음값(next value:nextval) 생성으로 인한 오류 패치
 	    FileVO fileVO = fileUploadService.storeUploadFile(boardNum+1, file, "d:/upload/");	
		
	    log.info("첨부 파일 처리 : "+ fileVO.getMsg());
      
	    BoardVO boardVO = new BoardVO(boardDTO);
	    boardVO.setBoardFileUpload(!boardDTO.getBoardFileUpload().isEmpty() && file != null ? fileVO.getFileName() : "");
	    
	    boardVO.setBoardNum(boardNum+1); 
		log.info("실제 시퀀스 게시글 번호 : " + (boardNum+1));		
		// 주의) JPA 에서 hibernate_sequence 저장시 다음값(next value:nextval) 생성으로 인한 오류 패치
		
	    // boardVO.setBoardDate(new Date(System.currentTimeMillis()));
		
		boardService.writeBoard(boardVO);
		
		return "redirect:/getAllBoards.do/1";
	} //
	
}