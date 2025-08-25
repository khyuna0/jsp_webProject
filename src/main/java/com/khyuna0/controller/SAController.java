package com.khyuna0.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

import com.khyuna0.dao.GuideBoardDao;
import com.khyuna0.dao.MemberDao;
import com.khyuna0.dto.CommentDto;
import com.khyuna0.dto.GuideBoardDto;
import com.khyuna0.dto.MemberDto;


/**
 * Servlet implementation class SAController
 */
@WebServlet("*.do")
public class SAController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public SAController() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
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
		List<GuideBoardDto> gBDto = new ArrayList<GuideBoardDto>();
		
		// 대문
		if (comm.equals("/index.do")) { 
			viewPage = "index.jsp";
		
			
		// 회원가입 화면	
		} else if (comm.equals("/signup.do")){ 
			viewPage = "signup.jsp";
		
			
		// 회원가입 확인 
		}else if (comm.equals("/signupOk.do")){ 

			String mid = request.getParameter("memberid");
			String mpw = request.getParameter("memberpw");
			String mname = request.getParameter("membername");
			String memail = request.getParameter("memberemail");
			
			int idcheck = memberDao.idCheck(mid);
			
			if(idcheck != 1 ) { // 1 이면 아이디 중복 존재
				
				memberDto = memberDao.join(mid, mpw, mname, memail);

			} else {
				response.sendRedirect("signup.do?idExists=1");
			}
	
			
		// 로그인 화면
		} else if (comm.equals("/login.do")){ 
			viewPage = "login.jsp";
			
			
		// 로그인 확인
		} else if (comm.equals("/loginOk.do")){ 
			String mid = request.getParameter("memberid");
			String mpw = request.getParameter("memberpw");
			
			int loginFlag = memberDao.loginCheck(mid, mpw);

			if (loginFlag == 1) {
				session = request.getSession();
				session.setAttribute("sessionId", mid);
				response.sendRedirect("index.do");
			} else {
				response.sendRedirect("login.do?loginfail=1");
				return;
			}
		
		// 로그아웃	
		} else if (comm.equals("/logout.do")) {
			session = request.getSession(false);
		if (session != null) {
		    	session.invalidate();   // 세션 전체 삭제
		}
			response.sendRedirect("index.do?logout=1");
			return;
		
		
		// 마이페이지 들어가서 내 정보 보기	
		} else if (comm.equals("/mypage.do")) {
			
			String sid = (String) request.getSession().getAttribute("sessionId"); // 키 이름 확인
			System.out.println("sid=" + sid);
			
			session = request.getSession(false);
		    if (session == null || session.getAttribute("sessionId") == null) {
		        response.sendRedirect("login.jsp");
		        return;
		    }

		    sid = (String) session.getAttribute("sessionId");
		    memberDto = memberDao.mypage(sid);  
		    request.setAttribute("memberDto", memberDto); 
		    viewPage = "mypage.jsp";
		
		    
		// 회원 정보 수정하기
		} else if (comm.equals("/mypageOk.do")) {
			
			String mpw = null;
			
			session = request.getSession(false);
			String sid = (String) session.getAttribute("sessionId");			
			
			mpw = request.getParameter("memberpw");
			if (mpw == null || mpw.trim().isEmpty()) {
			    mpw = request.getParameter("oldpw"); // 기존 값 유지
			}
			
			String mname = request.getParameter("membername");
			String memail = request.getParameter("memberemail");
			
			memberDao.mypageModify(mpw, mname, memail, sid);
			response.sendRedirect("mypage.do");

	        return;

//---------------------	 수강 안내 게시판  --------------------------	        
		
		// 게시판 페이지 보기 (수강안내) / 모든 게시판 글 보기, 검색한 게시판 글 보기
		} else if (comm.equals("/guideBoard.do")) {   
			request.setCharacterEncoding("utf-8");
	        int page;
	        
	        String p = request.getParameter("page");
	        String searchType = request.getParameter("searchType");
		    String searchKeyword = request.getParameter("searchKeyword");
	       
		    
	        if (p != null && !p.isBlank()) { // 유저가 보고싶은 페이지 번호를 클릭함
		        page = Integer.parseInt(p);
		    } else { 
		        page = 1;
		    }
	        
		    int listTotal = guideBoardDao.listTotal();
		    int startPage = (((page - 1) / GuideBoardDao.PAGE_GROUP_SIZE) * GuideBoardDao.PAGE_GROUP_SIZE) + 1;
		    int endPage = startPage + GuideBoardDao.PAGE_GROUP_SIZE - 1;
	        
	        if (searchType != null && (String) searchKeyword.strip() != null ) { // 검색 결과 원하는 경우
	        	gBDto = guideBoardDao.SearchBoardList(1, searchType, searchKeyword);
	        	if(!gBDto.isEmpty()) {
	        		listTotal = gBDto.get(0).getRnum();
	        		
	        		gBDto = guideBoardDao.SearchBoardList(page, searchType, searchKeyword);
			    	request.setAttribute("searchType", searchType);
			    	request.setAttribute("searchKeyword", searchKeyword);
	        	} else {
	        		gBDto = guideBoardDao.guideBoardList(1);
	        	}
	        } else {
	        	request.setAttribute("gBDto", guideBoardDao.guideBoardList(page));
	        }
		    

		    
			int totalPage = (int) Math.ceil((double) listTotal / GuideBoardDao.PAGE_SIZE);
		    if (totalPage <= 0) totalPage = 1;
		    if (page < 1) page = 1;
		    if (page > totalPage) page = totalPage;
		    
		    if(endPage > totalPage) {
		    	endPage = totalPage;
		    }
			
		    request.setAttribute("currentPage", page);       // 유저가 현재 선택한 페이지 번호
		    request.setAttribute("listTotal", listTotal);
		    request.setAttribute("totalPage", totalPage);    // 총 글의 갯수로 표현될 전체 페이지의 수
		    request.setAttribute("startPage", startPage);	// 페이지 그룹 출력 시 첫번째 페이지 번호
		    request.setAttribute("endPage", endPage); // 페이지 그룹 출력 시 마지막 페이지 번호

		    
		    viewPage = "guideBoard.jsp";
		
		    
		// 게시판 검색 
		    
		        
		    
		    
		// 선택한 게시판 글 보기	
		} else if (comm.equals("/guideView.do")) {	

			viewPage = "guideView.jsp";	

		// 게시판 글 리스트에서 선택한 글 보기 처리
		} else if (comm.equals("/guideViewOk.do")) {	
			
			int bnum = Integer.parseInt(request.getParameter("bnum"));
			//System.out.println(bnum);
			guideBoardDto = guideBoardDao.guideBoardView(bnum);
			request.setAttribute("gview", guideBoardDto);

			viewPage = "guideView.do";
			
			
		// 게시판 글 쓰기 화면	
		} else if (comm.equals("/guideBoardWrite.do")) {
			viewPage = "guideBoardWrite.jsp";
			
			
		// 글 쓰기 처리	
		} else if (comm.equals("/guideBoardWriteOk.do")) {	
			
			String btitle = request.getParameter("btitle");
			String memberid = request.getParameter("memberid");
			String bcontent = request.getParameter("bcontent");
			
			guideBoardDao.guideBoardWrite(btitle, memberid, bcontent);
			response.sendRedirect("guideBoard.do");
			return;
		
			
		// 게시판 글 리스트에서 선택한 글 삭제 (본인이 쓴 글만 가능)
			
		} else if (comm.equals("/guideDeleteOk.do")) {
			
			int bnum = Integer.parseInt(request.getParameter("bnum"));
			guideBoardDao.boardDelete(bnum);

			viewPage = "guideBoard.do";
			
			
		} else if (comm.equals("/guideBoardModify.do"))	 {
			
			int bnum = Integer.parseInt(request.getParameter("bnum"));
			guideBoardDto = guideBoardDao.guideBoardView(bnum);
			request.setAttribute("gview", guideBoardDto);
			
			viewPage = "guideBoardModify.jsp";
			
		// 게시판 글 리스트에서 선택한 글 수정 (본인이 쓴 글만 가능)		
		} else if (comm.equals("/guideModifyOk.do")) {		
			
			int bnum = Integer.parseInt(request.getParameter("bnum"));
			String btitle = request.getParameter("btitle");
			String bcontent = request.getParameter("bcontent");
			guideBoardDao.boardUpdate(bnum, btitle, bcontent);
	
			response.sendRedirect("guideViewOk.do?bnum=" + bnum);

			
			
		// 댓글 쓰기
		} else if (comm.equals("/commentOk.do")) {
			request.setCharacterEncoding("utf-8");
			
			String bnum = request.getParameter("bnum");
			String comment = request.getParameter("comment");
			
			session = request.getSession(false);
			String memberid = (String) session.getAttribute("sessionId");
			guideBoardDao.commentWrite(bnum, memberid, comment);
			
			List<CommentDto> commentDtos = new ArrayList<CommentDto>();
			commentDtos = guideBoardDao.commentList(Integer.parseInt(bnum));
			
			request.setAttribute("commentDtos", commentDtos);
			response.sendRedirect("guideView.do?bnum="+bnum);
			return;
			
			
		// 기본 고정 인덱스 페이지 	
		}else {
			viewPage = "index.jsp";
		}
		
		
		if (viewPage != null) {
	        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
	        dispatcher.forward(request, response);
	    } 
		
	}

}
