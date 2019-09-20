/**
 * 
 */
package com.javateam.board_project.service;

import org.springframework.web.multipart.MultipartFile;
import com.javateam.board_project.domain.FileVO;


/**
 * @author javateam
 *
 */
public interface FileUploadService {
	
	FileVO storeUploadFile(int boardNum, MultipartFile file, String storePath);

}