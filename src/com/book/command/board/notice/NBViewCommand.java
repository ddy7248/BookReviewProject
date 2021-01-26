package com.book.command.board.notice;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.command.Command;
import com.book.db.NBReplyDAO;
import com.book.db.NBReplyDTO;
import com.book.db.NoticeBoardDAO;
import com.book.db.NoticeBoardDTO;

public class NBViewCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		NoticeBoardDAO dao = NoticeBoardDAO.getNoticeBoardDAO();
		dao.increaseRCnt(Integer.parseInt(request.getParameter("no")));
		NoticeBoardDTO dto = dao.getNoticeBoardDTO(Integer.parseInt(request.getParameter("no")));
		
		NBReplyDAO rdao = NBReplyDAO.getNBReplyDAO();
		
		ArrayList<NBReplyDTO> list = rdao.comListDAO(Integer.parseInt(request.getParameter("no")));
		request.setAttribute("dto", dto);
		request.setAttribute("comList", list);
	}

}
