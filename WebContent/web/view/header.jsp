<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- 부트스트랩 CSS 추가 -->
<link rel="stylesheet" href="${ pageContext.servletContext.contextPath }/static/css/bootstrap/bootstrap.min.css" />
<link rel="stylesheet" href="${ pageContext.servletContext.contextPath }/static/css/common.css" />
<link rel="stylesheet" href="${ pageContext.servletContext.contextPath }/static/css/header.css" />

<header id="main-header">
	<div class="wrap-head">

		<!-- 로고 -->
		<span class="logo"> 
			<a href="index.do"> 
				<img src="${ pageContext.servletContext.contextPath }/static/images/logo.png" alt="로고" width="90px" />
			</a>
		</span>

		<!--메뉴바 -->
		<nav class="main-gnb">
			<ul>
				<li><a href="index.do">홈</a></li>
				<li class="dropdown">
					<a href="#" class="dropdown-btn">추천도서</a>
					<div class="dropdown-menu">
						<a href="book/genre.do?curPage=1&genreNum=100">베스트셀러</a>
						<a href="book/reco.do?curPage=1">추천도서</a>
						<div class="dropdown_genre">
							<a href="#" class="dropdown-btn">장르별</a>
							<div class="dropdown-menu_genre">
								<a href="book/genre.do?curPage=1&genreNum=101">소설</a>
								<a href="book/genre.do?curPage=1&genreNum=102">시/에세이</a>
								<a href="book/genre.do?curPage=1&genreNum=104">사회과학</a>
								<a href="book/genre.do?curPage=1&genreNum=116">자연과 과학</a>
								<a href="book/genre.do?curPage=1&genreNum=117">경제경영</a>
								<a href="book/genre.do?curPage=1&genreNum=118">자기계발</a>
								<a href="book/genre.do?curPage=1&genreNum=119">인문</a>
								<a href="book/genre.do?curPage=1&genreNum=126">건강/뷰티</a>
							</div>
						</div>
					</div>
				</li>
				<li><a href="board/review/listView.do">서평</a></li>
				<li class="dropdown">
					<a href="#" class="dropdown-btn">게시판</a>
					<div class="dropdown-menu">
						<a href="board/notice/listView.do">공지사항</a>
						<a href="board/question/listView.do">고객문의</a>
						<a href="board/information/listView.do">정보게시판</a>
					</div>
				</li>
			</ul>
		</nav>

		<!--side바-->
		<div class="side-bar">
			<form method="post" action="book/search.do?curPage=1">
				<div class="search-bar input-group">
					<select name="queryType" class="form-control"> 
						<option value="all" <c:if test="${ requestScope.queryType eq 'all' }">selected</c:if>>통합검색</option>
						<option value="title" <c:if test="${ requestScope.queryType eq 'title' }">selected</c:if>>책제목</option>
						<option value="author" <c:if test="${ requestScope.queryType eq 'author' }">selected</c:if>>저자</option>
						<option value="publisher" <c:if test="${ requestScope.queryType eq 'publisher' }">selected</c:if>>출판사</option>
					</select>&nbsp
					<div class="col-xs-4"> 
						<input type="search" name="query" class="form-control" <c:if test="${ requestScope.query != null }">value="${ requestScope.query }"</c:if> />
					</div>
					<div class="input-group-append">
						<button class="btn btn-outline-secondary" type="submit"
							id="button-addon2">
							<img
								src="${ pageContext.servletContext.contextPath }/static/images/magnifier2.png"
								alt="검색" class="search-img" /> 검색
						</button>
					</div>
				</div>
			</form>
		</div>

		<!--로그인 -->
		<div class="gnbloginInfo">
			<ul>
				<c:choose>
					<c:when test="${ empty sessionScope.id }">
						<li><a href="register.do">회원가입</a></li>
						<li><a href="login.do">로그인</a></li>
					</c:when>
					<c:otherwise>
						<c:if test="${ sessionScope.id ne 'admin' }">
							<li class="dropdown"><a href="#" class="dropdown-btn">마이페이지</a>
							<div class="dropdown-menu">
								<a href="member/modify.do">회원정보수정</a> 
								<a href="member/checkContents.do">내 게시글 확인</a> 
								<a href="member/deleteChk.do">회원탈퇴</a>
							</div></li>
							<li><a href="logout.do">로그아웃</a></li>
						</c:if>
						<c:if test="${ sessionScope.id eq 'admin' }">
							<li class="dropdown">
								<a href="#" class="dropdown-btn">관리</a>
								<div class="dropdown-menu">	
									<a href="member/manage.ad">회원관리</a>
									<a href="board/manage.ad" class="dropdown-btn">게시물관리</a>
									<a href="board/notice/listView.do">공지사항</a>
								</div>
							</li>
							<li><a href="logout.do">로그아웃</a></li>
						</c:if>
					</c:otherwise>
				</c:choose>
			</ul>
		</div>
	</div>
</header>