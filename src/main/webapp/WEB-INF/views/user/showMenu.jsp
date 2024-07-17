<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>ShowMenu</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
            crossorigin="anonymous"></script>
    <style>
        body {
            padding-top: 50px;
        }
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 20px;
            background-color: #f8f9fa;
            border-bottom: 1px solid #e7e7e7;
        }
        .content {
            padding: 20px;
        }
        .section {
            margin-bottom: 20px;
        }
        .menu-link {
            display: block;
            margin-bottom: 10px;
            text-align: center;
            padding: 10px;
            font-size: 18px;
        }
        .menu-image {
            width: 100%;
            max-width: 300px;
            height: auto;
        }
    </style>
</head>
<body>

<div class="header">
    <c:if test="${logIn.role eq 'ADMIN'}">
    <a href="/user/memberList" class="btn btn-secondary">회원 관리</a>
    </c:if>
    <div>
        <a href="/user/showOne" class="btn btn-primary">마이페이지</a>
        <a href="/user/logout" class="btn btn-danger">로그아웃</a>
    </div>
</div>
<hr color="#dc3026" style="margin-right: 13%; margin-left: 13%">
<div class="container">
    <div class="content">
        <div class="section">
            <a href="/movie/showAll" class="btn btn-success menu-link">Movie List</a>
            <a href="/movie/showAll"><img src="moviePoster.jpg" alt="영화 포스터 사진" class="menu-image"></a>
        </div>
        <div class="section">
            <a href="/theater/showAll" class="btn btn-success menu-link">Theater List</a>
            <a href="/theater/showAll"><img src="theater.jpg" alt="영화관 사진" class="menu-image"></a>
        </div>
    </div>
</div>

</body>
</html>
