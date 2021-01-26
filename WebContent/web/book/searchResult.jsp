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
<link rel="stylesheet" type="text/css" href="${ pageContext.servletContext.contextPath }/static/css/book/bookList.css" />
</head>
<body>
	<!--header -->
	<%@ include file="../view/header.jsp"%>

	<div id="container">

		<div class="contents_wrap">
			<div class="contents">
				<div class="board_list_wrap">
					<table class="board_list table table-hover">
						<tbody>
							<c:forEach var="dto" items="${ bSearchList }">
								<tr class="row">
									<td class="col text-center">
										<img src="${ dto.coverLargeUrl }" alt="${ dto.title }" />
									</td>
									<td class="col-6 d-flex align-items-start flex-column bd-highlight mb-3">
										<div class="p-2 bd-highlight">${ dto.title }</div>
										<div class="p-2 bd-highlight">${ dto.author }</div>
										<div class="p-2 bd-highlight">${ dto.publisher }</div>
										<div class="des p-2 bd-highlight">${ dto.description }</div>
									</td>
									<td class="col text-center row">
										<div class="align-self-center text-center col">
											<a href="board/review/write.do?isbn=${ dto.isbn }">
												<button type="button" class="btn btn-secondary">서평쓰기</button>
											</a>
										</div>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>

				<!-- 페이지 -->
				<div class="text-center mb-3">
					<c:forEach var="i" begin="1" end="${ pageNum }" step="1">
						<c:choose>
							<c:when test="${ i==curPage }">
								<button type="button" class="btn btn-secondary">${ i }</button>
							</c:when>
							<c:otherwise>
								<a
									href="book/search.do?query=${ query }&queryType=${ queryType }&curPage=${ i }"><button
										type="button" class="btn btn-outline-secondary">${ i }</button></a>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</div>
			</div>
		</div>
	</div>

	<!-- footer -->
	<%@ include file="../view/footer.jsp"%>
</body>
</html>