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
<link rel="stylesheet" type="text/css" href="${ pageContext.servletContext.contextPath }/static/css/member/login.css" />
</head>
<body>

	<!--header -->
	<%@ include file="../view/header.jsp"  %>
	
	<!-- container -->
	<div id="container">
		<div class="contents_wrap">
			<div class="contents">
				<form action="loginOK.do" method="post">
					<div class="text-center mb-4">
						<a href="index.do"><img src="${ pageContext.servletContext.contextPath }/static/images/logo.png" alt="로고" width="200px"/></a>
					</div>
					<div class="form-group">
						<label for="id">아이디</label>
						<input type="text" class="form-control" name="id" id="id" placeholder="ID" autofocus required />
					</div>
				
					<div class="form-group">
						<label for="pw">비밀번호</label>
						<input type="password" class="form-control" name="password" id="pw" placeholder="Password" required />
					</div>
					<div class="form-group">
						<button type="submit" class="form-control btn btn-secondary">로그인</button>
					</div>
					<div class="form-group">
						<a href="register.do"><button type="button" class="form-control btn btn-secondary">회원가입</button></a>
					</div>
					
				</form>
			</div>
		</div>
	</div>
	
	<!-- footer -->
	<%@ include file="../view/footer.jsp" %>
</body>
</html>