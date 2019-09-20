package com.javateam.board_project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javateam.board_project.dao.BoardDao;
import com.javateam.board_project.domain.BoardVO;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private BoardDao dao;

	@Override
	public void writeBoard(BoardVO boardVO) {

		log.info("Service writeBoard");
		dao.save(boardVO);
	}

	@Override
	public List<BoardVO> getAll(int page, int limit) {
		
		log.info("Service getAll");
		
		Pageable pageable = PageRequest.of(page-1, limit);
		Page<BoardVO> list = dao.findAll(pageable);
		
		return list.getContent();
	}

	@Override
	public int count() {
		
		log.info("Service count");
		return (int)dao.count();
	}

	@Override
	public BoardVO getBoard(int id) {

		log.info("Service getBoard");
		return dao.findById(id);
	}

	@Override
	public void updateReadCount(int boardNum) {

		log.info("Service updateReadCount");
		dao.updateReadCount(boardNum);
	}

	@Override
	public boolean updateBoard(BoardVO boardVO) {

		log.info("Service updateBoard");
		boolean flag = false; 
		
		try {
			dao.updateBoard(boardVO);
			flag = true;
		} catch (Exception e) {
			log.error("updateBoard error");
			flag = false;
		} 
		
		return flag;
	}

	@Override
	public boolean deleteBoard(int boardNum) {

		log.info("Service deleteBoard");
		boolean flag = false; 
		
		try {
			dao.deleteById(boardNum);
			flag = true;
		} catch (Exception e) {
			log.error("deleteBoard error");
			flag = false;
		} 
		
		return flag;
	}

	@Override
	public int getBoardNumByLastSeq() {

		log.info("Service getBoardNumByLastSeq");
		return dao.getBoardNumByLastSeq();
	}

	@Override
	public List<BoardVO> getBoardBySearch(String searchKind, String searchWord, int limit, int page) {

		log.info("Service getBoardBySearch");
		List<BoardVO> result = null;
		
		if (searchKind.equals("제목")) {
			result = dao.getBoardBySearchByTitle(searchWord, limit, page);
		} else {
			result = dao.getBoardBySearchByContent(searchWord, limit, page);
		}
		
		return result;
	} //

	@Override
	public int getCountBySearch(String searchKind, String searchWord) {
		
		log.info("Service getCountBySearch");
		int result = 0;
		
		if (searchKind.equals("제목")) {
			result = dao.getCountBySearchByTitle(searchWord);
		} else {
			result = dao.getCountBySearchByContent(searchWord);
		}
		
		return result;
	}

}