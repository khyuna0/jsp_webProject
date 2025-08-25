<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>글 수정 - Seoul Academy</title>
  <!-- 전역 스타일 안에 edit-page 규칙도 포함됨 -->
  <link rel="stylesheet" href="css/style.css">
</head>
<body class="edit-page">
  <%@ include file="include/header.jsp" %>

  <main class="container">
    <section class="section">
      <div class="edit-card">
        <h2 class="edit-title">글 수정</h2>

        <form class="edit-form" action="guideModifyOk.do" method="post" autocomplete="off">
          <input type="hidden" name="bnum" value="${gview.bnum}">

          <div class="edit-row">
            <label for="title">제목</label>
            <input type="text" id="btitle" name="btitle" value="${gview.btitle}" required maxlength="200">
          </div>

          <div class="edit-row">
            <label for="writer">작성자</label>
            <input type="text" id="memberid" name="memberid" value="${gview.memberid}" readonly>
          </div>

          <div class="edit-row edit-col-span">
            <label for="content">내용</label>
            <textarea id="bcontent" name="bcontent" class="edit-content" required>${gview.bcontent}</textarea>
          </div>

          <div class="edit-actions">
            <button type="submit" class="edit-btn edit-btn-primary">저장</button>
            <a href="guideView.do?bnum=${gview.bnum}" class="edit-btn">취소</a>
          </div>
        </form>
      </div>
    </section>
  </main>

  <%@ include file="include/footer.jsp" %>
</body>
</html>
