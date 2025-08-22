package com.khyuna0.command;

import com.khyuna0.dao.MemberDao;
import com.khyuna0.dto.MemberDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class joinCommand implements SACommand {

	
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		MemberDao memberDao = new MemberDao();
		
		String mid = request.getParameter("memberid");
		String mpw = request.getParameter("memberpw");
		String mname = request.getParameter("membername");
		String memail = request.getParameter("memberemail");
		
		// 회원가입 확인 (아이디 중복) 구현할것
		
		MemberDto memberDto = memberDao.join(mid, mpw, mname, memail);
		
		
		request.setAttribute("memberDto", memberDto);

	}

}
