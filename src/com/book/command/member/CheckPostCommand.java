package com.book.command.member;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.book.command.Command;
import com.book.db.ReviewBoardDAO;
import com.book.db.ReviewBoardDTO;

public class CheckPostCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		ReviewBoardDAO dao = ReviewBoardDAO.getReviewBoardDAO();
		String query = "";
		int pageNum = 0, curPage = 0;
		
		if(request.getParameter("curPage") != null) {
			curPage = Integer.parseInt(request.getParameter("curPage"));
		}
		else curPage = 1;
		
		query = " WHERE id='"+(String)session.getAttribute("id")+"' AND indentNum=0";
		pageNum = dao.getPageNum(query);
		
		ArrayList<ReviewBoardDTO> list = dao.checkPost((String)session.getAttribute("id"),curPage);
		
		request.setAttribute("list", list);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("curPage", curPage);
	}

}
