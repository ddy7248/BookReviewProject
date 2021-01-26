package com.book.command.board.review;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.command.Command;
import com.book.db.ReviewBoardDAO;

public class ReviewBoardDeleteCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		ReviewBoardDAO dao = ReviewBoardDAO.getReviewBoardDAO();
		dao.deleteReview(Integer.parseInt(request.getParameter("no")));
	}

}
