package com.book.command.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.book.command.Command;
import com.book.db.MemberDAO;

public class MemberDeleteCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		MemberDAO dao = MemberDAO.getMemberDAO();
		
		if(((String)session.getAttribute("id")).equals("admin")) {
			dao.delete(request.getParameter("id"));
		}
		else {
			dao.delete((String)session.getAttribute("id"));
		}
	}

}
