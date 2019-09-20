package com.javateam.board_project.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.javateam.board_project.domain.BoardVO;
import com.javateam.board_project.domain.FileVO;
import com.javateam.board_project.service.BoardService;
import com.javateam.board_project.service.FileUploadService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("board")
@Slf4j
public class BoardUpdateController {

	@Autowired
	private FileUploadService fileUploadService;
	
	@Autowired
	private BoardService boardService;

	@RequestMapping(value="/updateBoard.do/{boardNum}", 
					produces="text/html; charset=UTF-8")
	@ResponseBody
	public String updateBoard(@PathVariable("boardNum") int boardNum,
							  @RequestParam(value="update_boardFileUpload_new", required=false) MultipartFile file,
							  HttpServletRequest req) {

		log.info("########### updateBoard ############");
		
		String msg = "";
		boolean flag = false; // 업데이트 성공여부 플래그 추가
		
		if (file != null) {
			log.info("### 첨부 파일 : " + file.getOriginalFilename());
		}	
		
		if (req.getParameter("update_boardTitle") == null || 
				req.getParameter("update_boardTitle").equals(""))
			msg += "글제목을 입력하십시오.\n";

		if (req.getParameter("update_boardContent") == null || 
				req.getParameter("update_boardContent").equals(""))
			msg += "글내용을 입력하십시오.";

		if (!msg.equals("")) return msg; // 오류 있으면 메시지 전송

		log.info("msg : {}", msg);

		// 인자 출력
		log.info("update_boardNum : {}", req.getParameter("update_boardNum"));
		log.info("update_boardWriter : {}", req.getParameter("update_boardWriter"));
		log.info("update_boardTitle : {}", req.getParameter("update_boardTitle"));
		log.info("update_boardContent : {}", req.getParameter("update_boardContent"));
		
		if (file != null) {
			log.info("fileName : {}", file.getOriginalFilename());
		}	

		// 기존 정보 가져오기
		BoardVO boardVO = new BoardVO(); // 갱신할 VO
		BoardVO oldBoardVO = boardService.getBoard(boardNum);
		boardVO = oldBoardVO; // 

		// 게시글 수정
		boardVO.setBoardNum(boardNum);
		boardVO.setBoardWriter(req.getParameter("update_boardWriter"));
		boardVO.setBoardTitle(req.getParameter("update_boardTitle"));
		boardVO.setBoardContent(req.getParameter("update_boardContent"));

		// 주의) 편의상 다른 게시글에서도 동일한 첨부가 있을 경우 문제가 되므로 
		//      기존 파일 삭제는 생략하였습니다. 이러한 경우는 파일 업로드시 계정별 폴더 세분화     
		//      혹은 업로드 파일마다 접미사(순번 등) 등을 이용하여 처리할 수 있습니다.
		// 기존 파일과 비교
		// 추가) 첨부 파일 없을 경우(답글 수정시) 조건 추가 
		if (file==null ||
			file.getOriginalFilename()==null || 
			file.getOriginalFilename().trim().equals("")) {

			log.info("############ 신규 첨부 파일 없을 때 ############");
			
			// 기존 파일 저장
			log.info("기존 첨부 파일 : {}", oldBoardVO.getBoardFileUpload());
			
			boardVO.setBoardFileUpload(oldBoardVO.getBoardFileUpload());
			
			// 추가 : 첨부 파일이 애시당초 없을 경우
			if (oldBoardVO.getBoardFileUpload() == null) {
				boardVO.setBoardFileUpload("");
			}
			
		} else {
			
			/*
			 * 본 게시판에서는 신규 파일이 유입되더라도 기존의 파일을 삭제하지 않고 
			 * 유지하는 것을 전제로 구성되었음을 공지드립니다. 가령 변경을 시도한 사용자의 변심에 
			 * 따른 요청(클레임)에 대한 대응 복구가 용이해지는 장점이 있습니다.	
		     * 반면 지운 파일을 남겨두면	 서버 디스크의 사용량이 상대적으로 많아지는 단점도 있습니다.   
			 */
			log.info("############ 신규 첨부 파일 있을 때 ############");

			log.info("fileName : {}", file.getOriginalFilename());
			
			// 첨부 파일 처리
		    FileVO fileVO = fileUploadService.storeUploadFile(boardNum, file, "d:/upload/");
		    boardVO.setBoardFileUpload(fileVO.getFileName());
		    
		    log.info("첨부 파일 저장 메시지 : " + fileVO.getMsg());
		} //

		// 게시글 갱신 등록일 : 12시간 기준 hh: -> 24시간 기준으로 변경 HH:
		String dateString 
			= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				  .format(new Date(System.currentTimeMillis())).toString();

		log.info("등록일 {}", dateString);
		// boardVO.setBoardDate(new Date(System.currentTimeMillis()));
		boardVO.setBoardDate(LocalDateTime.now()); // 추가/변경
		
		log.info("########## boardVO : {}", boardVO);

		flag = boardService.updateBoard(boardVO);

		if (flag == false) {
			msg += "글 수정에 실패하였습니다.";
		} else {
			msg += "글수정에 성공하였습니다.";
		}
		
		return msg;
	} //

}