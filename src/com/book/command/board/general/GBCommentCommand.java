package com.book.command.board.general;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.book.command.Command;
import com.book.db.GeneralBoardDAO;
import com.book.db.GeneralBoardDTO;

public class GBCommentCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		GeneralBoardDAO dao = GeneralBoardDAO.getGeneralBoardDAO();
		int no = 0;
		String boardType = null;
		
		no = Integer.parseInt(request.getParameter("no"));
		boardType = request.getParameter("boardType");
				
		GeneralBoardDTO dto = dao.getGeneralBoardDTO(no, boardType);
		
		dto.setId((String)session.getAttribute("id"));
		dto.setContents(request.getParameter("comment"));
		
		dao.commentOK(dto);
		
		request.setAttribute("groupNum", dto.getGroupNum());
		request.setAttribute("boardType", dto.getBoardType());
	}

}
