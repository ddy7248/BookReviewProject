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
<script>
	function go(){
		history.go(-1);
	}
</script>
<link rel="stylesheet" type="text/css" href="${ pageContext.servletContext.contextPath }/static/css/board/view.css" />

</head>
<body>

	<!--header -->
	<%@ include file="../../view/header.jsp" %>
	
	<div id="container">
		<div class="contents_wrap">
			<div class="contents">
				<form action="writeOK.do" method="post">
					<table class="table text-center">
						<!-- 도서정보 출력-->
						<tr class="row">
							<td class="col text-center"><img src="${ sessionScope.dto.coverLargeUrl }" alt="${ sessionScope.dto.title }" /></td>
							<td	class="col-7 d-flex align-items-start flex-column bd-highlight mb-3">
								<div class="p-2 bd-highlight">
									<h4>
										<strong>${ sessionScope.dto.title }</strong>
									</h4>
								</div>
								<div class="p-2 bd-highlight">${ sessionScope.dto.author }</div>
								<div class="p-2 bd-highlight">${ sessionScope.dto.publisher }</div>
								<!-- 별점 영역 -->
								<div class="p-2 bd-highlight">
									<c:forEach var="i" begin="1" end="5" step="1">
										<input type="radio" name="starPoint" id="star-${i}" value="${ i }" required />
									</c:forEach>
								
									<section>
									  <label for="star-1">
									    <svg width="255" height="240" viewBox="0 0 51 48">
									      <path d="m25,1 6,17h18l-14,11 5,17-15-10-15,10 5-17-14-11h18z"/>
									    </svg>
									  </label>
									  <label for="star-2">
									    <svg width="255" height="240" viewBox="0 0 51 48">
									      <path d="m25,1 6,17h18l-14,11 5,17-15-10-15,10 5-17-14-11h18z"/>
									    </svg>
									  </label>
									  <label for="star-3">
									    <svg width="255" height="240" viewBox="0 0 51 48">
									      <path d="m25,1 6,17h18l-14,11 5,17-15-10-15,10 5-17-14-11h18z"/>
									    </svg>
									  </label>
									  <label for="star-4">
									    <svg width="255" height="240" viewBox="0 0 51 48">
									      <path d="m25,1 6,17h18l-14,11 5,17-15-10-15,10 5-17-14-11h18z"/>
									    </svg>
									  </label>
									  <label for="star-5">
									    <svg width="255" height="240" viewBox="0 0 51 48">
									      <path d="m25,1 6,17h18l-14,11 5,17-15-10-15,10 5-17-14-11h18z"/>
									    </svg>
									  </label>
									</section>
								</div>
								
							</td>
						</tr>
						<tr>
							<td>
								<textarea name="contents" class="form-control" cols="80" rows="20" placeholder="이 책은 여러분에게 어떤 생각의 변화를 주었나요?"></textarea>
							</td>
						</tr>
					</table>
					<div class="d-flex justify-content-end">
						<button type="button" onclick="go()" class="btn btn-secondary ml-3">목록</button>
						<input type="submit" class="btn btn-secondary ml-3" value="등록" />
						<input type="hidden" name="imgURL" value="${ sessionScope.dto.coverLargeUrl }" />
						<input type="hidden" name="bookTitle" id="bookTitle" value="${ sessionScope.dto.title }" required />
						<input type="hidden" name="author" id="author" value="${ sessionScope.dto.author }" required />
						<input type="hidden" name="publisher" id="publisher" value="${ sessionScope.dto.publisher }" required />
					</div>
				</form>
			</div>
		</div>
	</div>
	
	<!-- footer -->
	<%@ include file="../../view/footer.jsp" %>
</body>
</html>