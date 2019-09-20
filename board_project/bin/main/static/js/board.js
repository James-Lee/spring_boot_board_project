/**
 * jQuery
 */
$(document).ready(function(){
	
	// 게시글 보기
	$("a[id^=boardNum_").click(function(e) {
		
		var boardNum = e.target.id.substring(9);
		
		$.ajax({
			
			url : contextPath + "/board/boardDetailREST.do/"+boardNum,
			contentType : "application/json",
			type : "POST",
			success : function(article) {
				
				$("div#viewModal #boardNum").val(article.boardNum);
				$("div#viewModal #boardWriter").val(article.boardWriter);
				$("div#viewModal #boardTitle").val(article.boardTitle);
				$("div#viewModal #boardContent").val(article.boardContent);
				
				// 글 조회수 갱신
				$("div#wrap table #boardReadCount_"+article.boardNum).text(article.boardReadCount);
				
				var boardFileUpload = article.boardFileUpload=="" ? 
						        "파일없음": article.boardFileUpload;
				
				console.log(boardFileUpload);
				
				if (boardFileUpload == '파일없음')
					$("div#viewModal #boardFileUpload").text(boardFileUpload);
				else {
				  
				  // 파일 중복 방지위해 접미사 부착 파일 처리
				  var originalFile = boardFileUpload; 
				  
				  $("div#viewModal #boardFileUpload").html("<a href=\"" 
				   		  + contextPath + "/upload/" + article.boardNum + "/"+boardFileUpload+"\" >"+boardFileUpload+"</a>");	
				} //
				
				// 버튼 아이디 변경 : ex) "updateContentBtn_"+article.boardNum
				$("button").attr("id", "updateContentBtn_"+article.boardNum);
		
				// 글 수정 전송 버튼 아이디 변경 : ex) "update_btn_"+article.boardNum
				$("#update_btn_").attr("id", "update_submit_btn_"+article.boardNum);
	
				// 추가 : 글 삭제 버튼 아이디 변경 
				$("button").attr("id", "deleteContentBtn_"+article.boardNum);
				
			} //success	
		
		}); // ajax
		
	});
	// 게시글 보기 끝
	
	// 게시글 팝업(modal) 수정 
	$("button[id^=updateContentBtn_]").click(function (e) {
		
		var boardNum = e.target.id.substring(17); // "updateContentBtn_" 뒤부분 "글번호" 취득
		
		$.ajax ({
			
			url : contextPath + "/board/boardDetailREST.do/"+boardNum,
			contentType : "application/json",
			type : "POST",
			success : function (article) {
			
				// 기존 게시글 내용
				// modal
				$("form#updateForm #update_boardNum").val(article.boardNum);
				$("form#updateForm #update_boardWriter").val(article.boardWriter);
				$("form#updateForm #update_boardTitle").val(article.boardTitle);
				$("form#updateForm #update_boardContent").val(article.boardContent);		

				
				// 중복 첨가 방지를 위해 기존 필드 있는지 여부 점검
				// alert("첨부 파일 필드 여부 : "+$("#updateForm").find("label[for=update_boardFileUpload_new]").text());
				var fileFldFlag =$("#updateForm").find("span label[for=update_boardFileUpload_new]").text().trim();
				
				if (fileFldFlag == '') { // 파일 필드가 첨부되어 있지 않으면 ... 
			
					var fileFldContent  = "<div name=\"update_file_section\" class=\"input-group\">"
										+ "<div class=\"input-group-prepend\">"
										+ "<label for=\"update_boardFileUpload\" class=\"control-label input-group-text ml-2\">"
										+ "첨부파일 :</label>"
										+ "</div>"		
										+ "<div class=\"custom-file\">"
										+ "		<span class=\"btn btn-default btn-file\">"
										+ "			<label for=\"update_boardFileUpload_new\" class=\"custom-file-label\">파일 선택</label>"
										+ "			<input type=\"file\" class=\"custom-file-input\" id=\"update_boardFileUpload_new\" name=\"update_boardFileUpload_new\" />" 
										+ "		</span>" 
										+ "</div>"
										+ "<div class=\"input-group\">"
										+ "		<div class=\"input-group-prepend\">"	
										+ "			<div class=\"ml-3 mt-2\" id=\"update_boardFileUpload\"></div>"
										+ "		</div>"
										+ "</div>";
				
					// 파일 필드 추가
					$("form[id=updateForm] #article_content").append(fileFldContent);	// fileFldContent
					
	
					
					var boardFileUpload = article.boardFileUpload == null ? 
					        "파일없음": article.boardFileUpload;
					
					console.log("# 첨부 파일 : "+boardFileUpload);
			
					if (boardFileUpload == '파일없음')
						$("#update_boardFileUpload").text(boardFileUpload);
					else {
					
					  // 이 부분도 절대 경로로 변경
					  $("#update_boardFileUpload").html("<a href="
					  	    + contextPath + "/upload/" + article.boardNum + "/"+boardFileUpload+"/\">"+boardFileUpload+"</a>");	
					  
					  console.log("original file : "+originalFile);	
						
					  // 파일 중복 방지위해 접미사 부착 파일 처리					  
					  var originalFile = boardFileUpload.split(".")[0];
					  var ext = boardFileUpload.split(".")[1];
					  
					  originalFile = originalFile + "." + ext; 
					  
					  $("#update_boardFileUpload").html("<a href="
							  + contextPath + "/upload/" + article.boardNum + "/"+boardFileUpload+"/\">"+boardFileUpload+"</a>");						  
					} //
					
				} 
				
				// 조회수 업데이트
				$tempId = "#boardReadCount_"+article.boardNum;
				$($tempId).text(article.boardReadCount);
			}					
			
		}); // ajax
		
	}); // 게시글 팝업(modal) 수정 끝
	
	// 게시글 팝업(modal) 수정 : 전송
	$("button[id^=update_btn_]").click(function (e) {
		
		// alert("글수정 요청");
		
		var boardNum = e.target.id.substring(17); // "updateContentBtn_" 뒤부분 "글번호" 취득
		
		var form = $('form#updateForm')[0];
        var formData = new FormData(form);        
        
        $.ajax ({
        	
			url : contextPath + "/board/updateBoard.do/"+boardNum,

			cache: false,	 
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            
			type : "POST",
			data : formData,
			
			success : function (result) {
			
				// alert(result);
				location.reload(); // 페이지 리프레시(재설정)
			}, 
			
			error : function(xhr, status) {
                 
                console.log(xhr+" : "+status); // 에러 코드
            } 
		}); // ajax
		
	}); // 게시글 팝업(modal) 수정 : 전송 끝 
	
	//////////////////////////////////////////////////////////
	
	// 게시글 삭제 
	$("button[id^=deleteContentBtn_]").click(function (e) {
		
		var boardNum = e.target.id.substring(17); // "deleteContentBtn_" 뒤부분 "글번호" 취득
		// alert(boardNum);
		
		// 삭제폼에 글번호 표시(입력)
		$("form#boardDeleteForm input[name=boardNum]").val(boardNum);
		
	});	// 게시글 삭제  끝
	
	// 게시글 삭제 : 전송
	$("button[id^=delete_submit_btn_]").click(function (e) {
		
		// alert("글삭제 요청");
		
		var boardNum = e.target.id.substring(17); // "delete_subumit_btn_" 뒤부분 "글번호" 취득
		
		// alert(boardNum);
		
        $.ajax ({
        	
			url : contextPath + "/board/deleteBoardProcREST.do/"+boardNum,
			type : "POST",
			
			success : function (result) {
			
				alert(result);
				location.reload(); // 페이지 리프레시(재설정)
			}, 
			
			error : function(xhr, status) {
                 
                console.log(xhr+" : "+status); // 에러 코드
            } 
		}); // ajax
		
	}); // 게시글 삭제 : 전송 끝 
	
});

//angularJS
var app=angular.module('boardFormApp', []); 

// 글쓰기 제어 : 폼점검을 위해서는 형식적으로  Controller 부분을 기입해야 됨.
app.controller('boardFormController', function($scope) {
	
	// 게시글 쓰기폼 초기화 : 초기화하지 않을 경우 이전의 글들(쓰다가 빠져나갈 경우)이 남아 있을 수 있음.
	$scope.initWriteForm = function($event) {
		
		$scope.boardWriter = "";
		$scope.boardTitle = "";
		$scope.boardContent = "";
		$scope.boardFileUpload = "";
		
	} // 게시글 쓰기폼 초기화 끝 
	
	// 게시글 수정폼 초기화 : 초기화하지 않을 경우 이전의 글들이 남아 있을 수 있음.
	$scope.initUpdateForm = function($event) {
		
		// alert("수정");
		
		// 폼점검 플래그 초기화
		$scope.updateForm.update_boardTitle.$valid = "true";
		$scope.updateForm.update_boardContent.$valid = "true";
		
	} // 게시글 수정폼 초기화 끝 
	
	// 글쓰기  폼점검
	$scope.writeFormCheck = function($event) {
		
		console.log("글쓰기 폼점검");
		
		if ($scope.boardForm.boardWriter.$valid == true &&
			$scope.boardForm.boardTitle.$valid == true &&
			$scope.boardForm.boardContent.$valid == true)
		{
	  		console.log("글쓰기 요청 전송");
		
			var form = $('form#boardForm')[0];
	        var formData = new FormData(form);
	        
	        $.ajax ({
	        	
				url : contextPath + "/board/writeBoardProcREST.do",
	
				cache: false,	 
	            async: false,
	            cache: false,
	            contentType: false,
	            processData: false,
	            
				type : "POST",
				data : formData,
				
				success : function (result) {
				
					// alert(result);
					location.reload(); // 페이지 리프레시(재설정)
				}, 
				
				error : function(xhr, status) {
	                 
	                console.log(xhr+" : "+status); // 에러 코드
	            } 
			}); // ajax
	  		
		} else {
			
			alert("폼점검 미완료")
			document.boardForm.boardWriter.focus(); 
		} //
		
	} // 글쓰기  폼점검 끝
	
	
	// 목록 버튼 시작
	$("#list_btn").click(function(e) {
		
		// alert("목록");
		location.href = contextPath;
		
	});
	// 목록 끝

});