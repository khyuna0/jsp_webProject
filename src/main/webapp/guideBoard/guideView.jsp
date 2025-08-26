<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>${gview.btitle} - 수강안내 게시판</title>
  <link rel="stylesheet" href="css/style.css">
</head>
<body>
<%@ include file="../include/header.jsp" %>

<main class="container">
  <section class="section">
    <div class="card">
      <div class="post-head">
        <h2 class="post-title">${gview.btitle}</h2>
        <div class="post-meta muted">
          <span>작성자: ${gview.memberid}</span>
          <span>작성일: ${gview.bdate}</span>
          <span>조회수: ${gview.bhit}</span>
        </div>
      </div>

      <div class="post-body">
        <pre class="post-content"><c:out value="${gview.bcontent}"/></pre>
      </div>

      <div class="post-actions">
        <a class="btn" href="guideBoard.do">목록</a>
        <c:if test="${sessionScope.sessionId == gview.memberid || sessionScope.sessionRole == 'ADMIN'}">
          <a class="btn" href="guideBoardModify.do?bnum=${gview.bnum}">수정</a>
          <a class="btn btn-danger" href="guideDeleteOk.do?bnum=${gview.bnum}"
             onclick="return confirm('삭제하시겠습니까?');">삭제</a>
        </c:if>
      </div>
    </div>

    <!-- 댓글 영역(옵션) -->
    <div class="card comment-section">
      <h3 class="comment-title">댓글</h3>
      <c:if test="${empty comments}">
        <p class="muted">아직 댓글이 없습니다.</p>
      </c:if>
      <c:forEach var="c" items="${comments}">
        <div class="comment">
          <div class="comment-meta">
            <span class="comment-writer">${c.memberid}</span>
            <span class="comment-date">${c.cdate}</span>
          </div>
          <div class="comment-content"><c:out value="${c.comment}"/></div>
        </div>
      </c:forEach>

      <c:if test="${not empty sessionScope.sessionId}">
        <form class="comment-form" action="commentOk.do" method="post">
          <input type="hidden" name="bnum" value="${gview.bnum}">
          <div class="form-row"><textarea name="comment" placeholder="댓글을 입력하세요" required></textarea></div>
          <div class="form-actions"><button type="submit" class="btn btn-primary">등록</button></div>
        </form>
      </c:if>
    </div>
  </section>
</main>

<%@ include file="../include/footer.jsp" %>
</body>
</html>
