/**
 * 
 */
package com.javateam.board_project.service;

/**
 * 업로드된 파일 중복 방지를 위한  접미사 처리 
 * ex) abcd.jpg -> abcd_게시글 번호.jpg
 * 
 * @author javateam
 *
 */
public interface FileUploadNamingService {
	
	String parseFilePostfix(int boardNum, String destFile);
	String getOriginalFilename(int boardNum, String fileName);

}