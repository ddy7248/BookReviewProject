package com.book.command.member;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.command.Command;
import com.book.db.MemberDTO;
import com.book.db.MemberDAO;

public class MemberListCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		MemberDAO dao = MemberDAO.getMemberDAO();
		ArrayList<MemberDTO> list = dao.listDAO();
		request.setAttribute("memberList", list);
	}

}
