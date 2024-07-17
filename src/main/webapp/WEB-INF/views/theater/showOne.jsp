<%@page language="java" contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html>
<head>
    <title>${theaterDTO.id}번 극장</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body>
<div class="container-fluid">
    <div class="row justify-content-center">
        <div class="col-6">
            <table class="table table-striped">
                <tr>
                    <th>극장 번호</th>
                    <td>${theaterDTO.id}</td>
                </tr>
                <tr>
                    <th>극장 이름</th>
                    <td>${theaterDTO.theaterName}</td>
                </tr>
                <tr>
                    <th>주소</th>
                    <td>${theaterDTO.theaterLocation}</td>
                </tr>
                <tr>
                    <th>전화번호</th>
                    <td>${theaterDTO.theaterTel}</td>
                </tr>
                <tr>
                    <th>작성일</th>
                    <td><fmt:formatDate value="${theaterDTO.entryDate}" pattern="yyyy년 MM월 dd일 E요일 HH시 mm분 ss초"/></td>
                </tr>
                <tr>
                    <th>수정일</th>
                    <td><fmt:formatDate value="${theaterDTO.modifyDate}" pattern="yyyy년 MM월 dd일 E요일 HH시 mm분 ss초"/></td>
                </tr>
                <c:if test="${logIn.role eq ADMIN}">
                    <tr class="text-center">
                        <td class="text-center" colspan="3">
                            <a class="btn btn-outline-success" href="/theater/update/${theaterDTO.id}">수정하기</a>
                            <button class="btn btn-outline-danger" onclick="deleteTheater(${theaterDTO.id})">삭제하기</button>
                        </td>
                    </tr>
                </c:if>
                <tr>
                    <td colspan="3" class="text-center">
                        <a class="btn btn-outline-secondary" href="/theater/showAll">목록으로</a>
                    </td>
                </tr>
            </table>


        </div>
    </div>
</div>
<script>
    function deleteTheater(id) {
        Swal.fire({
            title: '정말로 삭제하시겠습니까?',
            showCancelButton: true,
            confirmButtonText: '삭제하기',
            cancelButtonText: '취소',
            icon: 'warning'
        }).then((result) => {
            if (result.isConfirmed) {
                Swal.fire({
                    title: '삭제되었습니다.',
                    icon: 'check'
                }).then((result) => {
                    location.href = '/theater/delete/' + id;
                })
            }
        })
    }


</script>
</body>
</html>
