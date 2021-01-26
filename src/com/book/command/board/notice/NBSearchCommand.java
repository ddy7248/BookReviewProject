package com.book.command.board.notice;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.command.Command;
import com.book.db.NoticeBoardDAO;
import com.book.db.NoticeBoardDTO;

public class NBSearchCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		NoticeBoardDAO dao = NoticeBoardDAO.getNoticeBoardDAO();
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
			query = " WHERE MATCH(type, title, contents) AGAINST('"+keyword+"') ";
		}
		else if(searchType.equals("type")) {
			if(keyword.equals("공지사항")) {
				keyword = "notice";
			}
			else if(keyword.equals("이벤트")) {
				keyword = "event";
			}
			query = " WHERE type LIKE '%"+keyword+"%'";
		}
		else if(searchType.equals("title")) {
			query = " WHERE title LIKE '%"+keyword+"%'";
		}
		else if(searchType.equals("contents")) {
			query = " WHERE contents LIKE '%"+keyword+"%'";
		}
		
		ArrayList<NoticeBoardDTO> list = dao.searchDAO(searchType, keyword, curPage);
		pageNum = dao.getPageNum(query);
		
		request.setAttribute("list", list);
		request.setAttribute("curPage", curPage);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("searchFlag", true);
		request.setAttribute("searchType", searchType);
		request.setAttribute("keyword", keyword);
	}

}
