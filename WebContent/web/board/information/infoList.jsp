<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
	<%@ include file="../../view/header.jsp" %>
	
	<div id="container">
	
		<div class="contents_wrap">
			<div class="title text-center mb-5">
				<h3><b>정보게시판</b></h3>
			</div>
			
			<div class="contents">
				
				<!-- 게시판 -->
				<div class="board_list_wrap">
					<table class="board_list table table-hover">
						<thead>
							<tr>
								<th>유형</th>
								<th>작성자</th>
								<th>제목</th>
								<th>조회수</th>
								<th>등록일</th>
							</tr>
						</thead>
						
						<tbody>
							<!-- 글 정보 출력 -->
							<c:if test="${ list != null }">
								<c:forEach var="dto" items="${ list }">
									<tr>
										<td>
											<c:if test="${ dto.boardType eq 'information' }">정보</c:if>
											<c:if test="${ dto.boardType ne 'information' }">${ dto.boardType }</c:if> 
										</td>
										<td>${ dto.id }</td>
										<td>
											<a href="view.do?no=${ dto.no }&boardType=${ dto.boardType }">${ dto.title }</a>
												<c:if test="${ dto.stepNum != 0 }">[${ dto.stepNum }]</c:if>
										</td>
										<td>${ dto.rCnt }</td>
										<td><fmt:formatDate type="date" value="${dto.wTime}" pattern="yyyy-MM-dd" /></td>
									</tr>
								</c:forEach>
							</c:if>
						</tbody>
						
					</table>
				</div>
				
				<!-- 메뉴 버튼 -->
				<div class="d-flex justify-content-end">
					<a href="index.do" class="ml-3"><button type="button" class="btn btn-secondary">메인페이지</button></a>
					<c:if test="${ sessionScope.id != null }"><a href="write.do" class="ml-3"><button type="button" class="btn btn-secondary">글쓰기</button></a></c:if>
				</div>
				
				<!-- 페이지 -->
				<div class="text-center">
					<c:if test="${ list != null }">
						<!-- 전체 목록 페이징 -->
						<c:if test="${ searchFlag ne true }">
							<c:forEach var="i" begin="1" end="${ requestScope.pageNum }" step="1">
								<c:choose>
									<c:when test="${i == requestScope.curPage }">
										<button type="button" class="btn btn-secondary">${ i }</button>
									</c:when>
									<c:otherwise>
										<a href="list.do?curPage=${ i }">
											<button type="button" class="btn btn-outline-secondary">${ i }</button>
										</a>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:if>
						
						<!-- 검색 결과 페이징 -->
						<c:if test="${ searchFlag eq true }">
							<c:forEach var="i" begin="1" end="${ requestScope.pageNum }" step="1">
								<c:choose>
									<c:when test="${i == requestScope.curPage }">
										<button type="button" class="btn btn-secondary">${ i }</button>
									</c:when>
									<c:otherwise>
										<a href="searchView.do?searchType=${ searchType }&keyword=${ keyword }&curPage=${ i }">
											<button type="button" class="btn btn-outline-secondary">${ i }</button>
										</a>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:if>
					</c:if>
				</div>
				
				<!-- 검색 -->
				<div class="d-flex justify-content-end mt-5">
					<form action="searchView.do" method="get">
						<div class="search-bar input-group">
							<select name="searchType" class="form-control">
								<option value="all"
									<c:if test="${ searchType eq 'all' }">selected</c:if> 
								>통합검색</option>
								<option value="title"
									<c:if test="${ searchType eq 'title' }">selected</c:if> 
								>제목</option>
								<option value="contents"
									<c:if test="${ searchType eq 'contents' }">selected</c:if>
								>내용</option>
								<option value="mixedTitleContents"
									<c:if test="${ searchType eq 'mixedTitleContents' }">selected</c:if>
								>제목+내용</option>
								<option value="id"
									<c:if test="${ searchType eq 'id' }">selected</c:if>
								>작성자</option>
							</select> 
							<div class="col-xs-4">
								<input type="search" name="keyword" class="form-control" value="${ keyword }"/>
							</div>
							<div class="input-group-append">
								<button class="btn btn-outline-secondary" type="submit"
									id="button-addon2">
									<img
										src="${ pageContext.servletContext.contextPath }/static/images/magnifier2.png"
										alt="검색" width="20px" class="search-img" /> 검색
								</button>
							</div>
							<input type="hidden" name="boardType" value="information" />
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	
	<!-- footer -->
	<%@ include file="../../view/footer.jsp" %>
	
</body>
</html>