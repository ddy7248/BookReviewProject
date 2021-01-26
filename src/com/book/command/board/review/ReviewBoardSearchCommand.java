package com.book.command.board.review;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.command.Command;
import com.book.db.ReviewBoardDAO;
import com.book.db.ReviewBoardDTO;

public class ReviewBoardSearchCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		ReviewBoardDAO dao = ReviewBoardDAO.getReviewBoardDAO();
		String searchType = request.getParameter("searchType");
		String keyword = request.getParameter("keyword");
		int curPage = 0;
		int pageNum = 0;
		String query = null;
		
		if(request.getParameter("curPage") != null) {
			curPage = Integer.parseInt(request.getParameter("curPage"));
		}
		else curPage = 1;
		
		if(searchType.equals("all")) {
			query = " WHERE indentNum=0 AND MATCH(id,bookTitle,author,publisher) AGAINST('"+keyword+"') ";
		}
		else if(searchType.equals("bookTitle")) {
			query = " WHERE indentNum=0 AND bookTitle LIKE '%"+keyword+"%'";
		}
		else if(searchType.equals("author")) {
			query = " WHERE indentNum=0 AND author LIKE '%"+keyword+"%'";
		}
		else if(searchType.equals("publisher")) {
			query = " WHERE indentNum=0 AND publisher LIKE '%"+keyword+"%'";
		}
		else if(searchType.equals("id")) {
			query = " WHERE indentNum=0 AND id LIKE '%"+keyword+"%'";
		}
		
		
		ArrayList<ReviewBoardDTO> list = dao.searchDAO(searchType, keyword, curPage);
		pageNum = dao.getPageNum(query);
		
		request.setAttribute("list", list);
		request.setAttribute("curPage", curPage);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("searchFlag", true);
		request.setAttribute("searchType", searchType);
		request.setAttribute("keyword", keyword);
	}

}
