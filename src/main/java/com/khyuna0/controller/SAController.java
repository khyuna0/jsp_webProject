package com.khyuna0.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.khyuna0.dao.GuideBoardDao;
import com.khyuna0.dao.MemberDao;
import com.khyuna0.dto.CommentDto;
import com.khyuna0.dto.GuideBoardDto;
import com.khyuna0.dto.MemberDto;

// import com.khyuna0.dao.ReviewBoardDao;
// import com.khyuna0.dto.ReviewBoardDto;

@WebServlet("*.do")
public class SAController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public SAController() { super(); }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	private void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");

		String uri = request.getRequestURI();
		String conPath = request.getContextPath();
		String comm = uri.substring(conPath.length()); // 최종 요청 값
		String viewPage = null; // 뷰페이지 설정

		HttpSession session = null;
		MemberDao memberDao = new MemberDao();
		MemberDto memberDto = new MemberDto();

		GuideBoardDao guideBoardDao = new GuideBoardDao();
		GuideBoardDto guideBoardDto = new GuideBoardDto();
		List<GuideBoardDto> gBDto = new ArrayList<>();
		
		List<CommentDto> CDto = new ArrayList<>();
		
		// ReviewBoardDao reviewBoardDao = new ReviewBoardDao();
		// ReviewBoardDto reviewBoardDto = new ReviewBoardDto();
		// List<ReviewBoardDto> rBDto = new ArrayList<>();

		// 대문
		if (comm.equals("/index.do")) {
			gBDto = guideBoardDao.guideBoardList(1);
			request.setAttribute("gBDto", gBDto);
			
			viewPage = "index.jsp";

		// 회원가입 화면
		} else if (comm.equals("/signup.do")){
			viewPage = "signup.jsp";

		// 회원가입 아이디 중복체크
		} else if (comm.equals("/idCheck.do")) {

			String mid = request.getParameter("memberid");
			String encMid = java.net.URLEncoder.encode(mid == null ? "" : mid, "UTF-8");

			if (memberDao.idCheck(mid) == 1) { // 중복
				response.sendRedirect("signup.do?idExists=1&memberid=" + encMid);
			} else {
				response.sendRedirect("signup.do?idExists=0&memberid=" + encMid);
			}
			return;

		// 회원가입 처리
		} else if (comm.equals("/signupOk.do")) {
			String mid = request.getParameter("memberid");
			String mpw = request.getParameter("memberpw");
			String mname = request.getParameter("membername");
			String memail = request.getParameter("memberemail");

			// 중복 방어
			if (memberDao.idCheck(mid) == 1) {
				String encMid = java.net.URLEncoder.encode(mid == null ? "" : mid, "UTF-8");
				response.sendRedirect("signup.do?idExists=1&memberid=" + encMid);
				return;
			}

			memberDto = memberDao.join(mid, mpw, mname, memail);
			response.sendRedirect("login.jsp");
			return;

		// 로그인 화면
		} else if (comm.equals("/login.do")){
			viewPage = "login.jsp";

		// 로그인 처리
		} else if (comm.equals("/loginOk.do")){
			String mid = request.getParameter("memberid");
			String mpw = request.getParameter("memberpw");

			int loginFlag = memberDao.loginCheck(mid, mpw);

			if (loginFlag == 1) {
				session = request.getSession();
				session.setAttribute("sessionId", mid);
				response.sendRedirect("index.do");
				return;
			} else {
				response.sendRedirect("login.do?loginfail=1");
				return;
			}

		// 로그아웃
		} else if (comm.equals("/logout.do")) {
			session = request.getSession(false);
			if (session != null) session.invalidate();
			response.sendRedirect("index.do?logout=1");
			return;

		// 마이페이지 보기
		} else if (comm.equals("/mypage.do")) {

			session = request.getSession(false);
			if (session == null || session.getAttribute("sessionId") == null) {
				response.sendRedirect("login.jsp");
				return;
			}

			String sid = (String) session.getAttribute("sessionId");
			memberDto = memberDao.mypage(sid);
			request.setAttribute("memberDto", memberDto);
			viewPage = "mypage.jsp";

		// 마이페이지 수정
		} else if (comm.equals("/mypageOk.do")) {

			session = request.getSession(false);
			String sid = (String) session.getAttribute("sessionId");

			String mpw = request.getParameter("memberpw");
			if (mpw == null || mpw.trim().isEmpty()) {
				mpw = request.getParameter("oldpw"); // 기존값 유지
			}
			String mname = request.getParameter("membername");
			String memail = request.getParameter("memberemail");

			memberDao.mypageModify(mpw, mname, memail, sid);
			response.sendRedirect("mypage.do");
			return;

		// --------------------- 수강 안내 게시판 ---------------------

		// 목록/검색
		} else if (comm.equals("/guideBoard.do")) {

			int page = 1;
			String p = request.getParameter("page");
			if (p != null && !p.isBlank()) {
				try { page = Integer.parseInt(p); } catch (NumberFormatException ignore) {}
			}

			String searchType = request.getParameter("searchType");
			String searchKeyword = request.getParameter("searchKeyword");
			searchKeyword = (searchKeyword == null) ? null : searchKeyword.trim();

			boolean doSearch = (searchType != null && !searchType.isBlank()
					&& searchKeyword != null && !searchKeyword.isBlank());

			int listTotal;
			if (doSearch) {
				listTotal = guideBoardDao.searchListTotal(searchType, searchKeyword);
			} else {
				listTotal = guideBoardDao.listTotal();
			}

			int totalPage = (int) Math.ceil((double) listTotal / GuideBoardDao.PAGE_SIZE);
			if (totalPage < 1) totalPage = 1;
			if (page < 1) page = 1;
			if (page > totalPage) page = totalPage;

			if (doSearch) {
				gBDto = guideBoardDao.SearchBoardList(page, searchType, searchKeyword);
				request.setAttribute("searchType", searchType);
				request.setAttribute("searchKeyword", searchKeyword);
			} else {
				gBDto = guideBoardDao.guideBoardList(page);
			}
			request.setAttribute("gBDto", gBDto);

			int startPage = (((page - 1) / GuideBoardDao.PAGE_GROUP_SIZE) * GuideBoardDao.PAGE_GROUP_SIZE) + 1;
			int endPage = Math.min(startPage + GuideBoardDao.PAGE_GROUP_SIZE - 1, totalPage);

			request.setAttribute("currentPage", page);
			request.setAttribute("listTotal", listTotal);
			request.setAttribute("totalPage", totalPage);
			request.setAttribute("startPage", startPage);
			request.setAttribute("endPage", endPage);

			
			viewPage = "guideBoard/guideBoard.jsp";


		// 글 상세 보기 (forward)
		} else if (comm.equals("/guideView.do")) {

			viewPage = "guideBoard/guideView.jsp";

		// 글 상세 보기 처리 + 댓글
		} else if (comm.equals("/guideViewOk.do")) {

			int bnum = Integer.parseInt(request.getParameter("bnum"));
			guideBoardDao.guideBoardHit(bnum); // 조회수 증가
			guideBoardDto = guideBoardDao.guideBoardView(bnum);
			CDto = guideBoardDao.commentList(bnum);

			request.setAttribute("comments", CDto);
			request.setAttribute("gview", guideBoardDto);


			viewPage = "guideBoard/guideView.jsp"; 

		// 글 쓰기 화면
		} else if (comm.equals("/guideBoardWrite.do")) {
			viewPage = "guideBoard/guideBoardWrite.jsp";

		// 글 쓰기 처리
		} else if (comm.equals("/guideBoardWriteOk.do")) {

			String btitle = request.getParameter("btitle");
			String memberid = request.getParameter("memberid");
			String bcontent = request.getParameter("bcontent");

			guideBoardDao.guideBoardWrite(btitle, memberid, bcontent);
			response.sendRedirect("guideBoard.do");
			return;

		// 글 삭제
		} else if (comm.equals("/guideDeleteOk.do")) {

			int bnum = Integer.parseInt(request.getParameter("bnum"));
			guideBoardDao.boardDelete(bnum);
			response.sendRedirect("guideBoard.do");
			return;

		// 글 수정 화면
		} else if (comm.equals("/guideBoardModify.do")) {

			int bnum = Integer.parseInt(request.getParameter("bnum"));
			guideBoardDto = guideBoardDao.guideBoardView(bnum);
			request.setAttribute("gview", guideBoardDto);

			viewPage = "guideBoard/guideBoardModify.jsp";


		// 글 수정 처리
		} else if (comm.equals("/guideModifyOk.do")) {

			int bnum = Integer.parseInt(request.getParameter("bnum"));
			String btitle = request.getParameter("btitle");
			String bcontent = request.getParameter("bcontent");
			guideBoardDao.boardUpdate(bnum, btitle, bcontent);

			response.sendRedirect("guideViewOk.do?bnum=" + bnum);
			return;

		// 댓글 쓰기
		} else if (comm.equals("/commentOk.do")) {
			request.setCharacterEncoding("utf-8");

			String bnum = request.getParameter("bnum");
			String comment = request.getParameter("comment");

			session = request.getSession(false);
			String memberid = (session == null) ? null : (String) session.getAttribute("sessionId");

			guideBoardDao.commentWrite(bnum, memberid, comment);
			response.sendRedirect("guideViewOk.do?bnum="+bnum);
			return;

		// 기본(인덱스)
		} else {
			viewPage = "index.jsp";
		}

		if (viewPage != null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
			dispatcher.forward(request, response);
		}
	}
}
