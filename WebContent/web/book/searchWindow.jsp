<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

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

	<!--header -->
	<%@ include file="../view/header.jsp"  %>
	
	<!-- container -->
	<div id="container">
		
		<form method="post" action="book/search.do?curPage=1">
				<div class="search-bar input-group">
					<select name="queryType">
						<option value="all">통합검색</option>
						<option value="title">책제목</option>
						<option value="author">저자</option>
						<option value="publisher">출판사</option>
					</select>&nbsp <input type="search" name="query" class="form-control"
						placeholder="" aria-label="Recipient's username"
						aria-describedby="button-addon2" />
					<div class="input-group-append">
						<button class="btn btn-outline-secondary" type="submit"
							id="button-addon2">
							<img
								src="${ pageContext.servletContext.contextPath }/static/images/magnifier2.png"
								alt="검색" width="20px" class="search-img" /> 검색
						</button>
					</div>
				</div>
				
			</form>
			
	</div>

	<!-- footer -->
	<%@ include file="../view/footer.jsp" %>
</body>
</html>