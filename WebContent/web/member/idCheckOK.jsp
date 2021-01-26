<%@page import="com.book.db.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="description" content="HTML Study">
<meta name="keywords" content="HTML,CSS,XML,JavaScript">
<meta name="author" content="Do">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>책담소</title>
</head>
<body>
	<h3>아이디 중복 확인 결과</h3>
	입력 ID : <strong> ${ requestScope.id } </strong>
	
	<c:choose>
		<c:when test="${ requestScope.flag }">
			<p>사용 가능한 아이디입니다.</p>
			<a href="javascript:apply('${ requestScope.id }')">[적용]</a>
			<script>
				function apply(id){
					opener.document.registerForm.id.value=id;
					window.close(); //창닫기
				}
			</script>
		</c:when>
		<c:otherwise>
			<p style='color: red'>해당 아이디는 사용하실 수 없습니다.</p>
		</c:otherwise>
	</c:choose>
	
	<hr>
	<a href="javascript:history.back()">[다시시도]</a>
	&nbsp; &nbsp;
	<a href="javascript:window.close()">[창닫기]</a>
	
</body>
</html>