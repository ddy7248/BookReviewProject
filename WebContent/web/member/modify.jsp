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
<link rel="stylesheet" type="text/css" href="${ pageContext.servletContext.contextPath }/static/css/member/register.css" />
<script>
	function pwChk(){
		let pw = document.forms.modifyForm.password;
		let pw2 = document.forms.modifyForm.password2;
		let password = ${dto.password};
		
		if(${sessionScope.id ne 'admin'}){
			if(pw.value == password){
				if(pw.value != pw2.value){
					alert("비밀번호가 일치하지않습니다.");
					pw.value="";
					pw2.value="";
					pw.focus();
					return false;
				}
			}
			else{
				alert("비밀번호를 확인해주십시오.");
				pw.value="";
				pw2.value="";
				pw.focus();
				return false;
			}
		}
		
	}
</script>
</head>
<body>

	<!--header -->
	<%@ include file="../view/header.jsp"  %>
	
	<!-- container -->
	<div id="container">
		<div class="contents_wrap">
			<div class="contents">
				<form name="modifyForm" action="${ pageContext.servletContext.contextPath }/web/member/modifyOK.do" method="post" onsubmit="return pwChk()">
					<table class="table text-center">
						<thead>
							<tr>
								<th colspan="2"><a href="index.do"><img src="${ pageContext.servletContext.contextPath }/static/images/logo.png" alt="로고" width="200px"/></a></th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td><label for="id"><strong>아이디</strong></label></td>
								<td class="text-left"><strong>${ dto.id }</strong></td>
							</tr>
							<tr>
								<td><label for="pw"><strong>비밀번호</strong></label></td>
								<td>
									<div class="form-group col-md-8 px-0">
										<input type="password" class="form-control" name="password" id="pw" required />
									</div>
								</td>
							</tr>
							<tr>
								<td><label for="pw2"><strong>비밀번호 확인</strong></label></td>
								<td>
									<div class="form-group col-md-8 px-0">
										<input type="password" class="form-control" name="password2" id="pw2" required />
									</div>
								</td>
							</tr>
							<tr>
								<td><label for="name"><strong>이름</strong></label></td>
								<td>
									<div class="form-group col-md-8 px-0">
										<input type="text" class="form-control" name="name" id="name" value="${ dto.name }" required />
									</div>
								</td>
							</tr>
							<tr>
								<td><strong>성별</strong></td>
								<td class="text-left"> 
									<input type="radio" class="px-2" name="gender" id="man" value="man"	<c:if test="${ dto.gender eq 'man' }">checked</c:if> /><label for="man" class="px-2">남</label>
									<input type="radio" class="px-2" name="gender" id="woman" value="woman" <c:if test="${ dto.gender eq 'woman' }">checked</c:if> /><label for="woman" class="px-2">여</label>
								</td>
							</tr>
							<tr>
								<td><label for="birth"><strong>생년월일</strong></label></td>
								<td>
									<div class="form-group col-md-8 px-0">
										<input type="date" class="form-control" name="birth" id="birth" value="${ dto.birth }"/>
									</div>
								</td>
							</tr>
							<tr>
								<td><label for="email"><strong>본인확인 이메일</strong></label></td>
								<td>
									<div class="form-group col-md-8 px-0">
										<input type="email" class="form-control" name="email" id="email" value="${ dto.email }" required />
									</div>
								</td>
							</tr>
							<tr>
								<td><label for="phone"><strong>휴대전화</strong></label></td>
								<td>
									<div class="form-row">
										<div class="form-group col-md-2">
											<select name="phoneType" id="phone" class="form-control">
													<option value="SKT" <c:if test="${ phone[0] eq 'SKT' }">selected</c:if>>SKT</option>
													<option value="KT" <c:if test="${ phone[0] eq 'KT' }">selected</c:if>>KT</option>
													<option value="LG" <c:if test="${ phone[0] eq 'LG' }">selected</c:if>>LG</option>
											</select>
										</div>
										<div class="form-group col-md-6">
											<input type="text" class="form-control" name="phoneNum" placeholder="-없이 입력해주세요." value="${ phone[1] }" required />
										</div>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
					<div class="d-flex justify-content-center">
						<input type="submit" class="btn btn-secondary ml-3" value="회원정보수정" />
						<input type="hidden" name="id" id="id" value="${ dto.id }" readonly />
						<a href="index.do"><button type="button" class="btn btn-secondary ml-3">취소</button></a>
					</div>
				</form>
			</div>
		</div>
	</div>
	
	<!-- footer -->
	<%@ include file="../view/footer.jsp" %>
</body>
</html>