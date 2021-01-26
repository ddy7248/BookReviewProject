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
<script>
	function go(){
		history.back();
	}
</script>
</head>
<body>
	<!--header -->
	<%@ include file="../../view/header.jsp" %>

	<div id="container">
		<div class="contents_wrap">
			<div class="title text-center mb-5">
				<h3><b>공지사항</b></h3>
			</div>
			
			<div class="contents">
				<form action="modify.do" method="post">
					<table class="table text-center">
						<tr>
							<td>유형</td>
							<td>
								<div class="form-group col-md-2"> 
									<select name="type" class="form-control">
										<option value="notice" 
											<c:if test="${ dto.type eq 'notice' }">selected</c:if> 
										>공지사항</option>
										<option value="event"
											<c:if test="${ dto.type eq 'event' }">selected</c:if>
										>이벤트</option>
									</select>
								</div>
							</td>
						</tr>
						<tr>
							<td><strong>제목</strong></td>
							<td class="text-left"><strong class="ml-3">${ dto.title }</strong></td>
						</tr>
						<tr>
							<td><strong>내용</strong></td>
							<td><textarea class="form-control" name="contents" cols="80" rows="20" <c:if test="${ sessionScope.id ne 'admin' }">readonly</c:if>>${ dto.contents }</textarea></td>
						</tr>
					</table>
					<input type="hidden" name="title" value="${ dto.title }"/>
					
					<div class="d-flex justify-content-end">
						<c:if test="${ sessionScope.id eq 'admin' }">
							<button type="submit" class="btn btn-secondary ml-3">수정</button>
							<input type="hidden" name="no" value="${ dto.no }" />
							<a href="delete.do?no=${ dto.no }">
								<button type="button" class="btn btn-secondary ml-3">삭제</button>
							</a>
						</c:if>
						<a href="list.do">
							<button type="button" class="btn btn-secondary ml-3">목록</button>
						</a>
					</div>
				</form>
			
				<div class="com_area">
					<!-- 댓글작성 -->
					<form action="comment.do?no=${ dto.no }" method="post">
						<label for="comment">댓글</label>
						<textarea class="form-control" name="comment" id="comment" cols="80" rows="5"></textarea>
						<div class="d-flex justify-content-end py-3">
							<button type="submit" class="btn btn-secondary">등록</button>
						</div>
					</form>
					<table class="table">
						<c:forEach var="dto" items="${ comList }">
							<form action="modifyCom.do" method="post">
								<tr>
									<td class="text-center">
										<strong>${ dto.id }</strong>
									</td>
									<td>
										<textarea class="form-control" name="comment" cols="80" rows="5">${ dto.comment }</textarea>
									</td>
									<td class="text-center row">	
										<div class="align-self-center text-center col">	
											<c:if test="${ sessionScope.id eq dto.id || sessionScope.id eq 'admin'}">
												<button type="submit" class="btn btn-secondary">수정</button>
												<a href="deleteCom.do?bno=${ dto.bno }&rno=${ dto.rno}">
													<button type="button" class="btn btn-secondary">삭제</button>
												</a>
											</c:if>
										</div>
									</td>
								</tr>
								<input type="hidden" name="id" value="${ dto.id }" readonly />
								<input type="hidden" name="bno" value="${ dto.bno }"/>
								<input type="hidden" name="rno" value="${ dto.rno }"/>
							</form>
						</c:forEach>
					</table>
				</div>
			</div>
		</div>
	</div>
	
	<!-- footer -->
	<%@ include file="../../view/footer.jsp" %>
	
</body>
</html>