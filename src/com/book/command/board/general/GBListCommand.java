package com.book.command.board.general;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.book.command.Command;
import com.book.db.GeneralBoardDAO;
import com.book.db.GeneralBoardDTO;

public class GBListCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		GeneralBoardDAO dao = GeneralBoardDAO.getGeneralBoardDAO();
		int curPage = 0;
		int pageNum = 0;
		String query = "";
		String boardType = (String)request.getAttribute("boardType");
		String id = null;
		
		if(request.getAttribute("checkFlag") != null) {
			HttpSession session = request.getSession();
			id = (String)session.getAttribute("id");
			query += " WHERE id='"+id+"' AND boardType='" + boardType + "' AND indentNum=0 ";
		}else {
			query += " WHERE boardType='" + boardType + "' AND indentNum=0 ";
		}
		
		if(request.getParameter("curPage") != null) {
			curPage = Integer.parseInt(request.getParameter("curPage"));
		}
		else curPage = 1;
		
		
		ArrayList<GeneralBoardDTO> list = dao.listDAO(boardType,curPage, id);
		pageNum = dao.getPageNum(query);
		
		request.setAttribute("list", list);
		request.setAttribute("curPage", curPage);
		request.setAttribute("pageNum", pageNum);
	}

}
