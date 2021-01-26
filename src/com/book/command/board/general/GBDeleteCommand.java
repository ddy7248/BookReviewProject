package com.book.command.board.general;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.command.Command;
import com.book.db.GeneralBoardDAO;

public class GBDeleteCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		GeneralBoardDAO dao = GeneralBoardDAO.getGeneralBoardDAO();
		int no = 0;
		String boardType = request.getParameter("boardType");
		
		no = Integer.parseInt(request.getParameter("no"));
		if( boardType == null) boardType = (String)request.getAttribute("boardType");
		
		dao.deleteOK(no, boardType);
		
	}

}
