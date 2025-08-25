<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<html lang="ko">
<head>

	<c:if test="${param.loginfail == '1'}">
	  <script>
	    alert('아이디 또는 비밀번호가 일치하지 않습니다.');
	  </script>
	</c:if>
	
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>로그인 - Seoul Academy</title>
  <link rel="stylesheet" href="css/style.css">
</head>
<body>

  <%@ include file="include/header.jsp" %>

  <!-- 중앙 고정 배경 + 모달 -->
  <div class="auth-backdrop" aria-hidden="true"></div>
  <section class="auth-modal" role="dialog" aria-label="로그인">
    <div class="auth-head">
      <h2 class="auth-title">로그인</h2>
      <p class="auth-sub">Seoul Academy 계정</p>
    </div>

    <form class="form" action="loginOk.do" method="post" autocomplete="off">
      <div class="form-row">
        <label for="uid">아이디</label>
        <input type="text" id="memberid" name="memberid" required>
      </div>
      <div class="form-row">
        <label for="upw">비밀번호</label>
        <input type="password" id="memberpw" name="memberpw" required>
      </div>
      <div class="form-actions">
        <button type="submit" class="btn btn-primary" style="flex:1;">로그인</button>
        <a class="btn" href="signup.jsp">회원가입</a>
      </div>
      <p class="hint">성공 시 sessionScope.loginUser 에 사용자 정보를 저장하세요.</p>
    </form>

    <div class="auth-footer">
      <span>비밀번호를 잊으셨나요?</span>
      <a href="#">비밀번호 재설정</a>
    </div>
  </section>

  <%@ include file="include/footer.jsp" %>
</body>
</html>
