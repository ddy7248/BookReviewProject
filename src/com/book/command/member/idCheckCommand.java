package com.book.command.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.command.Command;
import com.book.db.MemberDAO;

public class idCheckCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		MemberDAO dao = MemberDAO.getMemberDAO();
		String id=request.getParameter("id");
		int cnt = 0;
		cnt = dao.checkID(id);
		
		if(cnt==0){
			request.setAttribute("flag", true);
		}else{
			request.setAttribute("flag", false);
		}
		request.setAttribute("id", id);
		
	}

}
