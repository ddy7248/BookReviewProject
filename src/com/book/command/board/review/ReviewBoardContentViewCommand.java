package com.book.command.board.review;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.command.Command;
import com.book.db.ReviewBoardDAO;
import com.book.db.ReviewBoardDTO;

public class ReviewBoardContentViewCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		ReviewBoardDAO dao = ReviewBoardDAO.getReviewBoardDAO();
		dao.increaseRCnt(Integer.parseInt(request.getParameter("no")));
		ReviewBoardDTO dto = dao.getReviewBoardDTO(Integer.parseInt(request.getParameter("no")));
		
		ArrayList<ReviewBoardDTO> list = dao.comListDAO(Integer.parseInt(request.getParameter("no")));
		
		request.setAttribute("dto", dto);
		request.setAttribute("comList", list);
	}

}
