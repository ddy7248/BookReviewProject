package com.book.command.board.notice;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.command.Command;
import com.book.db.NoticeBoardDAO;
import com.book.db.NoticeBoardDTO;

public class NBListCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		NoticeBoardDAO dao = NoticeBoardDAO.getNoticeBoardDAO();
		int curPage = 0;
		int pageNum = 0;
		String query = "";
		
		if(request.getParameter("curPage") != null) {
			curPage = Integer.parseInt(request.getParameter("curPage"));
		}
		else curPage = 1;
		
		query += " WHERE indentNum=0";
		
		ArrayList<NoticeBoardDTO> list = dao.listDAO(curPage);
		pageNum = dao.getPageNum(query);
		
		request.setAttribute("list", list);
		request.setAttribute("curPage", curPage);
		request.setAttribute("pageNum", pageNum);
	}

}
