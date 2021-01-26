package com.book.command.board.general;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.command.Command;
import com.book.db.GeneralBoardDAO;
import com.book.db.GeneralBoardDTO;

public class GBWriteCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		GeneralBoardDAO dao = GeneralBoardDAO.getGeneralBoardDAO();
		GeneralBoardDTO dto = new GeneralBoardDTO();
		
		dto.setBoardType(request.getParameter("boardType"));
		dto.setId(request.getParameter("id"));
		dto.setTitle(request.getParameter("title"));
		dto.setContents(request.getParameter("contents"));
		
		dao.writeOK(dto);
	}

}
