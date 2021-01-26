package com.book.command.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.command.Command;
import com.book.db.MemberDAO;
import com.book.db.MemberDTO;

public class MemberViewCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		MemberDAO dao = MemberDAO.getMemberDAO();
		MemberDTO dto = dao.getMemberDTO((String)request.getAttribute("id"));
		String[] phone = new String[2];
		
		phone = dto.getPhoneNum().split(",");
		
		
		request.setAttribute("dto", dto);
		request.setAttribute("phone", phone);
	}

}
