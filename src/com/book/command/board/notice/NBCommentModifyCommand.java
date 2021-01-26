package com.book.command.board.notice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.command.Command;
import com.book.db.NBReplyDAO;
import com.book.db.NBReplyDTO;

public class NBCommentModifyCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		NBReplyDAO dao = NBReplyDAO.getNBReplyDAO();
		NBReplyDTO dto = new NBReplyDTO();
		
		dto.setBno(Integer.parseInt(request.getParameter("bno")));
		dto.setRno(Integer.parseInt(request.getParameter("rno")));
		dto.setComment(request.getParameter("comment"));
		
		dao.modifyCom(dto);
		
		request.setAttribute("bno", dto.getBno());
	}

}
