package com.book.command.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.book.command.Command;
import com.book.db.MemberDAO;

public class LoginCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		MemberDAO dao = MemberDAO.getMemberDAO();
		String id = request.getParameter("id");
		String pw = request.getParameter("password");
		boolean flag = false;
		
		flag = dao.loginOK(id, pw);
		
		if(flag) {
			session.setAttribute("id", id);
			session.setAttribute("password", pw);
			request.setAttribute("flag", true);
		}
		else {
			session.setAttribute("msg", "아이디 혹은 비밀번호가 다릅니다.");
			session.setAttribute("viewPage", "login.do");
			request.setAttribute("flag", false);
		}
	}

}
