package com.khyuna0.dto;

public class GuideBoardDto {

	private int rnum;
	private int bnum;
	private String btitle;
	private String bcontent;
	private String memberid;
	private int bhit;
	private String bdate;
	private MemberDto memberdto;
	public GuideBoardDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GuideBoardDto(int rnum, int bnum, String btitle, String bcontent, String memberid, int bhit, String bdate,
			MemberDto memberdto) {
		super();
		this.rnum = rnum;
		this.bnum = bnum;
		this.btitle = btitle;
		this.bcontent = bcontent;
		this.memberid = memberid;
		this.bhit = bhit;
		this.bdate = bdate;
		this.memberdto = memberdto;
	}
	public GuideBoardDto(int rnum, int bnum, String btitle, String bcontent, String memberid, int bhit, String bdate) {
		super();
		this.rnum = rnum;
		this.bnum = bnum;
		this.btitle = btitle;
		this.bcontent = bcontent;
		this.memberid = memberid;
		this.bhit = bhit;
		this.bdate = bdate;
	}
	public GuideBoardDto(int bnum, String btitle, String bcontent, String memberid, int bhit, String bdate) {
		super();
		this.bnum = bnum;
		this.btitle = btitle;
		this.bcontent = bcontent;
		this.memberid = memberid;
		this.bhit = bhit;
		this.bdate = bdate;
	}
	
	public int getRnum() {
		return rnum;
	}
	public void setRnum(int rnum) {
		this.rnum = rnum;
	}
	public int getBnum() {
		return bnum;
	}
	public void setBnum(int bnum) {
		this.bnum = bnum;
	}
	public String getBtitle() {
		return btitle;
	}
	public void setBtitle(String btitle) {
		this.btitle = btitle;
	}
	public String getBcontent() {
		return bcontent;
	}
	public void setBcontent(String bcontent) {
		this.bcontent = bcontent;
	}
	public String getMemberid() {
		return memberid;
	}
	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}
	public int getBhit() {
		return bhit;
	}
	public void setBhit(int bhit) {
		this.bhit = bhit;
	}
	public String getBdate() {
		return bdate;
	}
	public void setBdate(String bdate) {
		this.bdate = bdate;
	}
	public MemberDto getMemberdto() {
		return memberdto;
	}
	public void setMemberdto(MemberDto memberdto) {
		this.memberdto = memberdto;
	}
	
}
