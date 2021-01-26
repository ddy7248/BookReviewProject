package com.book.command.board.notice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.command.Command;
import com.book.db.NoticeBoardDAO;

public class NBDeleteCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		NoticeBoardDAO dao = NoticeBoardDAO.getNoticeBoardDAO();
		dao.deleteOK(Integer.parseInt(request.getParameter("no")));
	}

}
