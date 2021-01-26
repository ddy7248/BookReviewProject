package com.book.command.board.review;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.command.Command;
import com.book.db.ReviewBoardDAO;
import com.book.db.ReviewBoardDTO;

public class ReviewBoardModifyCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		ReviewBoardDAO dao = ReviewBoardDAO.getReviewBoardDAO();
		ReviewBoardDTO dto = new ReviewBoardDTO();
		
		dto.setBookTitle(request.getParameter("bookTitle"));
		dto.setAuthor(request.getParameter("author"));
		dto.setPublisher(request.getParameter("publisher"));
		dto.setStarPoint(Integer.parseInt(request.getParameter("starPoint")));
		dto.setContents(request.getParameter("contents"));
		dto.setNo(Integer.parseInt(request.getParameter("no")));
		
		dao.modifyReview(dto);
	}

}
