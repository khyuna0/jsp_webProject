package com.khyuna0.command;

import com.khyuna0.dao.MemberDao;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginCommand implements SACommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		MemberDao memberDao = new MemberDao();
		HttpSession session;
		String mid = request.getParameter("memberid");
		String mpw = request.getParameter("memberpw");
		
		int loginFlag = memberDao.loginCheck(mid, mpw);
		
		if (loginFlag == 1) {
			session = request.getSession();
			session.setAttribute("sessionId", mid);
		} else {
			session = request.getSession();
			session.setAttribute("loginFail", "로그인 실패!");
		}

	}

}
