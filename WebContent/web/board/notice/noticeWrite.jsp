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
				<form action="writeOK.do" method="post">
					<table class="table text-center">
						<tr>
							<td>유형</td>
							<td>
								<div class="form-group col-md-2"> 
									<select name="type" class="form-control">
										<option value="notice">공지사항</option>
										<option value="event">이벤트</option>
									</select>
								</div>
							</td>
						</tr>
						<tr>
							<td><label for="title"><strong>제목</strong></label></td>
							<td>
								<div class="form-group col-md-6"> 
									<input type="text" class="form-control" name="title" id="title" required />
								</div>
							</td>
						</tr>
						
						<tr>
							<td colspan="2"><textarea class="form-control" name="contents" cols="80" rows="20" ></textarea></td>
						</tr>
					</table>
					<div class="d-flex justify-content-end">
						<button type="submit" class="btn btn-secondary ml-3">등록</button>
						<button type="button" onclick="go()" class="btn btn-secondary ml-3">목록</button>
						<input type="hidden" name="id" value=${ sessionScope.id } />
					</div>
				</form>
				
			</div>
		</div>
	</div>
	
	<!-- footer -->
	<%@ include file="../../view/footer.jsp" %>
</body>
</html>