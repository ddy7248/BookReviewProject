package com.book.command.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.command.Command;
import com.book.db.MemberDAO;
import com.book.db.MemberDTO;

public class MemberRegisterCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		MemberDAO dao = MemberDAO.getMemberDAO();
		MemberDTO dto = new MemberDTO();
		String phone = "";

		phone = request.getParameter("phoneType")+","+request.getParameter("phoneNum");
		
		dto.setId(request.getParameter("id"));
		dto.setPassword(request.getParameter("password"));
		dto.setName(request.getParameter("name"));
		dto.setGender(request.getParameter("gender"));
		dto.setBirth(request.getParameter("birth"));
		dto.setEmail(request.getParameter("email"));
		dto.setPhoneNum(phone);
		
		dao.registerMember(dto);
	}

}
