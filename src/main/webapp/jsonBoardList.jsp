<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>    
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSON 게시글 조회</title>
<script src="http://code.jquery.com/jquery-3.1.1.js"></script>
<script>
$(function() {
	$.getJSON('dataTransform.do', function(data,textStatus) {
		$.each(data, function() {
			var date = new Date(this.regDate);
			$('#boardList').append(
				'<tr>' +
					'<td>'+this.seq+'</td>'+
					'<td align="left"><a href="getBoard.do?seq=' + this.seq + ' ">' + this.title + '</a></td>' + 
					'<td>' + this.writer + '</td>' + 
					'<td>' + date.getFullYear() + '-' + ((date.getMonth() + 1) <= 9 ? "0" : "") + (date.getMonth()+1) + '-' + ( (date.getDate() <= 9) ? "0" : "") + date.getDate() + '</td>' + 
					'<td>' + this.cnt + '</td>' + 
				'</tr>'
			);
		});
	});
});
</script>
</head>
<body>
<center>
<h1>JSON BoardList Test</h1>

<!-- 검색 종료 -->
<table id="boardList" border="1" cellpadding="0" cellspacing="0" width="700">
<tr>
	<th bgcolor="orange" width="100">순번</th>
	<th bgcolor="orange" width="200">제목</th>
	<th bgcolor="orange" width="150">작성자</th>
	<th bgcolor="orange" width="150">등록일자</th>
	<th bgcolor="orange" width="100">횟수</th>
</tr>

</table>
<br>
</center>
</body>
</html>