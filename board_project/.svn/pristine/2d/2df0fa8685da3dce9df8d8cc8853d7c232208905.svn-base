/**
 * 
 */
package com.javateam.board_project.service;

import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

/**
 * @author javateam
 *
 */
@Service
@Slf4j
public class FileUploadNamingServiceImpl implements FileUploadNamingService {
	
	/**
	 * @see com.javateam.spring_board.util.FileUploadNamingService
	 * #parseFilePostfix(int, java.lang.String)
	 */
	@Override
	public String parseFilePostfix(int boardNum, String file) {

		log.info("#### 실제 저장 파일로 변환 : 파일 접미어(_ + boardNum) 첨부");
		String result = "";
		
		if (file !=null) {
			// 본 파일명과 확장자 분리 처리
			String[] fileStr = file.split("\\."); 
			String fileName = fileStr[fileStr.length-2];
			String fileExt = fileStr[fileStr.length-1]==null ? "" : fileStr[1];
			
			log.info("#### 저장 파일명 : "+ fileName+"_"+ boardNum+"."+fileExt);
			// 업로드 파일명 형성
			result = fileName + "_" + boardNum + "." + fileExt;
		}
		
		return result;
	} //

	@Override
	public String getOriginalFilename(int boardNum, String file) {
		
		log.info("#### 원래 파일명으로 변환 : 파일 접미어 제거");
		String result = "";
		
		if (file != null ) {
			String[] fileStr = file.split("\\."); 
			String fileName = fileStr[0];
			String fileExt = fileStr[1]==null ? "" : fileStr[1];
					
			int boardNumIdx = fileName.lastIndexOf(boardNum+"");
	
			fileName = fileName.substring(0, boardNumIdx-1);
			
			result = fileName + "." + fileExt;
		}
		
		return result;
	}

}
