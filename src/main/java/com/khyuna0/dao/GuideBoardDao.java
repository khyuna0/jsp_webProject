package com.khyuna0.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.khyuna0.dto.CommentDto;
import com.khyuna0.dto.GuideBoardDto;
import com.khyuna0.dto.MemberDto;

public class GuideBoardDao {

	private String drivername = "com.mysql.jdbc.Driver"; 
	private String url = "jdbc:mysql://localhost:3306/seoul_academy"; 
	private String username = "root";
	private String password = "12345";
	
	int sqlResult = 0;
	private static final int SUCCESS = 1;
	private static final int FAIL = 0;
	
	public static final int PAGE_SIZE = 10;
	public static final int PAGE_GROUP_SIZE = 10;
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	List<GuideBoardDto> GBDto = new ArrayList<GuideBoardDto>();
	MemberDto memberDto = new MemberDto();
	GuideBoardDto guideBoardDto = new GuideBoardDto();
	
	// 게시판 단순 글쓰기
	public void guideBoardWrite(String btitle, String memberid, String bcontent) {
		
		String sql = "INSERT INTO guideboard (btitle, memberid, bcontent, bhit) VALUES (?,?,?,0)";

		try {
			Class.forName(drivername);		
			conn = DriverManager.getConnection(url, username, password);
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, btitle);
			pstmt.setString(2, memberid);
			pstmt.setString(3, bcontent);
			
			pstmt.executeUpdate(); 
			
			
		} catch (Exception e) {
			
			System.out.println("DB 에러 발생! 글쓰기 실패");
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
	
	
	// 게시판 글 불러오기
	public List<GuideBoardDto> guideBoardList(int page) {
		
		String sql =	" SELECT ROW_NUMBER() OVER (ORDER BY bnum ASC) AS rnum, " +
					    " bnum, btitle, bcontent, memberid, bhit, bdate " +
					    " FROM guideBoard " +
					    " ORDER BY bnum DESC" +
					    " LIMIT ? OFFSET ?";
		
		int offset = ( page - 1 ) * PAGE_SIZE;

		try {
			Class.forName(drivername);		
			conn = DriverManager.getConnection(url, username, password);
			
			pstmt = conn.prepareStatement(sql); 
			pstmt.setInt(1, PAGE_SIZE);
			pstmt.setInt(2, offset);
			rs = pstmt.executeQuery(); 
			
			while (rs.next()) { 
				
				int rnum = rs.getInt("rnum");
				
				int bnum = rs.getInt("bnum");
				String btitle = rs.getString("btitle");
				String bcontents = rs.getString("bcontent");
				String memberid = rs.getString("memberid");
				int bhit = rs.getInt("bhit");
				String bdate = rs.getString("bdate");
				
				
				guideBoardDto = new GuideBoardDto(rnum, bnum, btitle, bcontents, memberid, bhit, bdate);
				GBDto.add(guideBoardDto);
			} 
			
		} catch (Exception e) {
			
			System.out.println("DB 에러 발생! 게시판 불러오기 실패");
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
		return GBDto;
	}
	
	// 게시판 글 검색
	
	public List<GuideBoardDao> SearchBoardList (int page, String searchType, String searchKeyword) { 
		
		String sql =  " SELECT ROW_NUMBER() OVER (ORDER BY bnum ASC) AS bno,"
					+ " B.bnum, B.btitle, B.bcontents, B.memberid, M.memberemail, B.bhit, B.bdate "
		            + " FROM board "
		            + " WHERE " + searchType + " LIKE ? "
		            + " ORDER BY bno DESC "
		            + " LIMIT ? OFFSET ? ";

		List<GuideBoardDao> searchList = new ArrayList<GuideBoardDao>();
		int offset = ( page - 1 ) * PAGE_SIZE;
		
		try {
			Class.forName(drivername); 		
			conn = DriverManager.getConnection(url, username, password);
			
			pstmt = conn.prepareStatement(sql); 

			pstmt.setString(1, "%" + searchKeyword + "%");
			pstmt.setInt(2, PAGE_SIZE);
			pstmt.setInt(3, offset);
			rs = pstmt.executeQuery(); 
			
			while(rs.next()) { // 성공
				
				int rnum = rs.getInt("rnum");
				int bnum = rs.getInt("bnum");
				String btitle = rs.getString("btitle");
				String bcontents = rs.getString("bcontents");
				String memberid = rs.getString("memberid");
				int bhit = rs.getInt("bhit");
				String bdate = rs.getString("bdate");			
			
				guideBoardDto = new GuideBoardDto(rnum, bnum, btitle, bcontents, memberid, bhit, bdate);
				GBDto.add(guideBoardDto);
				
			}
			
			
		} catch (Exception e) {
			System.out.println("DB 에러 발생! 게시판 불러오기 실패");
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
		
		return searchList; // 글들(bDto)이 담긴 boardlist 리스트 반환
	}//
	
	
	// 게시판에서 선택한 글 보기
	
	public GuideBoardDto guideBoardView(int num) {
		
		String sql = "SELECT * FROM guideboard WHERE bnum = ?";

		try {
			Class.forName(drivername);		
			conn = DriverManager.getConnection(url, username, password);
			
			pstmt = conn.prepareStatement(sql); 
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery(); 
			
			if (rs.next()) { 

				int bnum = rs.getInt("bnum");
				String btitle = rs.getString("btitle");
				String bcontents = rs.getString("bcontent");
				String memberid = rs.getString("memberid");
				int bhit = rs.getInt("bhit");
				String bdate = rs.getString("bdate");
				
				
				guideBoardDto = new GuideBoardDto(bnum, btitle, bcontents, memberid, bhit, bdate);
				
			} 
			
		} catch (Exception e) {
			
			System.out.println("DB 에러 발생! 게시판 불러오기 실패");
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
		return guideBoardDto;
	}
	
	
	// 게시판 글 수정하기
	
	public void boardUpdate (int bnum, String btitle, String bcontent) {
		
		String sql = "UPDATE guideboard SET btitle = ?, bcontent = ? WHERE bnum = ?";
		
		try {
			Class.forName(drivername);			
			conn = DriverManager.getConnection(url, username, password);
			
			pstmt = conn.prepareStatement(sql); 
			pstmt.setString(1, btitle);
			pstmt.setString(2, bcontent);
			pstmt.setInt(3, bnum);
				
			pstmt.executeUpdate(); 
			
		} catch (Exception e) {
			System.out.println("DB 에러 발생! 글 수정 실패!");
			e.printStackTrace(); 
		} finally {  
			try {
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
		
	} //
	
	// 게시판 글 삭제하기 
	
	public void boardDelete(int bnum) {
		
		String sql = "DELETE FROM guideboard WHERE bnum = ?";
		
		try {
			Class.forName(drivername);			
			conn = DriverManager.getConnection(url, username, password);
			
			pstmt = conn.prepareStatement(sql); 
			pstmt.setInt(1, bnum);
				
			pstmt.executeUpdate(); 
			
		} catch (Exception e) {
			System.out.println("DB 에러 발생! 글 삭제 실패!");
			e.printStackTrace(); 
		} finally {  
			try {
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
	
		
// ---------- 페이징 ------------
	
	// 전체 글 개수 세기
	public int listTotal() {
		
		String sql = "SELECT * FROM guideboard";
		int count = 0;

		try {
			Class.forName(drivername);		
			conn = DriverManager.getConnection(url, username, password);
			
			pstmt = conn.prepareStatement(sql); 
			rs = pstmt.executeQuery(); 
		
		while (rs.next()) {
			count++;
		}
			
			
		} catch (Exception e) {
			
			System.out.println("DB 에러 발생! 글 개수 불러오기 실패!");
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
		return count;
	}
	
	
	
	
// --------------- 댓글 ------------------	
	
	public void commentWrite(String bnum, String memberid, String comment) {
		
		String sql = "INSERT INTO comment(bnum, memberid, comment) VALUES (?,?,?)";
		
		try {
			Class.forName(drivername);		
			conn = DriverManager.getConnection(url, username, password);
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, bnum);
			pstmt.setString(2, memberid);
			pstmt.setString(3, comment);
			
			pstmt.executeUpdate(); 
			
			
		} catch (Exception e) {
			
			System.out.println("DB 에러 발생! 댓글 쓰기 실패");
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
	
	
	// 모든 댓글 리스트 반환하는 메서드
	public List<CommentDto> commentList(int bcnum) {
		
		List<CommentDto> commentDtos = new ArrayList<CommentDto>();
		
		String sql = "SELECT * FROM comment WHERE bnum = ?";
			   

		try {
			Class.forName(drivername);		
			conn = DriverManager.getConnection(url, username, password);
			
			pstmt = conn.prepareStatement(sql); 
			pstmt.setInt(1, bcnum);
			rs = pstmt.executeQuery(); 
			
			while (rs.next()) { 
				
				int cnum = rs.getInt("cnum");
				int bnum = rs.getInt("bnum");
				String memberid = rs.getString("memberid");
				String comment = rs.getString("comment");
				String cdate = rs.getString("cdate");
				
				CommentDto commentDto = new CommentDto(cnum, bnum, memberid, comment, cdate);
				commentDtos.add(commentDto);
				
			} 
			
		} catch (Exception e) {
			
			System.out.println("DB 에러 발생! 게시판 불러오기 실패");
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
		return commentDtos;
	}
	
}	
;