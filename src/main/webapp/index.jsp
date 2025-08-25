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

  <main class="container">
    <section class="section">
      <div class="card">
        <h2>안내</h2>
        <h2> </h2>
        <p class="muted">상단 메뉴에서 수강안내/후기 게시판으로 이동하세요. 로그인 사용자만 글쓰기 가능.</p>
        <div style="margin-top:14px;">
          <a class="btn btn-primary" href="<%=request.getContextPath()%>/guideBoard.jsp">수강안내 바로가기</a>
          <a class="btn" href="<%=request.getContextPath()%>/reviewBoard.jsp" style="margin-left:8px;">수강생 후기</a>
        </div>
      </div>
    </section>
  </main>

  <%@ include file="include/footer.jsp" %>
</body>
</html>
