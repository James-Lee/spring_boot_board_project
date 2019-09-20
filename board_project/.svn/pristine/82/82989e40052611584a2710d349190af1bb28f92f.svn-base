package com.javateam.board_project.domain;

import java.time.LocalDateTime;
// import java.util.Date;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class BoardDTO {
	
	private int boardNum; // 글 번호
	private String boardWriter; // 글 작성자
	private String boardTitle; // 글 제목
	private String boardContent; // 글 내용
	private MultipartFile boardFileUpload; // 첨부 파일
	private int boardReadCount = 0; // 조회수
	// private Date boardDate; // 작성일
	private LocalDateTime boardDate; // 교정

}