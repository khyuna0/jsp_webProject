<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>수강안내 게시판 - Seoul Academy</title>
  <link rel="stylesheet" href="css/style.css?v=fix">
</head>
<body>

	<c:if test="${param.noResult != null}">
	  <script>
	    alert('검색 결과가 없습니다!');
	  </script>
	</c:if>

<%@ include file="include/header.jsp" %>

<main class="container">
  <section class="section">
    <div class="card">
      <h2>수강안내 게시판</h2>

      <div class="board-toolbar">
        <form method="get" action="guideBoard.do" class="board-search" autocomplete="off">
          <select name="searchType">
            <option value="btitle"   ${searchType == 'btitle'   ? 'selected' : ''}>제목</option>
            <option value="bcontent" ${searchType == 'bcontent' ? 'selected' : ''}>내용</option>
            <option value="memberid"  ${searchType == 'memberid'  ? 'selected' : ''}>작성자</option>
          </select>
          <input type="text" name="searchKeyword" value="${searchKeyword != null ? searchKeyword : ''}" placeholder="검색어 입력">
          <button type="submit">검색</button>
        </form>

        <a href="guideBoardWrite.do" class="btn btn-primary">글쓰기</a>
      </div>

      <div class="table-wrap">
        <table class="table">
          <thead>
            <tr>
              <th style="width:80px">번호</th>
              <th>제목</th>
              <th style="width:140px">작성자</th>
              <th style="width:160px">작성일</th>
              <th style="width:100px">조회수</th>
            </tr>
          </thead>
          <tbody>
          <c:if test="${empty gBDto}">
            <tr><td colspan="5" class="muted" style="text-align:center;">게시글이 없습니다.</td></tr>
          </c:if>

          <c:forEach var="b" items="${gBDto}">
            <tr>
              <td>${b.rnum}</td>
              <td class="title-cell">
                <a href="guideViewOk.do?bnum=${b.bnum}">${b.btitle}</a>
              </td>
              <td>${b.memberid}</td>
              <td>${b.bdate}</td>
              <td>${b.bhit}</td>
            </tr>
          </c:forEach>
          </tbody>
        </table>
      </div>

      <c:if test="${totalPage > 1}">
        <nav class="pagination">
        
        <c:if test="${currentPage > 1 }">
      		<a href="guideBoard.do?page=${1 }&searchType=${searchType}&searchKeyword=${searchKeyword}">첫 번째 글</a>
    	</c:if> 
        
          <a class="page ${startPage == 1 ? 'disabled' : ''}"
             href="guideBoard.do?page=${startPage-1}&searchType=${searchType}&searchKeyword=${searchKeyword}">이전</a>

          <c:forEach var="p" begin="${startPage}" end="${endPage}">
          	<c:choose>
		      	<c:when test="${p == currentPage }"> 
		      		<a href="guideBoard.do?page=${p}&searchType=${searchType}&searchKeyword=${searchKeyword}" class="active"><b style="color: navy;">${p}</b></a>
		      	</c:when>
		      	<c:otherwise>
		      		<a href="guideBoard.do?page=${p}&searchType=${searchType}&searchKeyword=${searchKeyword}" class="active">${p}</a>
		      	</c:otherwise>	
		    </c:choose>  
          </c:forEach>

          <a class="page ${endPage == totalPage ? 'disabled' : ''}"
             href="guideBoard.do?page=${endPage+1}&searchType=${searchType}&searchKeyword${searchKeyword}">다음</a>
             
        <c:if test="${currentPage != totalPage }">
     		 <a href="guideBoard.do?page=${totalPage}&searchType=${searchType}&searchKeyword${searchKeyword}">마지막 글</a>
    	</c:if>    
    	
    	
        </nav>
      </c:if>
	
	
	
      <p class="muted" style="margin-top:8px;">총 ${listTotal}건</p>
    </div>
  </section>
</main>

<%@ include file="include/footer.jsp" %>
</body>
</html>
