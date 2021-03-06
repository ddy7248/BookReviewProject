package com.book.command.board.general;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.command.Command;
import com.book.db.GeneralBoardDAO;
import com.book.db.GeneralBoardDTO;

public class GBCommentDeleteCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		GeneralBoardDAO dao = GeneralBoardDAO.getGeneralBoardDAO();
		int no = 0;
		String boardType = null;
		
		no = Integer.parseInt(request.getParameter("no"));
		boardType = request.getParameter("boardType");
		
		GeneralBoardDTO dto = dao.getGeneralBoardDTO(no, boardType);
		
		request.setAttribute("groupNum", dto.getGroupNum());
		request.setAttribute("boardType", boardType);
		
		dao.deleteCom(no, boardType);
		dao.decreaseStepNum(dto);
	}

}
