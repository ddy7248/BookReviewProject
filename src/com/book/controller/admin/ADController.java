package com.book.controller.admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.command.Command;
import com.book.command.board.general.GBDeleteCommand;
import com.book.command.board.general.GBListCommand;
import com.book.command.board.notice.NBDeleteCommand;
import com.book.command.board.notice.NBListCommand;
import com.book.command.board.review.ReviewBoardDeleteCommand;
import com.book.command.board.review.ReviewBoardListCommand;
import com.book.command.member.MemberDeleteCommand;
import com.book.command.member.MemberListCommand;
import com.book.command.member.MemberViewCommand;

@WebServlet("*.ad")
public class ADController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ADController() {
        super();
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String commandName = request.getServletPath();
		String contextPath = request.getContextPath();
		String viewPage = null;
		Command command = null;
		boolean flag = false;
		
		System.out.println("command: "+ commandName);
		
		//공용루트
		if(commandName.contains("/member/manage.ad")) {
			viewPage = contextPath + "/web/admin/memberList.ad";
		}
		else if(commandName.contains("/board/manage.ad")) {
			viewPage = contextPath + "/web/admin/boardList.ad";
		}
		
		else if(commandName.contains("/member/delete.ad")) {
			command = new MemberDeleteCommand();
			command.execute(request, response);
			viewPage = "member/manage.ad";
		}
		else if(commandName.contains("/board/notice/write.ad")) {
			viewPage = contextPath + "/web/board/notice/noticeWrite.jsp";
		}
		
		//회원관리
		else if(commandName.equals("/web/admin/memberList.ad")) {
			command = new MemberListCommand();
			command.execute(request, response);
			viewPage = "memberManage.jsp";
			flag = true;
		}
		else if(commandName.equals("/web/admin/memberView.ad")) {
			request.setAttribute("id", request.getParameter("id"));
			command = new MemberViewCommand();
			command.execute(request, response);
			viewPage = "../member/modify.jsp";
			flag = true;
		}
			
		//게시판 목록 불러오기
		else if(commandName.equals("/web/admin/boardList.ad")) {
			String boardType = request.getParameter("boardType");
			if(boardType == null) boardType = "review";
			
			if(boardType.equals("review")) {
				command = new ReviewBoardListCommand();
				command.execute(request, response);
			}
			else if(boardType.equals("notice")) {
				command = new NBListCommand();
				command.execute(request, response);
			}
			else if(boardType.equals("question")) {
				request.setAttribute("boardType", boardType);
				command = new GBListCommand();
				command.execute(request, response);
			}
			else if(boardType.equals("information")) {
				request.setAttribute("boardType", boardType);
				command = new GBListCommand();
				command.execute(request, response);
			}
			
			request.setAttribute("boardType", boardType);
			
			viewPage = "boardManage.jsp";
			flag = true;
		}
		//게시물 삭제
		else if(commandName.equals("/web/admin/board/delete.ad")) {
			String boardType = request.getParameter("boardType");
			
			if(boardType.equals("review")) {
				command = new ReviewBoardDeleteCommand();
				command.execute(request, response);
			}
			else if(boardType.equals("notice")) {
				command = new NBDeleteCommand();
				command.execute(request, response);
			}
			else if(boardType.equals("question")) {
				request.setAttribute("boardType", boardType);
				command = new GBDeleteCommand();
				command.execute(request, response);
			}
			else if(boardType.equals("information")) {
				request.setAttribute("boardType", boardType);
				command = new GBDeleteCommand();
				command.execute(request, response);
			}
			
			viewPage = "board/manage.ad";
		}
		
		//viewPage 연결
		if(flag) {
			RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
			dispatcher.forward(request, response);
		}
		else {
			response.sendRedirect(viewPage);
		}
	}
	
}
