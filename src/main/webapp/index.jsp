<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="ko">
<link rel="stylesheet" href="css/style.css">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Seoul Academy</title>
</head>
<body>

 	<c:if test="${param.logout == '1'}">
	  <script>
	    alert('로그아웃 되었습니다.');
	  </script>
	</c:if>

  <%@ include file="include/header.jsp" %>

<!-- 학원 홍보 배너 (배경 클릭 시 수강안내 게시판으로 이동) -->
<section class="index-hero">
  <a class="hero-link" href="guideBoard.do" aria-label="수강안내 게시판 바로가기"></a>
</section>

<!-- 수강생 후기 섹션: 테이블 제목 클릭 시 reviewBoard.do 이동 -->
<section class="section">
  <div class="container">
    <div class="card">
      <div class="board-toolbar">
        <h2 class="auth-title" style="margin:0;">
          <a href="reviewBoard.do" class="title-cell">수강생 후기</a>
        </h2>
      </div>

      <div class="table-wrap">
        <table class="table">
          <thead>
            <tr>
              <th style="width:80px">번호</th>
              <th>제목</th>
              <th style="width:160px">작성자</th>
              <th style="width:180px">작성일</th>
              <th style="width:100px">조회수</th>
            </tr>
          </thead>
          <tbody>
            <c:if test="${empty reviewList}">
              <tr><td colspan="5" class="muted" style="text-align:center;">후기가 없습니다.</td></tr>
            </c:if>
            <c:forEach var="r" items="${reviewList}">
              <tr>
                <td>${r.rnum}</td>
                <td class="title-cell"><a href="reviewView.do?bnum=${r.bnum}">${r.btitle}</a></td>
                <td>${r.memberid}</td>
                <td>${r.bdate}</td>
                <td>${r.bhit}</td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</section>

  </main>

  <%@ include file="include/footer.jsp" %>
</body>
</html>
