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

<link rel="stylesheet" type="text/css"
	href="${ pageContext.servletContext.contextPath }/static/css/board/view.css" />

</head>
<body>

	<!--header -->
	<%@ include file="../../view/header.jsp"%>

	<div id="container">

		<div class="contents_wrap">

			<div class="contents">
				<form action="modify.do" method="post">
					<table class="table text-center">
						<tr class="row">
							<td class="col text-center"><img src="${ dto.imgURL }" alt="책그림" /></td>
							<td	class="col-7 d-flex align-items-start flex-column bd-highlight mb-3">
								<div class="p-2 bd-highlight">
									<h4><strong>${ dto.bookTitle }</strong></h4>
								</div>
								<div class="p-2 bd-highlight">${ dto.author }</div>
								<div class="p-2 bd-highlight">${ dto.publisher }</div>
								<div class="p-2 bd-highlight"><strong>작성자</strong>&nbsp ${ dto.id }</div>
								<div class="p-2 bd-highlight">
									<c:forEach var="i" begin="1" end="5" step="1">
										<input type="radio" name="starPoint" id="star-${i}" value="${ i }" <c:if test="${ i == dto.starPoint }">checked</c:if> />
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
						<tr >
							<td>
								<textarea name="contents" class="form-control" cols="80" rows="20" placeholder="이 책은 여러분에게 어떤 생각의 변화를 주었나요?"
									<c:if test="${ dto.id ne sessionScope.id && sessionScope.id ne 'admin' }">readonly</c:if>>${ dto.contents }
								</textarea>
							</td>
						</tr>
						
					</table>
					<input type="hidden" name="bookTitle" value="${ dto.bookTitle }" />
					<input type="hidden" name="author" value="${ dto.author }" />
					<input type="hidden" name="publisher" value="${ dto.publisher }" />
					<input type="hidden" name="id" value="${ dto.id }" />
					
					
					<div class="d-flex justify-content-end mb-3">
						<c:choose>
							<c:when test="${ sessionScope.id eq 'admin'}">
								<a href="board/manage.ad">
									<button type="button" class="btn btn-secondary ml-3">목록</button>
								</a>
								<button type="submit" class="btn btn-secondary ml-3">수정</button>
								<a href="${ pageContext.servletContext.contextPath }/web/admin/board/delete.ad?boardType=review&no=${ dto.no }">
									<button type="button" class="btn btn-secondary ml-3">삭제</button>
								</a>
							</c:when>
							<c:when test="${ sessionScope.id eq dto.id }">
								<button type="submit" class="btn btn-secondary ml-3">수정</button>
								<a href="delete.do?no=${ dto.no }"><button type="button" class="btn btn-secondary ml-3">삭제</button></a>
							</c:when>
						</c:choose>
						<c:if test="${ sessionScope.id ne 'admin' }">
							<a href="list.do">
								<button type="button" class="btn btn-secondary ml-3">목록</button>
							</a>
						</c:if>
					</div>
					<input type="hidden" name="no" value="${ dto.no }" />
				</form>

			
			
				<div class="com_area">
					<!-- 댓글작성 -->
					<form action="comment.do?no=${ dto.no }" method="post">
						<label for="comment">댓글</label>
						<textarea name="comment" class="form-control" id="comment" cols="80" rows="5"></textarea>
						<div class="d-flex justify-content-end py-3">
							<button type="submit" class="btn btn-secondary">등록</button>
						</div>
					</form>
	
					<table class="table">
						<c:forEach var="dto" items="${ comList }">
							<form action="modifyCom.do" method="post">
								<tr id="arr${ dto.no }">
									<td class="text-center">
										<strong class="row">
											<div class="align-self-center text-center col">
												${ dto.id }
											</div>
										</strong>
									</td>
									<td>
										<textarea name="comment" class="form-control" cols="80" rows="5">${ dto.contents }</textarea>
									</td>
									<td class="text-center row">
										<div class="align-self-center text-center col">
											<c:if test="${ sessionScope.id eq dto.id || sessionScope.id eq 'admin' }">
												<button type="submit" class="btn btn-secondary">수정</button>
												<a href="deleteCom.do?no=${ dto.no }">
													<button type="button" class="btn btn-secondary">삭제</button>
												</a>
											</c:if>
											<c:if test="${ sessionScope.id ne dto.id }">
												<button type="button" onclick="addRepl(${dto.no})" class="btn btn-secondary">댓글달기</button>
											</c:if>
										</div>
									</td>
								</tr>
								<input type="hidden" name="no" value="${ dto.no }" />
								<input type="hidden" name="id" value="${ dto.id }" readonly />
							</form>
	
						</c:forEach>
					</table>
				</div>
			</div>
		</div>
	</div>

	<!-- footer -->
	<%@ include file="../../view/footer.jsp"%>

	<script>
		
		function addRepl(idx) { 
			  $('#arr'+idx).after("<tr><td colspan='3'><form action=comment.do?no=" + idx + " method='post'><label for='comment'>댓글</label><textarea name='comment' class='form-control' id='comment' cols='100' rows='5'></textarea><div class='d-flex justify-content-end py-3'><button type='submit' class='btn btn-secondary'>등록</button></div></form></td></tr>"); 
		}
	</script>


</body>
</html>