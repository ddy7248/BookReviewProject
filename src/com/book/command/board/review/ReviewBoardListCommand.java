package com.book.command.board.review;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.command.Command;
import com.book.db.ReviewBoardDAO;
import com.book.db.ReviewBoardDTO;

public class ReviewBoardListCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		int curPage = 0;
		int pageNum = 0;
		String query = "";
		
		if(request.getParameter("curPage") != null) {
			curPage = Integer.parseInt(request.getParameter("curPage"));
		}
		else curPage = 1;
		
		query += " WHERE indentNum=0";
		
		ReviewBoardDAO dao = ReviewBoardDAO.getReviewBoardDAO();
		ArrayList<ReviewBoardDTO> list = dao.listDAO(curPage);
		pageNum = dao.getPageNum(query);
		
		request.setAttribute("list", list);
		request.setAttribute("curPage", curPage);
		request.setAttribute("pageNum", pageNum);
		
	}

}
