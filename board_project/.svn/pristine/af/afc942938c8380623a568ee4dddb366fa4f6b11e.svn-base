package com.javateam.board_project.service;

import java.util.List;
import com.javateam.board_project.domain.BoardVO;

public interface BoardService {
	
	void writeBoard(BoardVO boardVO);
	
	List<BoardVO> getAll(int page, int limit);
	
	int count();

	BoardVO getBoard(int id);
	
	// 추가
	void updateReadCount(int boardNum);

	boolean updateBoard(BoardVO boardVO);

	boolean deleteBoard(int boardNum);

	int getBoardNumByLastSeq();

	List<BoardVO> getBoardBySearch(String searchKind, String searchWord, int limit, int page);
}