package com.book.command.board.review;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.book.command.Command;
import com.book.db.ReviewBoardDAO;
import com.book.db.ReviewBoardDTO;

public class ReviewBoardWriteCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		ReviewBoardDAO dao = ReviewBoardDAO.getReviewBoardDAO();
		ReviewBoardDTO dto = new ReviewBoardDTO();
		
		dto.setId((String)session.getAttribute("id"));
		dto.setBookTitle(request.getParameter("bookTitle"));
		dto.setAuthor(request.getParameter("author"));
		dto.setPublisher(request.getParameter("publisher"));
		dto.setStarPoint(Integer.parseInt(request.getParameter("starPoint")));
		dto.setContents(request.getParameter("contents"));
		dto.setImgURL(request.getParameter("imgURL"));
		
		dao.writeReview(dto);
		
	}

}
