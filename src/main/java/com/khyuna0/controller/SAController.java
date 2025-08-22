package com.khyuna0.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.khyuna0.command.joinCommand;
import com.khyuna0.command.LoginCommand;
import com.khyuna0.command.SACommand;

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
		
		SACommand command = null;
		
		
		// 대문
		if (comm.equals("/index.do")) { 
			viewPage = "index.jsp";
		
		// 회원가입 화면	
		} else if (comm.equals("/signup.do")){ 
			viewPage = "signup.jsp";
		
		// 회원가입 확인 
		}else if (comm.equals("/signupOk.do")){ 
			// 아이디 중복 체크 선행
			command = new joinCommand();
			command.execute(request, response);
			response.sendRedirect("/login.do");
			return;
			
		// 로그인 화면
		} else if (comm.equals("/login.do")){ 
			viewPage = "login.jsp";
			
		// 로그인 확인
		} else if (comm.equals("/loginOk.do")){ 
			command = new LoginCommand();
			command.execute(request, response);
			response.sendRedirect("/index.do");	
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
