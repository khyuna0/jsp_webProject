<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>회원가입 - Seoul Academy</title>
  <link rel="stylesheet" href="css/style.css">
</head>
<body>

	<c:if test="${param.doIdExists != null}">
	  <script>
	    alert('아이디 중복 체크를 해주세요!');
	  </script>
	</c:if>

  <%@ include file="include/header.jsp" %>

  <div class="auth-backdrop" aria-hidden="true"></div>
  <section class="auth-modal" role="dialog" aria-label="회원가입">
    <div class="auth-head">
      <h2 class="auth-title">회원가입</h2>
    </div>

	<form class="form" action="signupOk.do" method="post" autocomplete="off">
  <!-- 아이디 -->
  <div class="form-row">
  <label for="memberid">아이디</label>
  <div style="display:flex; gap:8px;">
    <!-- 중복 아님이면 값 유지, 중복이면 공백 -->
    <input type="text" id="memberid" name="memberid"
           value="${param.idExists eq '0' ? param.memberid : ''}"
           required minlength="4" maxlength="20" style="flex:1;">

    <button type="submit"
            class="btn btn-check"
            formaction="idCheck.do"
            formmethod="get"
            formnovalidate>
      중복확인
    </button>
  </div>

  <!-- 상태 메시지 -->
  <c:if test="${param.idExists eq '1'}">
    <p style="color:red;">중복된 아이디입니다. 다시 입력하세요.</p>
    <script>alert('중복된 아이디입니다!');</script>
  </c:if>
  <c:if test="${param.idExists eq '0'}">
    <p style="color:green;">사용 가능한 아이디입니다.</p>
  </c:if>
</div>


  <!-- 나머지 필드 (변수명 유지) -->
  <div class="form-row">
    <label for="memberpw">비밀번호</label>
    <input type="password" id="memberpw" name="memberpw"  minlength="8" maxlength="50">
  </div>

  <div class="form-row">
    <label for="membername">이름</label>
    <input type="text" id="membername" name="membername"  maxlength="20">
  </div>

  <div class="form-row">
    <label for="memberemail">이메일</label>
    <input type="email" id="memberemail" name="memberemail">
  </div>

  <div class="form-actions">
    <input type="submit"  value = "가입하기" class="btn btn-primary" style="flex:1;">
    <a class="btn" href="login.do">로그인</a>
  </div>
</form>

  </section>

  <%@ include file="include/footer.jsp" %>
</body>
</html>
