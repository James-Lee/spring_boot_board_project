/**
 * 
 */
package com.javateam.board_project.domain;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import com.javateam.board_project.util.LocalDateTimeDBConverter;

import lombok.Data;

/**
 * @author java
 *
 */
@Data
@Entity
@Table(name = "board_tbl")
public class BoardVO {

	/** 게시글 아이디 */
	@Id
	@GeneratedValue
	@Column(name = "board_num", nullable=false)
	private int boardNum;
	
	/** 게시글 제목 */
	@Column(name = "board_title", nullable=false)
	private String boardTitle;
	
	/** 게시글 작성자 */
	@Column(name = "board_writer", nullable=false)
	private String boardWriter;
	
	/** 게시글 내용 */
	@Column(name = "board_content", nullable=false)
	private String boardContent;
	
	/** 게시글 작성일 */
	// 추가/변경 : 시분초 미기록 문제
	@LastModifiedDate // 추가
	@CreationTimestamp // 추가
	@Convert(converter = LocalDateTimeDBConverter.class) 
	@Column(name = "board_date", nullable=false)
	// private Date boardDate = new Date(System.currentTimeMillis());
	private LocalDateTime boardDate;
	
	/** 게시글 파일 업로드(첨부 파일) */
	@Column(name = "board_file_upload")
	private String boardFileUpload;
	
	@Column(name = "board_read_count")
	private int boardReadCount;
		
	public BoardVO() {}
	
	public BoardVO(BoardDTO boardDTO) {
		this.boardNum = boardDTO.getBoardNum();
        this.boardWriter = boardDTO.getBoardWriter();
        this.boardTitle = boardDTO.getBoardTitle();
        this.boardContent = boardDTO.getBoardContent();
        this.boardFileUpload = boardDTO.getBoardFileUpload().getOriginalFilename(); // 파일명 저장
        this.boardReadCount = boardDTO.getBoardReadCount();
        this.boardDate = boardDTO.getBoardDate();
	}
	
}