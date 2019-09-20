package com.javateam.board_project;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.javateam.board_project.domain.BoardVO;
import com.javateam.board_project.service.BoardService;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class GetBoardTest {
	
	@Autowired
	private BoardService svc;
	

	@Test
	public void test() {
		
		BoardVO board = svc.getBoard(1);
		log.info("board : "+board);
		
	}

}