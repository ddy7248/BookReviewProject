package com.book.command.board.review;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.book.command.Command;
import com.book.db.ReviewBoardDAO;
import com.book.db.ReviewBoardDTO;

public class ReviewBoardCommentCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		ReviewBoardDAO dao = ReviewBoardDAO.getReviewBoardDAO();
		ReviewBoardDTO dto = dao.getReviewBoardDTO(Integer.parseInt(request.getParameter("no")));
		
		dto.setId((String)session.getAttribute("id"));
		dto.setContents(request.getParameter("comment"));
		
		dao.commentOK(dto);
		
		request.setAttribute("groupNum", dto.getGroupNum());
	}

}
