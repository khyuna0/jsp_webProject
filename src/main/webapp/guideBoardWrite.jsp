<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>수강안내 글쓰기 - Seoul Academy</title>
  <link rel="stylesheet" href="css/style.css?v=fix1">

</head>
<body>
  <%@ include file="include/header.jsp" %>

	<c:if test="${empty sessionScope.sessionId}">
	  <script>
	    alert('로그인 하지 않은 유저는 접근 불가합니다.');
	    window.location.href='login.do';
	  </script>
	</c:if>

  <main class="container">
    <section class="section">
      <div class="card">
        <h2>글 쓰기</h2>

        <!-- 글쓰기 폼 -->
        <form class="form form-editor" action="guideBoardWriteOk.do" method="post" autocomplete="off">
          <div class="form-row">
            <label for="title">글 제목</label>
            <input type="text" id="btitle" name="btitle" required maxlength="200">
          </div>

          <div class="form-row">
            <label for="writer">작성자</label>
            <input type="text" id="memberid" name="memberid"
                   value="${sessionScope.sessionId}" required maxlength="50" readonly="readonly">
          </div>

          <div class="form-row form-col-span">
            <label for="content">내용</label>
            <textarea id="bcontent" name="bcontent" class="editor-content" required></textarea>
          </div>

          <div class="form-actions editor-actions">
            <button type="submit" class="btn btn-primary">글쓰기</button>
            <button type="button" class="btn" onclick="history.back()">취소</button>
          </div>
        </form>
      </div>
    </section>
  </main>

  <%@ include file="include/footer.jsp" %>
</body>
</html>
