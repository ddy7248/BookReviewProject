package com.book.command.board.notice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.command.Command;
import com.book.db.NoticeBoardDAO;
import com.book.db.NoticeBoardDTO;

public class NBModifyCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		NoticeBoardDAO dao = NoticeBoardDAO.getNoticeBoardDAO();
		NoticeBoardDTO dto = new NoticeBoardDTO();
		
		dto.setNo(Integer.parseInt(request.getParameter("no")));
		dto.setType(request.getParameter("type"));
		dto.setTitle(request.getParameter("title"));
		dto.setContents(request.getParameter("contents"));
		
		
		dao.modifyOK(dto);
	}

}
