<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 헤더 -->
<header class="site-header">
  <div class="nav-bar">
    <div class="container nav-flex">
      <!-- 학원 이름(왼쪽) -->
      <h1 class="brand">
        <a href="<c:url value='/index.do'/>">Seoul Academy</a>
      </h1>

      <!-- 네비게이션 -->
		<nav class="main-nav">
		  <a href="<c:url value='/index.do'/>">홈</a>
		  <a href="<c:url value='/guideBoard.do'/>">수강안내 게시판</a>
		  <a href="<c:url value='/reviewBoard.do'/>">수강생 후기</a>
		
		  <c:choose>
		    <c:when test="${not empty sessionScope.sessionId}">
		      <span class="nav-text"> ${sessionScope.sessionId} 님 환영합니다.</span>
		      <a href="<c:url value='/logout.do'/>">로그아웃</a>
		      <a href="<c:url value='/mypage.do'/>">내 정보</a>
		    </c:when>
		    <c:otherwise>
		      <a href="<c:url value='/signup.do'/>">회원가입</a>
		      <a href="<c:url value='/login.do'/>">로그인</a>
		    </c:otherwise>
		  </c:choose>
		</nav>

    </div>
  </div>
</header>
