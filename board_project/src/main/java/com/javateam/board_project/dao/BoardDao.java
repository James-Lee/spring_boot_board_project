package com.javateam.board_project.dao;

// import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.javateam.board_project.domain.BoardVO;

public interface BoardDao extends PagingAndSortingRepository<BoardVO, Integer> {
	
	// 추가 : 게시판 검색 SQL
	public static final String BOARD_SEARCH_BY_TITLE_SQL 
	  = "SELECT * " +
	  	"     FROM (" + 
	  	"            SELECT m.*, FLOOR((ROWNUM() - 1)/:limit + 1) page" + 
  		"            FROM (" + 
		"                    SELECT * FROM board_tbl b " + 
		"					 WHERE b.board_title like %:searchWord% " + 
		"                    ORDER BY b.board_num DESC" + 
		"       	) m " + 
		") WHERE page = :page"; 

	public static final String BOARD_SEARCH_BY_CONTENT_SQL 
	  = "SELECT * " +
	  	"     FROM (" + 
	  	"            SELECT m.*, FLOOR((ROWNUM() - 1)/:limit + 1) page" + 
		"            FROM (" + 
		"                    SELECT * FROM board_tbl b " + 
		"					 WHERE b.board_content like %:searchWord% " + 
		"                    ORDER BY b.board_num DESC" + 
		"       	) m " + 
		") WHERE page = :page"; 

	
	BoardVO save(BoardVO boardVO);

	Page<BoardVO> findAll(Pageable pageable);

	// S save(BoardVO boardVO);
	long count();
	
	BoardVO findById(int id);
	
	@Modifying
	@Query("update BoardVO b set b.boardReadCount = b.boardReadCount+1 "
		 + "where b.boardNum=?1")
	void updateReadCount(int boardNum);
	
	// 추가
	default void updateBoard(BoardVO boardVO) {
		
		BoardVO defaultBoardVO = findById(boardVO.getBoardNum());
		
		// 변경사항
		defaultBoardVO.setBoardTitle(boardVO.getBoardTitle());
		defaultBoardVO.setBoardContent(boardVO.getBoardContent());
		defaultBoardVO.setBoardFileUpload(boardVO.getBoardFileUpload());
		// defaultBoardVO.setBoardDate(new Date(System.currentTimeMillis())); // 패치
		
		this.save(defaultBoardVO);
	}

	void deleteById(int boardNum);
	
	@Query(value="SELECT hibernate_sequence.nextval FROM dual", nativeQuery=true)
	int getBoardNumByLastSeq();
	
	@Query(value="SELECT count(*) FROM board_tbl b WHERE b.board_title like %:searchWord%", nativeQuery=true)
	int getCountBySearchByTitle(@Param("searchWord") String searchWord);
	
	@Query(value="SELECT count(*) FROM board_tbl b WHERE b.board_content like %:searchWord%", nativeQuery=true)
	int getCountBySearchByContent(@Param("searchWord") String searchWord);

	@Query(value=BOARD_SEARCH_BY_TITLE_SQL, nativeQuery=true)
	List<BoardVO> getBoardBySearchByTitle(@Param("searchWord") String searchWord, 
								   		  @Param("limit") int limit, 
								   		  @Param("page") int page);
	
	@Query(value=BOARD_SEARCH_BY_CONTENT_SQL, nativeQuery=true)
	List<BoardVO> getBoardBySearchByContent(@Param("searchWord") String searchWord, 
								   		    @Param("limit") int limit, 
								   		    @Param("page") int page);
	
}