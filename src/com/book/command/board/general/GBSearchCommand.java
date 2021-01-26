package com.book.command.board.general;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.command.Command;
import com.book.db.GeneralBoardDAO;
import com.book.db.GeneralBoardDTO;

public class GBSearchCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		GeneralBoardDAO dao = GeneralBoardDAO.getGeneralBoardDAO();
		String searchType = request.getParameter("searchType");
		String keyword = request.getParameter("keyword");
		String boardType = request.getParameter("boardType");
		int curPage = 0;
		int pageNum = 0;
		String query = null;
		
		if(request.getParameter("curPage") != null) {
			curPage = Integer.parseInt(request.getParameter("curPage"));
		}
		else curPage = 1;
		
		if(searchType.equals("all")) {
			query = " WHERE indentNum=0 AND boardType='"+boardType+"' AND MATCH(id,title,contents) AGAINST('"+keyword+"') ";
		}
		else if(searchType.equals("title")) {
			query = " WHERE indentNum=0 AND boardType='"+boardType+"' AND title LIKE '%"+keyword+"%'";
		}
		else if(searchType.equals("contents")) {
			query = " WHERE indentNum=0 AND boardType='"+boardType+"' AND contents LIKE '%"+keyword+"%'";
		}
		else if(searchType.equals("mixedTitleContents")) {
			query = " WHERE indentNum=0 AND boardType='"+boardType+"' AND (title LIKE '%"+keyword+"%' OR contents LIKE '%"+keyword+"%')";
		}
		else if(searchType.equals("id")) {
			query = " WHERE indentNum=0 AND boardType='"+boardType+"' AND id LIKE '%"+keyword+"%'";
		}
		
		
		ArrayList<GeneralBoardDTO> list = dao.searchDAO(boardType, searchType, keyword, curPage);
		pageNum = dao.getPageNum(query);
		
		request.setAttribute("list", list);
		request.setAttribute("curPage", curPage);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("searchFlag", true);
		request.setAttribute("searchType", searchType);
		request.setAttribute("keyword", keyword);
	}

}
