<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="description" content="HTML Study">
<meta name="keywords" content="HTML,CSS,XML,JavaScript">
<meta name="author" content="Do">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>책담소</title>
<link rel="stylesheet" type="text/css"
	href="${ pageContext.servletContext.contextPath }/static/css/footer.css" />
<link rel="stylesheet" type="text/css"
	href="${ pageContext.servletContext.contextPath }/static/css/admin/manage.css" />
<link rel="stylesheet" type="text/css"
	href="${ pageContext.servletContext.contextPath }/static/css/board/list.css" />
</head>
<body>
	<!--header -->
	<%@ include file="../view/header.jsp"%>

	<div id="container">

		<!-- 탭 관리 -->
		<div class="tab-block" id="tab-block">

			<ul class="tab-mnu">
				<li <c:if test="${ boardType eq 'review' }">class = "active"</c:if>><a
					href="check.do?boardType=review">서평게시판</a></li>
				<li
					<c:if test="${ boardType eq 'question' }">class = "active"</c:if>><a
					href="check.do?boardType=question">고객문의</a></li>
				<li
					<c:if test="${ boardType eq 'information' }">class = "active"</c:if>><a
					href="check.do?boardType=information">정보게시판</a></li>
			</ul>

			<div class="tab-cont">

				<div class="tab-pane">

					<!-- 서평게시판 -->
					<c:if test="${ requestScope.boardType eq 'review' }">
						<div class="board_list_wrap">
							<table class="board_list table table-hover">
								<thead>
									<tr>
										<th>번호</th>
										<th>작성자</th>
										<th>책제목</th>
										<th>평점</th>
										<th>조회수</th>
										<th>등록일</th>
										<th>삭제</th>
									</tr>
								</thead>

								<tbody>
									<!-- 글 정보 출력 -->
									<c:if test="${ list != null }">
										<c:forEach var="dto" items="${ list }">
											<tr>
												<td>${ dto.no }</td>
												<td>${ dto.id }</td>
												<td><c:forEach begin="1" end="${dto.indentNum}"
														step="1">-</c:forEach> <a
													href="../board/review/view.do?no=${ dto.no }">${ dto.bookTitle }</a>
												</td>
												<td>${ dto.starPoint }</td>
												<td>${ dto.rCnt }</td>
												<td><fmt:formatDate type="date" value="${dto.wTime}"
														pattern="yyyy-MM-dd" /></td>
												<td><a href="../board/review/delete.do?no=${ dto.no }"><button
															type="button" class="btn btn-secondary">삭제</button></a></td>
											</tr>
										</c:forEach>
									</c:if>
								</tbody>
							</table>
						</div>

						<!-- 페이지 -->
						<div class="text-center">
							<c:if test="${ list != null }">
								<c:forEach var="i" begin="1" end="${ pageNum }" step="1">
									<c:choose>
										<c:when test="${i == requestScope.curPage }">
											<button type="button" class="btn btn-secondary">${ i }</button>
										</c:when>
										<c:otherwise>
											<a href="check.do?boardType=review&curPage=${ i }"><button
													type="button" class="btn btn-outline-secondary">${ i }</button></a>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</c:if>
						</div>
					</c:if>

					<!-- 고객문의 -->
					<c:if test="${ requestScope.boardType eq 'question' }">
						<div class="board_list_wrap">
							<table class="board_list table table-hover">
								<thead>
									<tr>
										<th>유형</th>
										<th>제목</th>
										<th>작성자</th>
										<th>조회수</th>
										<th>등록일</th>
										<th>삭제</th>
									</tr>
								</thead>

								<tbody>
									<!-- 글 정보 출력 -->
									<c:if test="${ list != null }">
										<c:forEach var="dto" items="${ list }">

											<tr>
												<td><c:if test="${ dto.boardType eq 'question' }">질문</c:if>
													<c:if test="${ dto.boardType ne 'question' }">${ dto.boardType }</c:if>
												</td>
												<td><a
													href="../board/question/view.do?no=${ dto.no }&boardType=${ dto.boardType }">${ dto.title }</a>
													<c:if test="${ dto.stepNum != 0 }">[${ dto.stepNum }]</c:if>
												</td>
												<td>${ dto.id }</td>
												<td>${ dto.rCnt }</td>
												<td><fmt:formatDate type="date" value="${dto.wTime}"
														pattern="yyyy-MM-dd" /></td>
												<td><a
													href="../board/information/delete.do?boardType=question&no=${ dto.no }"><button
															type="button" class="btn btn-secondary">삭제</button></a></td>
											</tr>
										</c:forEach>
									</c:if>
								</tbody>

							</table>
						</div>

						<!-- 전체 목록 페이징 -->
						<div class="text-center">
							<c:if test="${ list != null }">
								<c:forEach var="i" begin="1" end="${ requestScope.pageNum }"
									step="1">
									<c:choose>
										<c:when test="${i == requestScope.curPage }">
											<button type="button" class="btn btn-secondary">${ i }</button>
										</c:when>
										<c:otherwise>
											<a href="check.do?boardType=question&curPage=${ i }"><button
													type="button" class="btn btn-outline-secondary">${ i }</button></a>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</c:if>
						</div>
					</c:if>

					<!-- 정보게시판 -->
					<c:if test="${ requestScope.boardType eq 'information' }">
						<div class="board_list_wrap">
							<table class="board_list table table-hover">
								<thead>
									<tr>
										<th>유형</th>
										<th>제목</th>
										<th>작성자</th>
										<th>조회수</th>
										<th>등록일</th>
										<th>삭제</th>
									</tr>
								</thead>

								<tbody>
									<!-- 글 정보 출력 -->
									<c:if test="${ list != null }">
										<c:forEach var="dto" items="${ list }">
											<tr>
												<td><c:if test="${ dto.boardType eq 'information' }">정보</c:if>
													<c:if test="${ dto.boardType ne 'information' }">${ dto.boardType }</c:if>
												</td>
												<td><a
													href="../board/information/view.do?no=${ dto.no }&boardType=${ dto.boardType }">${ dto.title }</a>
													<c:if test="${ dto.stepNum != 0 }">[${ dto.stepNum }]</c:if>
												</td>
												<td>${ dto.id }</td>
												<td>${ dto.rCnt }</td>
												<td><fmt:formatDate type="date" value="${dto.wTime}"
														pattern="yyyy-MM-dd" /></td>
												<td><a
													href="../board/information/delete.do?boardType=information&no=${ dto.no }"><button
															type="button" class="btn btn-secondary">삭제</button></a></td>
											</tr>
										</c:forEach>
									</c:if>
								</tbody>
							</table>
						</div>

						<!-- 전체 목록 페이징 -->
						<div class="text-center">
							<c:if test="${ list != null }">
								<c:forEach var="i" begin="1" end="${ requestScope.pageNum }"
									step="1">
									<c:choose>
										<c:when test="${i == requestScope.curPage }">
											<button type="button" class="btn btn-secondary">${ i }</button>
										</c:when>
										<c:otherwise>
											<a href="check.do?boardType=information&curPage=${ i }"><button
													type="button" class="btn btn-outline-secondary">${ i }</button></a>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</c:if>
						</div>
					</c:if>

				</div>

			</div>

		</div>
	</div>


	<!-- footer -->

	<footer id="main_footer">

		<div class="footer_info">
			<img
				src="${ pageContext.servletContext.contextPath }/static/images/logo.png"
				class="footer_logo" alt="로고" width="200px" />
			<ul>
				<li>서울 강남구 강남대로 428 만이빌딩 5, 10층</li>
				<li><span>사업자 등록번호</span> XXX-XX-XXXXX</li>
			</ul>
			<ul>
				<li><span>이름</span>도대영</li>
				<li><span>전화번호</span>010-XXXX-XXXX</li>
				<li><a href="board/question/writeView.do">문의하기</a></li>
			</ul>
			<ul>
				<li><p>© 책담소. ALL RIGHTS RESERVED.</p></li>
			</ul>
		</div>

		<div class="footer_link">
			<a href="board/review/write.do">서평 쓰기</a>
		</div>

	</footer>
</body>
</html>