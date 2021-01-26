package com.book.command.board.general;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.command.Command;
import com.book.db.GeneralBoardDAO;
import com.book.db.GeneralBoardDTO;

public class GBModifyCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		GeneralBoardDAO dao = GeneralBoardDAO.getGeneralBoardDAO();
		GeneralBoardDTO dto = new GeneralBoardDTO();
		
		dto.setNo(Integer.parseInt(request.getParameter("no")));
		dto.setBoardType(request.getParameter("boardType"));
		dto.setTitle(request.getParameter("title"));
		dto.setContents(request.getParameter("contents"));
		
		dao.modifyOK(dto);
	}

}
