SELECT * 
	  	FROM (  
	  	       SELECT m.*, FLOOR((ROWNUM() - 1)/10 + 1) page  
  		       FROM (  
		               SELECT * FROM board_tbl b   
							 WHERE b.board_title like '%제목%'   
		               ORDER BY b.board_num DESC  
		  	) m   
) WHERE page =1; 