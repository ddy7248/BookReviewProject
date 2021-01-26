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
<link rel="stylesheet" type="text/css" href="${ pageContext.servletContext.contextPath }/static/css/member/register.css" />
<script>
	function pwChk(){
		let pw = document.forms.registerForm.password;
		let pw2 = document.forms.registerForm.password2;
		
		if(pw.value != pw2.value){
			alert("비밀번호가 일치하지않습니다.");
			pw.value="";
			pw2.value="";
			pw.focus();
			return false;
		}
	}
	
	function idCheck()
	{
		window.open("idCheckForm.jsp", "idCheckForm", "width=400, height=300");
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
				<form name="registerForm" action="registerOK.do" method="post" onsubmit="return pwChk()">
					<table class="table text-center">
						<thead>
							<tr>
								<th colspan="2"><a href="index.do"><img src="${ pageContext.servletContext.contextPath }/static/images/logo.png" alt="로고" width="200px"/></a></th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td><label for="id"><strong>아이디</strong></label></td>
								<td>
									<div class="form-row">
										<div class="form-group col-md-8">
											<input type="text" class="form-control" name="id" id="id" readonly />
										</div>
										<div class="form-group col-md-2">
											<button type="button" onClick="idCheck()" class="btn btn-secondary ml-3">중복확인</button>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<td><label for="pw"><strong>비밀번호</strong></label></td>
								<td>
									<div class="form-group col-md-8 px-0">
										<input type="password" class="form-control"  name="password" id="pw" required />
									</div>
								</td>
							</tr>
							<tr>
								<td><label for="pw2"><strong>비밀번호 확인</strong></label></td>
								<td>
									<div class="form-group col-md-8 px-0">
										<input type="password" class="form-control"  name="password2" id="pw2" required />
									</div>
								</td>
							</tr>
							<tr>
								<td><label for="name"><strong>이름</strong></label></td>
								<td>
									<div class="form-group col-md-8 px-0">
										<input type="text" class="form-control"  name="name" id="name" required />
									</div>
								</td>
							</tr>
							<tr>
								<td><strong>성별</strong></td>
								<td class="text-left">
									<input type="radio" class="px-2" name="gender" id="man" value="man" /><label for="man" class="px-2"><strong>남</strong></label>
									<input type="radio" class="px-2" name="gender" id="woman" value="woman" /><label for="woman" class="px-2"><strong>여</strong></label>
								</td>
							</tr>
							<tr>
								<td><label for="birth"><strong>생년월일</strong></label></td>
								<td>
									<div class="form-group col-md-8 px-0">
										<input type="date" class="form-control"  name="birth" id="birth" />
									</div>
								</td>
							</tr>
							
							<tr>
								<td><label for="email"><strong>본인확인 이메일</strong></label></td>
								<td>
									<div class="form-group col-md-8 px-0">
										<input type="email" class="form-control"  name="email" id="email" />
									</div>	
								</td>
							</tr>
							
							<tr>
								<td><label for="phone"><strong>휴대전화</strong></label></td>
								<td>
									<div class="form-row">
										<div class="form-group col-md-2">
											<select name="phoneType" id="phone" class="form-control" >
													<option value="SKT">SKT</option>
													<option value="KT">KT</option>
													<option value="LG">LG</option>
											</select>
											
										</div>
										<div class="form-group col-md-6">
											<input type="text" class="form-control"  name="phoneNum" placeholder="-없이 입력해주세요." />
										</div>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
					<div class="d-flex justify-content-center">
						<input type="submit" class="btn btn-secondary ml-3" value="회원가입" />
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