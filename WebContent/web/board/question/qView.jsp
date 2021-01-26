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
				<h3><b>고객문의</b></h3>
			</div>
			
			<div class="contents">
				<form action="modify.do" method="post">
					<table class="table text-center">
						<tr>
							<td><strong>제목</strong></td>
							<td class="text-left"><strong>${ dto.title }</strong></td>
						</tr>
						<tr>
							<td><strong>작성자</strong></td>
							<td class="text-left"><strong>${ dto.id }</strong></td>
						</tr>
						<tr>
							<td><strong>내용</strong></td>
							<td><textarea name="contents" class="form-control" cols="80" rows="20" <c:if test="${ dto.id ne sessionScope.id }">readonly</c:if>>${ dto.contents }</textarea></td>
						</tr>
					</table>
					
					<div class="d-flex justify-content-end">
						<c:if test="${ sessionScope.id eq dto.id || sessionScope.id eq 'admin'}">
							<button type="submit" class="btn btn-secondary ml-3">수정</button>
							<input type="hidden" name="no" value="${ dto.no }" />
							<a href="delete.do?no=${ dto.no }&boardType=${ dto.boardType }">
								<button type="button" class="btn btn-secondary ml-3">삭제</button>
							</a>
						</c:if>
						
						<input type="hidden" name="title" value=${ dto.title } />
						<input type="hidden" name="id" value="${ dto.id }" />
						<input type="hidden" name="boardType" value="${ dto.boardType }" />
						<a href="list.do"><button type="button" class="btn btn-secondary ml-3">목록</button></a>
					</div>
				</form>
		
				<div class="com_area">
					<!-- 댓글작성 -->
					<c:if test="${ sessionScope.id eq 'admin' || sessionScope.id eq dto.id}">
						<form action="comment.do?no=${ dto.no }&boardType=${ dto.boardType }" method="post">
							<label for="comment">답변</label>
							<textarea class="form-control" name="comment" id="comment" cols="80" rows="5"></textarea>
							<div class="d-flex justify-content-end py-3">
								<button type="submit" class="btn btn-secondary">등록</button>
							</div>
						</form>
					</c:if>
					
					<table class="table" >
						<c:forEach var="dto" items="${ comList }">
							<form action="modifyCom.do" method="post">
								<tr id="arr${ dto.no }">
									<td class="text-center">	
										<strong>${ dto.id }</strong>
									</td>
									<td><textarea class="form-control" name="comment" cols="80" rows="5">${ dto.contents }</textarea></td>
									<td class="text-center row">	
										<div class="align-self-center text-center col">			
											<c:if test="${ sessionScope.id eq dto.id || sessionScope.id eq 'admin' }">
												<button type="submit" class="btn btn-secondary">수정</button>
												<a href="deleteCom.do?no=${ dto.no }&boardType=${ dto.boardType }">
													<button type="button" class="btn btn-secondary">삭제</button>
												</a>
											</c:if>
											<c:if test="${ sessionScope.id eq 'admin' }">
												<button type="button" onclick="addRepl(${dto.no})" class="btn btn-secondary">답변등록</button>
											</c:if> 
										</div>
									</td>
								</tr>
								<input type="hidden" name="no" value=${ dto.no } />
								<input type="hidden" name="boardType" value="${ dto.boardType }" />
								<input type="hidden" name="id" value="${ dto.id }" readonly />
							</form>
						</c:forEach>
					</table>
				</div>
			</div>
		</div>
	</div>
	
	<!-- footer -->
	<%@ include file="../../view/footer.jsp" %>
	
	<script>
		function addRepl(idx) { 
			$('#arr'+idx).after("<tr><td colspan='3'><form action=comment.do?no=" + idx + "&boardType=question method='post'><label for='comment'>답변</label><textarea name='comment' class='form-control' id='comment' cols='80' rows='5'></textarea><div class='d-flex justify-content-end py-3'><button type='submit' class='btn btn-secondary'>등록</button></div></form></td></tr>");
		}
	</script>	
</body>
</html>