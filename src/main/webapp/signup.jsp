<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>회원가입 - Seoul Academy</title>
  <link rel="stylesheet" href="css/style.css">
</head>
<body>
  <%@ include file="include/header.jsp" %>

  <div class="auth-backdrop" aria-hidden="true"></div>
  <section class="auth-modal" role="dialog" aria-label="회원가입">
    <div class="auth-head">
      <h2 class="auth-title">회원가입</h2>
      <p class="auth-sub">Seoul Academy 신규 계정 생성</p>
    </div>

    <form class="form" action="signupOk.do" method="post" autocomplete="off">
      <div class="form-row">
        <label for="uid">아이디</label>
        <input type="text" id="memberid" name="memberid" required minlength="4" maxlength="20">
      </div>
      <div class="form-row">
        <label for="upw">비밀번호</label>
        <input type="password" id="memberpw" name="memberpw" required minlength="8" maxlength="50">
        <p class="hint">8자 이상 권장</p>
      </div>
      <div class="form-row">
        <label for="uname">이름</label>
        <input type="text" id="membername" name="membername" required maxlength="20">
      </div>
      <div class="form-row">
        <label for="email">이메일</label>
        <input type="email" id="memberemail" name="memberemail" required>
      </div>

      <div class="form-actions">
        <button type="submit" class="btn btn-primary" style="flex:1;">가입하기</button>
        <a class="btn" href="login.do">로그인</a>
      </div>
    </form>

    <div class="auth-footer">
      <span>이미 계정이 있으신가요?</span>
      <a href="login.jsp">로그인으로 이동</a>
    </div>
  </section>

  <%@ include file="include/footer.jsp" %>
</body>
</html>
