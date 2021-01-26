package com.book.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.book.command.Command;
import com.book.command.board.general.GBCommentCommand;
import com.book.command.board.general.GBCommentDeleteCommand;
import com.book.command.board.general.GBDeleteCommand;
import com.book.command.board.general.GBListCommand;
import com.book.command.board.general.GBModifyCommand;
import com.book.command.board.general.GBModifyCommentCommand;
import com.book.command.board.general.GBSearchCommand;
import com.book.command.board.general.GBViewCommand;
import com.book.command.board.general.GBWriteCommand;
import com.book.command.board.notice.NBCommentCommand;
import com.book.command.board.notice.NBCommentDeleteCommand;
import com.book.command.board.notice.NBCommentModifyCommand;
import com.book.command.board.notice.NBDeleteCommand;
import com.book.command.board.notice.NBListCommand;
import com.book.command.board.notice.NBModifyCommand;
import com.book.command.board.notice.NBSearchCommand;
import com.book.command.board.notice.NBViewCommand;
import com.book.command.board.notice.NBWriteCommand;
import com.book.command.board.review.ReviewBoardCommentCommand;
import com.book.command.board.review.ReviewBoardContentViewCommand;
import com.book.command.board.review.ReviewBoardDeleteCommand;
import com.book.command.board.review.ReviewBoardDeleteCommentCommand;
import com.book.command.board.review.ReviewBoardListCommand;
import com.book.command.board.review.ReviewBoardModifyCommand;
import com.book.command.board.review.ReviewBoardModifyCommentCommand;
import com.book.command.board.review.ReviewBoardSearchCommand;
import com.book.command.board.review.ReviewBoardWriteCommand;
import com.book.command.book.BestCommand;
import com.book.command.book.BookSearchCommand;
import com.book.command.book.RecoBookCommand;
import com.book.command.member.CheckPostCommand;
import com.book.command.member.LoginCommand;
import com.book.command.member.MemberDeleteCommand;
import com.book.command.member.MemberModifyCommand;
import com.book.command.member.MemberRegisterCommand;
import com.book.command.member.MemberViewCommand;
import com.book.command.member.idCheckCommand;

@WebServlet("*.do")
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public FrontController() {
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
	public void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		String commandName = request.getServletPath();
		String contextPath = request.getContextPath();
		String viewPage = null;
		Command command = null;
		boolean flag = false;
		
		System.out.println("command: "+commandName);
		
		//공용 루트
		if(commandName.contains("/index.do")) {
			viewPage = contextPath + "/web/indexView.do";
		}
		else if(commandName.equals("/web/indexView.do")) {
			session.setAttribute("categoryId", 100);
			command = new BestCommand();
			command.execute(request, response);
			command = new RecoBookCommand();
			command.execute(request, response);
			
			session.removeAttribute("categoryId");

			viewPage = "index.jsp";
			flag = true;
		}
		else if(commandName.contains("/login.do")) {
			viewPage = contextPath + "/web/member/login.jsp";
		}
		else if(commandName.contains("/logout.do")) {
			
			session.invalidate();
			//로그아웃창 표시
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('로그아웃이 완료되었습니다.');");
			out.println("location.href='index.do';");
			out.println("</script>");
			out.flush();
			out.close();
			return;
		}		
		else if(commandName.contains("/register.do")) {
			viewPage = contextPath + "/web/member/register.jsp";
		}
		else if(commandName.contains("/member/modify.do")) {
			viewPage = contextPath+"/web/member/modifyView.do";
			
		}
		else if(commandName.contains("/member/checkContents.do")) {
			viewPage = contextPath + "/web/member/check.do";
		}
		else if(commandName.contains("/member/deleteChk.do")) {
			
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("let flag = confirm(\"정말로 탈퇴하시겠습니까?\");");
			out.println("if(flag == true){");
			out.println("location.href='member/delete.do';");
			out.println("}");
			out.println("else if(flag == false){");
			out.println("location.href='index.do';");
			out.println("}");
			out.println("</script>");
			out.flush();
			out.close();
			return;
			
		}
		else if(commandName.contains("/member/delete.do")) {
			
			command = new MemberDeleteCommand();
			command.execute(request, response);
			session.invalidate();
			
			//회원탈퇴 완료 알림창
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('회원탈퇴가 완료되었습니다.');");
			out.println("location.href='index.do';");
			out.println("</script>");
			out.flush();
			out.close();
			return;
		} 
		else if(commandName.contains("/board/review/listView.do")) {
			viewPage = contextPath +"/web/board/review/list.do";
		}
		else if(commandName.contains("/board/review/write.do")) {
			
			if(session.getAttribute("id") == null) {
				session.setAttribute("msg", "로그인 후 작성할 수 있습니다.");
				session.setAttribute("viewPage", "login.do");
				viewPage = contextPath + "/web/noticePage.jsp";
			}
			else {
				if( request.getParameter("isbn") == null) {
					session.setAttribute("msg", "서평을 작성할 책을 선택해주세요.");
					session.setAttribute("viewPage", contextPath+"/web/book/searchWindow.jsp");
					viewPage = contextPath + "/web/noticePage.jsp";
				}
				else {
					session.setAttribute("queryType", "isbn");
					session.setAttribute("query", request.getParameter("isbn"));
					
					command = new BookSearchCommand();
					command.execute(request, response);
					
					session.setAttribute("dto", request.getAttribute("dto"));
					session.removeAttribute("queryType");
					session.removeAttribute("query");
					
					viewPage = contextPath+"/web/board/review/write.jsp";
				}
			}
			
		}
		else if(commandName.contains("/board/notice/listView.do")) {
			viewPage = contextPath +"/web/board/notice/list.do";
		}
		else if(commandName.contains("/board/information/listView.do")) {
			viewPage = contextPath +"/web/board/information/list.do";
		}
		else if(commandName.contains("/board/question/listView.do")) {
			viewPage = contextPath +"/web/board/question/list.do";
		}
		else if(commandName.contains("/board/question/writeView.do")) {
			if(session.getAttribute("id") == null) {
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('로그인 후 이용해주세요.');");
				out.println("location.href='login.do';");
				out.println("</script>");
				out.flush();
				out.close();
				return;
			}
			else  viewPage = contextPath +"/web/board/question/write.do";
		}
		
		
		
		else if(commandName.contains("/book/search.do")) {
			session.setAttribute("query", request.getParameter("query"));
			session.setAttribute("queryType", request.getParameter("queryType"));
			session.setAttribute("curPage", Integer.parseInt(request.getParameter("curPage")));
			viewPage = contextPath +"/web/book/searchView.do";
		}
		else if(commandName.contains("/book/reco.do")) {
			session.setAttribute("curPage", Integer.parseInt(request.getParameter("curPage")));
			viewPage = contextPath + "/web/book/recoView.do";
		}
		
		else if(commandName.contains("/book/genre.do")) {
			if(request.getParameter("genreNum") != null) session.setAttribute("categoryId", Integer.parseInt(request.getParameter("genreNum")));
			session.setAttribute("curPage", Integer.parseInt(request.getParameter("curPage")));
			viewPage = contextPath + "/web/book/genreView.do";
		}
		
		//회원관리
		else if(commandName.equals("/web/member/idCheck.do")) {
			command = new idCheckCommand();
			command.execute(request, response);
			viewPage = "idCheckOK.jsp";
			flag = true;
		}
		else if(commandName.equals("/web/member/registerOK.do")) {
			command = new MemberRegisterCommand();
			command.execute(request, response);
			viewPage = "index.do";
		}
		else if(commandName.equals("/web/member/loginOK.do")) {
			command = new LoginCommand();
			command.execute(request, response);
			
			if(!(boolean)request.getAttribute("flag")) {
				viewPage = contextPath + "/web/noticePage.jsp";
			}
			else viewPage = "index.do";
		}
		else if(commandName.equals("/web/member/modifyView.do")) {
			request.setAttribute("id", session.getAttribute("id"));
			command = new MemberViewCommand();
			command.execute(request, response);
			viewPage = "modify.jsp";
			flag = true;
		}
		else if(commandName.contains("/web/member/modifyOK.do")) {
			command = new MemberModifyCommand();
			command.execute(request, response);
			viewPage = "index.do";
		}
		else if(commandName.equals("/web/member/check.do")) {
			String boardType = request.getParameter("boardType");
			if(boardType == null) boardType = "review";
			request.setAttribute("checkFlag", true);
			
			if(boardType.equals("review")) {
				command = new CheckPostCommand();
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
			
			viewPage = "checkPost.jsp";
			flag = true;
		}
		
		
		//서평게시판
		else if(commandName.equals("/web/board/review/list.do")) {
			command = new ReviewBoardListCommand();
			command.execute(request, response);
			viewPage = "list.jsp";
			flag = true;
		}
		
		else if(commandName.equals("/web/board/review/writeOK.do")) {
			command = new ReviewBoardWriteCommand();
			command.execute(request, response);
			viewPage = "list.do";
		}
		else if(commandName.equals("/web/board/review/view.do")) {
			command = new ReviewBoardContentViewCommand();
			command.execute(request, response);
			viewPage = "contentView.jsp";
			flag = true;
		}
		else if(commandName.equals("/web/board/review/modify.do")) {
			command = new ReviewBoardModifyCommand();
			command.execute(request, response);
			viewPage = "list.do";
		}
		else if(commandName.equals("/web/board/review/delete.do")) {
			command = new ReviewBoardDeleteCommand();
			command.execute(request, response);
			viewPage = "list.do";
		}
		else if(commandName.equals("/web/board/review/searchView.do")) {
			command = new ReviewBoardSearchCommand();
			command.execute(request, response);
			viewPage = "list.jsp";
			flag = true;
		}
		
		else if(commandName.equals("/web/board/review/comment.do")) {
			command = new ReviewBoardCommentCommand();
			command.execute(request, response);
			viewPage = "view.do?no="+(int)request.getAttribute("groupNum");
		}
		else if(commandName.equals("/web/board/review/modifyCom.do")) {
			command = new ReviewBoardModifyCommentCommand();
			command.execute(request, response);
			viewPage = "view.do?no=" + (int)request.getAttribute("groupNum");
		}
		else if(commandName.equals("/web/board/review/deleteCom.do")) {
			command = new ReviewBoardDeleteCommentCommand();
			command.execute(request, response);
			viewPage = "view.do?no=" + (int)request.getAttribute("groupNum");
		}
		
		//공지사항
		else if(commandName.equals("/web/board/notice/list.do")) {
			command = new NBListCommand();
			command.execute(request, response);
			viewPage = "noticeList.jsp";
			flag = true;
		}
		else if(commandName.equals("/web/board/notice/write.do")) {
			viewPage = "noticeWrite.jsp";
		}
		else if(commandName.equals("/web/board/notice/writeOK.do")) {
			command = new NBWriteCommand();
			command.execute(request, response);
			viewPage = "list.do";
		}
		else if(commandName.equals("/web/board/notice/view.do")) {
			command = new NBViewCommand();
			command.execute(request, response);
			viewPage = "noticeView.jsp";
			flag = true;
		}
		else if(commandName.equals("/web/board/notice/modify.do")) {
			command = new NBModifyCommand();
			command.execute(request, response);
			viewPage = "list.do";
		}
		else if(commandName.equals("/web/board/notice/delete.do")) {
			command = new NBDeleteCommand();
			command.execute(request, response);
			viewPage = "list.do";
		}
		else if(commandName.equals("/web/board/notice/comment.do")) {
			command = new NBCommentCommand();
			command.execute(request, response);
			viewPage = "view.do?no="+(int)request.getAttribute("bno");
		}
		else if(commandName.equals("/web/board/notice/modifyCom.do")) {
			command = new NBCommentModifyCommand();
			command.execute(request, response);
			viewPage = "view.do?no="+(int)request.getAttribute("bno");
		}
		else if(commandName.equals("/web/board/notice/deleteCom.do")) {
			command = new NBCommentDeleteCommand();
			command.execute(request, response);
			viewPage = "view.do?no="+(int)request.getAttribute("bno");
		}
		else if(commandName.equals("/web/board/notice/searchView.do")) {
			command = new NBSearchCommand();
			command.execute(request, response);
			viewPage = "noticeList.jsp";
			flag = true;
		}
		
		//문의게시판
		else if(commandName.equals("/web/board/question/list.do")) {
			request.setAttribute("boardType", "question");
			command = new GBListCommand();
			command.execute(request, response);
			viewPage = "qList.jsp";
			flag = true;
		}
		else if(commandName.equals("/web/board/question/write.do")) {
			viewPage = "qWrite.jsp";
		}
		else if(commandName.equals("/web/board/question/writeOK.do")) {
			command = new GBWriteCommand();
			command.execute(request, response);
			viewPage = "list.do";
		}
		else if(commandName.equals("/web/board/question/view.do")) {
			command = new GBViewCommand();
			command.execute(request, response);
			viewPage = "qView.jsp";
			flag = true;
		}
		else if(commandName.equals("/web/board/question/modify.do")) {
			command = new GBModifyCommand();
			command.execute(request, response);
			viewPage = "list.do";
		}
		else if(commandName.equals("/web/board/question/delete.do")) {
			command = new GBDeleteCommand();
			command.execute(request, response);
			viewPage = "list.do";
		}
		else if(commandName.equals("/web/board/question/comment.do")) {
			command = new GBCommentCommand();
			command.execute(request, response);
			viewPage = "view.do?no=" + (int)request.getAttribute("groupNum")+"&boardType=" + (String)request.getAttribute("boardType");
		}
		else if(commandName.equals("/web/board/question/modifyCom.do")) {
			command = new GBModifyCommentCommand();
			command.execute(request, response);
			viewPage = "view.do?no=" + (int)request.getAttribute("groupNum")+"&boardType=" + (String)request.getAttribute("boardType");
		}
		else if(commandName.equals("/web/board/question/deleteCom.do")) {
			command = new GBCommentDeleteCommand();
			command.execute(request, response);
			viewPage = "view.do?no=" + (int)request.getAttribute("groupNum") + "&boardType=" + (String)request.getAttribute("boardType");
		}
		else if(commandName.equals("/web/board/question/searchView.do")) {
			command = new GBSearchCommand();
			command.execute(request, response);
			viewPage = "qList.jsp";
			flag = true;
		}
		
		
		//정보게시판
		else if(commandName.equals("/web/board/information/list.do")) {
			request.setAttribute("boardType", "information");
			command = new GBListCommand();
			command.execute(request, response);
			viewPage = "infoList.jsp";
			flag = true;
		}
		else if(commandName.equals("/web/board/information/write.do")) {
			viewPage = "infoWrite.jsp";
		}
		else if(commandName.equals("/web/board/information/writeOK.do")) {
			command = new GBWriteCommand();
			command.execute(request, response);
			viewPage = "list.do";
		}
		else if(commandName.equals("/web/board/information/view.do")) {
			command = new GBViewCommand();
			command.execute(request, response);
			viewPage = "infoView.jsp";
			flag = true;
		}
		else if(commandName.equals("/web/board/information/modify.do")) {
			command = new GBModifyCommand();
			command.execute(request, response);
			viewPage = "list.do";
		}
		else if(commandName.equals("/web/board/information/delete.do")) {
			command = new GBDeleteCommand();
			command.execute(request, response);
			viewPage = "list.do";
		}
		else if(commandName.equals("/web/board/information/comment.do")) {
			command = new GBCommentCommand();
			command.execute(request, response);
			viewPage = "view.do?no=" + (int)request.getAttribute("groupNum")+"&boardType=" + (String)request.getAttribute("boardType");
		}
		else if(commandName.equals("/web/board/information/modifyCom.do")) {
			command = new GBModifyCommentCommand();
			command.execute(request, response);
			viewPage = "view.do?no=" + (int)request.getAttribute("groupNum")+"&boardType=" + (String)request.getAttribute("boardType");
		}
		else if(commandName.equals("/web/board/information/deleteCom.do")) {
			command = new GBCommentDeleteCommand();
			command.execute(request, response);
			viewPage = "view.do?no=" + (int)request.getAttribute("groupNum") + "&boardType=" + (String)request.getAttribute("boardType");
		}
		else if(commandName.equals("/web/board/information/searchView.do")) {
			command = new GBSearchCommand();
			command.execute(request, response);
			viewPage = "infoList.jsp";
			flag = true;
		}
		
		//책정보
		else if(commandName.equals("/web/book/searchView.do")) {
			command = new BookSearchCommand();
			command.execute(request, response);
			viewPage = "searchResult.jsp";
			flag = true;
		}
		else if(commandName.equals("/web/book/recoView.do")) {
			command = new RecoBookCommand();
			command.execute(request, response);
			viewPage = "requestPage.jsp";
			flag = true;
		
		}
		else if(commandName.equals("/web/book/genreView.do")) {
			command = new BestCommand();
			command.execute(request, response);
			viewPage = "requestPage.jsp";
			flag = true;
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
