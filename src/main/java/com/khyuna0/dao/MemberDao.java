package com.khyuna0.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


import com.khyuna0.dto.MemberDto;

public class MemberDao {
	
	private String drivername = "com.mysql.jdbc.Driver"; 
	private String url = "jdbc:mysql://localhost:3306/seoul_academy"; 
	private String username = "root";
	private String password = "12345";
	
	int sqlResult = 0;
	private static final int SUCCESS = 1;
	private static final int FAIL = 0;
	
	private static final int ID_ALREADY_EXISTS = 1;
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	MemberDto memberDto = new MemberDto();

	// 회원가입
	public MemberDto join(String memberid, String memberpw, String membername,String memberemail) {
		
		String sql = "INSERT INTO members (memberid,memberpw,membername,memberemail) VALUES (?,?,?,?)";
		
		LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String nowString = now.format(formatter);

		try {
			Class.forName(drivername);		
			conn = DriverManager.getConnection(url, username, password);
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, memberid);
			pstmt.setString(2, memberpw);
			pstmt.setString(3, membername);
			pstmt.setString(4, memberemail);
			
			int sqlResult = pstmt.executeUpdate(); 
			
			if ( sqlResult == SUCCESS ) { 

				memberDto.setMemberid(memberid);
				memberDto.setMemberpw(memberpw);
				memberDto.setMembername(membername);
				memberDto.setMemberemail(memberemail);
				memberDto.setMemberdate(nowString);

			}
			
		} catch (Exception e) {
			
			System.out.println("DB 에러 발생! 회원가입 실패");
			e.printStackTrace();
		} finally { 
			try {
				if(rs != null) { 
					rs.close();
				}				
				if(pstmt != null) { 
					pstmt.close();
				}				
				if(conn != null) { 
					conn.close();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return memberDto;
	}//
	
	// 회원가입 시 아이디 중복 확인
	public int idCheck(String mid) {
		
		String sql = "SELECT * FROM members WHERE memberid = ? AND memberpw =?";
		int sqlResult = 0;
		
		try {
			Class.forName(drivername);		
			conn = DriverManager.getConnection(url, username, password);
			
			pstmt = conn.prepareStatement(sql); 
			pstmt.setString(1, mid);
			rs = pstmt.executeQuery(); 
			
			if (rs.next()) { 
				sqlResult = ID_ALREADY_EXISTS; 
			} 
			
		} catch (Exception e) {
			
			System.out.println("DB 에러 발생! 아이디 중복 확인 실패");
			e.printStackTrace();
		} finally { 
			try {
				if(rs != null) { 
					rs.close();
				}				
				if(pstmt != null) { 
					pstmt.close();
				}				
				if(conn != null) { 
					conn.close();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return sqlResult;
		
	}//
	
	// 로그인 확인
	public int loginCheck(String mid, String mpw) {
		
		String sql = "SELECT * FROM members WHERE memberid = ? AND memberpw =?";
		int sqlResult = 0;
		
		try {
			Class.forName(drivername);		
			conn = DriverManager.getConnection(url, username, password);
			
			pstmt = conn.prepareStatement(sql); 
			pstmt.setString(1, mid);
			pstmt.setString(2, mpw);
			rs = pstmt.executeQuery(); 
			
			if (rs.next()) { 
				sqlResult = SUCCESS; 
			} 
			
		} catch (Exception e) {
			
			System.out.println("DB 에러 발생! 로그인 실패");
			e.printStackTrace();
		} finally { 
			try {
				if(rs != null) { 
					rs.close();
				}				
				if(pstmt != null) { 
					pstmt.close();
				}				
				if(conn != null) { 
					conn.close();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return sqlResult;
		
	}//	
	

	// 마이페이지 내 정보 불러오기
	public MemberDto mypage(String mid) {
		
		String sql = "SELECT * FROM members WHERE memberid = ?";
		
		try {
			Class.forName(drivername);		
			conn = DriverManager.getConnection(url, username, password);
			
			pstmt = conn.prepareStatement(sql); 
			pstmt.setString(1, mid);
			rs = pstmt.executeQuery(); 
			
			if (rs.next()) { 
				memberDto.setMemberid(rs.getString("memberid"));
				memberDto.setMemberpw(rs.getString("memberpw"));
				memberDto.setMembername(rs.getString("membername"));
				memberDto.setMemberemail(rs.getString("memberemail"));
				memberDto.setMemberdate(rs.getString("memberdate"));
			} 
			
		} catch (Exception e) {
			
			System.out.println("DB 에러 발생! 내 정보 불러오기 실패");
			e.printStackTrace();
		} finally { 
			try {
				if(rs != null) { 
					rs.close();
				}				
				if(pstmt != null) { 
					pstmt.close();
				}				
				if(conn != null) { 
					conn.close();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return memberDto;
	}
	
	
	// 마이페이지 내 정보 수정하기
	public void mypageModify(String memberpw, String membername, String memberemail, String memberid) {
		
		String sql = "UPDATE members SET memberpw=?, membername=?, memberemail=? WHERE memberid=?";

		try {
			Class.forName(drivername);		
			conn = DriverManager.getConnection(url, username, password);
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, memberpw);
			pstmt.setString(2, membername);
			pstmt.setString(3, memberemail);
			pstmt.setString(4, memberid);
			
			pstmt.executeUpdate(); 

		} catch (Exception e) {
			
			System.out.println("DB 에러 발생! 내 정보 수정 실패");
			e.printStackTrace();
		} finally { 
			try {
				if(rs != null) { 
					rs.close();
				}				
				if(pstmt != null) { 
					pstmt.close();
				}				
				if(conn != null) { 
					conn.close();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}	
	}//
	
	
	//게시판 글 쓰기
	public MemberDto guideBoardWrite(String memberid, String memberpw, String membername,String memberemail) {
		
		String sql = "INSERT INTO guideboard (memberid,memberpw,membername,memberemail) VALUES (?,?,?,?)";
		
		LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String nowString = now.format(formatter);

		try {
			Class.forName(drivername);		
			conn = DriverManager.getConnection(url, username, password);
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, memberid);
			pstmt.setString(2, memberpw);
			pstmt.setString(3, membername);
			pstmt.setString(4, memberemail);
			
			int sqlResult = pstmt.executeUpdate(); 
			
			if ( sqlResult == SUCCESS ) { 

				memberDto.setMemberid(memberid);
				memberDto.setMemberpw(memberpw);
				memberDto.setMembername(membername);
				memberDto.setMemberemail(memberemail);
				memberDto.setMemberdate(nowString);

			}
			
		} catch (Exception e) {
			
			System.out.println("DB 에러 발생! 회원가입 실패");
			e.printStackTrace();
		} finally { 
			try {
				if(rs != null) { 
					rs.close();
				}				
				if(pstmt != null) { 
					pstmt.close();
				}				
				if(conn != null) { 
					conn.close();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return memberDto;
	}//

	
}

