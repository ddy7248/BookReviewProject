package com.book.command.board.general;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.command.Command;
import com.book.db.GeneralBoardDAO;
import com.book.db.GeneralBoardDTO;

public class GBViewCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		GeneralBoardDAO dao = GeneralBoardDAO.getGeneralBoardDAO();
		int no = 0;
		String boardType = null;
		
		no = Integer.parseInt(request.getParameter("no"));
		boardType = request.getParameter("boardType");
		
		dao.increaseRCnt(no, boardType);
		GeneralBoardDTO dto = dao.getGeneralBoardDTO(no, boardType); 
		
		ArrayList<GeneralBoardDTO> list = dao.comListDAO(no, boardType);
		request.setAttribute("dto", dto);
		request.setAttribute("comList", list);
	}

}
