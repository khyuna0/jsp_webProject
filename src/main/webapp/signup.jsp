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
      <p class="auth-sub">Seoul Academy 신규 계정</p>
    </div>

    <form class="form" action="signupOk.do" method="post" autocomplete="off">
      <div class="form-row">
        <label for="uid">아이디</label>
        <div style="display:flex; gap:8px;">
          <input type="text" id="memberid" name="memberid" required minlength="4" maxlength="20" style="flex:1;">
          <!-- 여기 버튼만 추가 -->
          <a href="idCheck.do" class="btn btn-check">중복확인</a>
        </div>
        <p class="hint">영문/숫자 4~20자</p>
      </div>

      <div class="form-row">
        <label for="upw">비밀번호</label>
        <input type="password" id="membername" name="membername" required minlength="8" maxlength="50">
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
        <a class="btn" href="login.jsp">로그인</a>
      </div>
    </form>
  </section>

  <%@ include file="include/footer.jsp" %>
</body>
</html>
