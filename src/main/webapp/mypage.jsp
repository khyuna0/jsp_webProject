<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>내 정보 - Seoul Academy</title>
  <link rel="stylesheet" href="css/style.css">
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
        <h2>내 정보</h2>
        <form class="form form-profile" action="mypageOk.do" method="post" autocomplete="off">
          
          <div class="form-grid">
            <!-- 아이디 (보기 전용) -->
            <div class="form-row readonly">
              <label for="memberid">아이디</label>
              <input type="text" id="memberid" name="memberid"
                     value="${memberDto.memberid}" readonly>
            </div>

            <!-- 이메일 (수정 가능) -->
            <div class="form-row readonly">
              <label for="memberemail">이메일</label>
              <input type="email" id="memberemail" name="memberemail"
                     value="${memberDto.memberemail}">
            </div>

            <!-- 이름 (수정 가능) -->
            <div class="form-row">
              <label for="membername">이름</label>
              <input type="text" id="membername" name="membername"
                     value="${memberDto.membername}" required maxlength="20">
            </div>

            <!-- 비밀번호 (수정 가능) -->
            <div class="form-row">
              <label for="memberpw">비밀번호</label>
              <input type="hidden" name="oldpw" value="${memberDto.memberpw}">
              <input type="password" id="memberpw" name="memberpw" placeholder="변경 시에만 입력">
            </div>

            <!-- 가입일 (보기 전용, 전체폭) -->
            <div class="form-row readonly form-col-span">
              <label for="memberdate">가입일</label>
              <input type="text" id="memberdate" name="memberdate"
                     value="${memberDto.memberdate}" readonly>
            </div>
          </div>

          <!-- 액션 -->
          <div class="form-actions">
          
          <input type="submit" class="btn btn-primary" value="저장">
            <button type="button" class="btn" onclick="history.back()">취소</button>
          </div>
        </form>
        
      </div>
    </section>
  </main>

  <%@ include file="include/footer.jsp" %>
</body>
</html>
