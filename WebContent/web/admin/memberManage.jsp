<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="description" content="HTML Study">
<meta name="keywords" content="HTML,CSS,XML,JavaScript">
<meta name="author" content="Do">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>책담소</title>
<link rel="stylesheet" type="text/css" href="${ pageContext.servletContext.contextPath }/static/css/board/list.css" />
</head>
<body>
	<!--header -->
	<%@ include file="../view/header.jsp"  %>
	
	<!-- container -->
	<div id="container">
		
		<div class="contents_wrap">
		
			<div class="title text-center mb-5">
				<h3><b>회원관리</b></h3>
			</div>
			
			<div class="contents">
				<div class="board_list_wrap">
					<table class="board_list table table-hover">
						<thead>
							<tr>
								<th>아이디</th>
								<th>이름</th>
								<th>성별</th>
								<th>생년월일</th>
								<th>이메일</th>
								<th>전화번호</th>
								<th>가입일</th>
								<th>삭제</th>
							</tr>
						</thead>
						<tbody>		
							<c:forEach var="dto" items="${ memberList }">
								<tr>
									<td>${ dto.id }</td>
									<td>
										<a href="memberView.ad?id=${dto.id}">${ dto.name }</a>
									</td>
									<td>${ dto.gender }</td>
									<td>${ dto.birth }</td>
									<td>${ dto.email }</td>
									<td>${ dto.phoneNum }</td>
									<td><fmt:formatDate type="date" value="${dto.joinDate}" pattern="yyyy-MM-dd" /></td>
									<td><a href="member/delete.ad?id=${ dto.id }"><button type="button" class="btn btn-secondary">삭제</button></a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>

	<!-- footer -->
	<%@ include file="../view/footer.jsp" %>
	
</body>
</html>