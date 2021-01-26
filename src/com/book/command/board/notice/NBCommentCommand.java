package com.book.command.board.notice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.book.command.Command;
import com.book.db.NBReplyDAO;
import com.book.db.NBReplyDTO;

public class NBCommentCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		NBReplyDAO dao = NBReplyDAO.getNBReplyDAO();
		NBReplyDTO dto = new NBReplyDTO();
		int rno = 0;
		
		rno = dao.getRno(Integer.parseInt(request.getParameter("no")));
		
		dto.setBno(Integer.parseInt(request.getParameter("no")));
		dto.setRno(rno);
		dto.setId((String)session.getAttribute("id"));
		dto.setComment(request.getParameter("comment"));
		
		dao.commentOK(dto);
		
		request.setAttribute("bno", dto.getBno());
	}

}
