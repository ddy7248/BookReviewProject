package com.book.command.board.review;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.command.Command;
import com.book.db.ReviewBoardDAO;
import com.book.db.ReviewBoardDTO;

public class ReviewBoardDeleteCommentCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		ReviewBoardDAO dao = ReviewBoardDAO.getReviewBoardDAO();
		ReviewBoardDTO dto = dao.getReviewBoardDTO(Integer.parseInt(request.getParameter("no")));
		
		dao.deleteCom(dto.getNo());
		dao.decreaseStepNum(dto);
		
		request.setAttribute("groupNum", dto.getGroupNum());
	}

}
