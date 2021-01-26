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

<!-- 부트스트랩 CSS 추가 -->
<link rel="stylesheet" href="${ pageContext.servletContext.contextPath }/static/css/bootstrap/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="${ pageContext.servletContext.contextPath }/static/css/index.css" />

</head>
<body>
	<c:if test="${ empty best }"><c:redirect url="index.do"/></c:if>

	<!--header -->
	<%@ include file="view/header.jsp"  %>
	
	<!-- container -->
	<div id="container">
		
		<!-- 베스트셀러 슬라이드 -->
		<div class="slide slide1">
			<h4 class="part_title">베스트 셀러</h4>
			<input id="first" type="radio" name="tab" checked="checked" /> 
			<input id="second" type="radio" name="tab" />

			<section class="buttons">
				<label for="first">
					<img src="${ pageContext.servletContext.contextPath }/static/images/button2.png" alt="이전" />
				</label>
				<label for="second">
					<img src="${ pageContext.servletContext.contextPath }/static/images/button.png" alt="다음" />
				</label>
			</section>
			
			<!-- 슬라이드 아이템 출력 -->
			<div class="slide-list slide-list1">
				<ul>
					<c:forEach var="dto" begin="0" end="4" items="${ best }" >
						<li>
							<div class="slide-item">
								<!-- 도서 커버 관련 -->
								<div class="book-cover">
									<div class="cover">
										<p class="bpos">
											<a href="board/review/write.do?isbn=${ dto.isbn }">
												<img src="${ dto.coverLargeUrl }" alt="${ dto.rank }" class="bim" />
											</a> 
											<span class="item-rank">${ dto.rank }</span>
										</p>
									</div>
									<div class="book-buttons">
										<div class="like">
											<input type="button" name="like-button" />
										</div>
										<span class="likecnt"> 
											<img src="${ pageContext.servletContext.contextPath }/static/images/star.png" alt="좋아요: " class="likeim" /> 
											<strong>${ dto.customerReviewRank }</strong>
											<em>/</em>
											<em>10</em>
										</span>
									</div>
								</div>
								<!-- 도서 정보 -->
								<div class="book-info">
									<strong>${ dto.title }</strong> ${ dto.author } 
								</div>
							</div>
						</li>
					</c:forEach>
				</ul>
			</div>
			
			<div class="slide-list slide-list2">
				<ul>
					<c:forEach var="dto" begin="5" end="9" items="${ best }" >
						<li>
							<div class="slide-item">
								<div class="book-cover">
									<div class="cover">
										<p class="bpos">
											<a
												href="board/review/write.do?isbn=${ dto.isbn }">
												<img
												src="${ dto.coverLargeUrl }"
												alt="${ dto.rank }" class="bim" />
											</a> <span class="item-rank">${ dto.rank }</span>
										</p>
									</div>
									<div class="book-buttons">
										<div class="like">
											<input type="button" name="like-button" />
										</div>
										<span class="likecnt"> <img src="${ pageContext.servletContext.contextPath }/static/images/star.png"
											alt="좋아요: " class="likeim" /> <strong>${ dto.customerReviewRank }</strong> <em>/</em>
											<em>10</em>
										</span>
									</div>
								</div>
								<div class="book-info">
									<strong>${ dto.title }</strong> ${ dto.author } 
								</div>
							</div>
						</li>
					</c:forEach>
				</ul>
			</div>
		</div>
		
		<div class="slide slide2">
			<h4 class="part_title">추천도서</h4>
			<input id="third" type="radio" name="tab2" checked="checked" /> <input
				id="fourth" type="radio" name="tab2" />

			<section class="buttons">
				<label for="third"> <img src="${ pageContext.servletContext.contextPath }/static/images/button2.png" alt="다음" />
				</label> <label for="fourth"> <img src="${ pageContext.servletContext.contextPath }/static/images/button.png" alt="이전" />
				</label>
			</section>

			
			<div class="slide-list slide-list1">
				<ul>
					<c:forEach var="dto" begin="0" end="4" items="${ reco }" varStatus="status">
						<li>
							<div class="slide-item">
								<div class="book-cover">
									<div class="cover">
										<p class="bpos">
											<a
												href="board/review/write.do?isbn=${ dto.isbn }">
												<img
												src="${ dto.coverLargeUrl }"
												alt="${ status.count }" class="bim" />
											</a> <span class="item-rank">${ status.count }</span>
										</p>
									</div>
									<div class="book-buttons">
										<div class="like">
											<input type="button" name="like-button" />
										</div>
										<span class="likecnt"> <img src="${ pageContext.servletContext.contextPath }/static/images/star.png"
											alt="좋아요: " class="likeim" /> <strong>${ dto.customerReviewRank }</strong> <em>/</em>
											<em>10</em>
										</span>
									</div>
								</div>
								<div class="book-info">
									<strong>${ dto.title }</strong> ${ dto.author } 
								</div>
							</div>
						</li>
					</c:forEach>
				</ul>
			</div>
			<div class="slide-list slide-list2">
				<ul>
					<c:forEach var="dto" begin="5" end="9" items="${ reco }" varStatus="status">
						<li>
							<div class="slide-item">
								<div class="book-cover">
									<div class="cover">
										<p class="bpos">
											<a
												href="board/review/write.do?isbn=${ dto.isbn }">
												<img
												src="${ dto.coverLargeUrl }"
												alt="${ status.count }" class="bim" />
											</a> <span class="item-rank">${ status.index+1 }</span>
										</p>
									</div>
									<div class="book-buttons">
										<div class="like">
											<input type="button" name="like-button" />
										</div>
										<span class="likecnt"> <img src="${ pageContext.servletContext.contextPath }/static/images/star.png"
											alt="좋아요: " class="likeim" /> <strong>${ dto.customerReviewRank }</strong> <em>/</em>
											<em>10</em>
										</span>
									</div>
								</div>
								<div class="book-info">
									<strong>${ dto.title }</strong> ${ dto.author } 
								</div>
							</div>
						</li>
					</c:forEach>
				</ul>
			</div>
			
		</div>
		
	</div>

	<!-- footer -->
	<%@ include file="view/footer.jsp" %>
</body>
</html>