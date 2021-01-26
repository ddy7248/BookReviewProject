<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="description" content="HTML Study">
<meta name="keywords" content="HTML,CSS,XML,JavaScript">
<meta name="author" content="Do">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>책담소</title>
<link rel="stylesheet" type="text/css" href="${ pageContext.servletContext.contextPath }/static/css/board/generalView.css" />
</head>
<body>

	<!--header -->
	<%@ include file="../../view/header.jsp" %>
	
	<div id="container">
		<div class="contents_wrap">
			<div class="title text-center mb-5">
				<h3><b>정보게시판</b></h3>
			</div>
			
			<div class="contents">
				<form action="writeOK.do" method="post">
					<table class="table text-center">
						<tr>
							<td><label for="id"><strong>작성자</strong></label></td>
							<td class="text-left"><strong>${ sessionScope.id }</strong></td>
						</tr>
						<tr>
							<td><label for="title"><strong>제목</strong></label></td>
							<td class="text-left">
								<div class="form-group col-md-3 pl-0"> 
									<input type="text" class="form-control" name="title" id="title"  required />
								</div>
							</td>
						</tr>
						<tr>
							<td><label for="contents"><strong>내용</strong></label></td>
							<td><textarea name="contents" id="contents" class="form-control" cols="80" rows="20"></textarea></td>
						</tr>
					</table>
					<div class="d-flex justify-content-end">
						<a href="list.do"><button type="button" class="btn btn-secondary ml-3">목록</button></a>
						<button type="submit" class="btn btn-secondary ml-3">등록</button>
						<input type="hidden" class="form-control" name="id" id="id" value="${ sessionScope.id }" readonly />
						<input type="hidden" name="boardType" value="information" />
					</div>
				</form>
			</div>
		</div>
	</div>
	
	<!-- footer -->
	<%@ include file="../../view/footer.jsp" %>
</body>
</html>