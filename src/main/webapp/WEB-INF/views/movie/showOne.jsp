<%@page language="java" contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html>
<head>
    <title>${movieDTO.id}번 영화</title>
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
                    <th>영화 번호</th>
                    <td>${movieDTO.id}</td>
                </tr>
                <tr>
                    <th>제목</th>
                    <td>${movieDTO.title}</td>
                </tr>
                <tr>
                    <th>작성일</th>
                    <td><fmt:formatDate value="${movieDTO.entryDate}" pattern="yyyy년 MM월 dd일 E요일 HH시 mm분 ss초"/></td>
                </tr>
                <tr>
                    <th>수정일</th>
                    <td><fmt:formatDate value="${movieDTO.modifyDate}" pattern="yyyy년 MM월 dd일 E요일 HH시 mm분 ss초"/></td>
                </tr>
                <tr>
                    <th colspan="2" class="text-center">내용</th>
                </tr>
                <tr>
                    <td colspan="2" >${movieDTO.story}</td>
                </tr>
                <c:if test="${logIn.role eq ADMIN}">
                    <tr class="text-center">
                        <td class="text-center" colspan="3">
                            <a class="btn btn-outline-success" href="/movie/update/${movieDTO.id}">수정하기</a>
                            <button class="btn btn-outline-danger" onclick="deleteMovie(${movieDTO.id})">삭제하기</button>
                        </td>
                    </tr>
                </c:if>
                <tr>
                    <td colspan="3" class="text-center">
                        <a class="btn btn-outline-secondary" href="/movie/showAll">목록으로</a>
                    </td>
                </tr>
            </table>

            <table class="table table-primary table-striped">
                <tr class="text-center">
                    <td>댓글</td>
                    <form action="/reply/insert/${movieDTO.id}" method="post">
                        <td colspan="5">
                            <input type="text" name="content" class="form-control justify-content-center" placeholder="댓글">
                        </td>
                        <td>
                            <input type="submit" class="btn btn-outline-success" value="작성"/>
                        </td>
                    </form>
                </tr>
                <c:forEach items="${replyList}" var="list">
                    <tr id="tr-${list.id}">
                        <c:choose>
                            <c:when test="${list.writerId eq logIn.id}">
                                <form id="form-${productDTO.id}" action="/reply/update/${list.id}" method="post">
                                    <td colspan="2">
                                        <input id='form-${list.id}' type="text" class="form-control" name="content"
                                               value="${list.content}" disabled>
                                    </td>
                                    <td>${list.nickname}</td>
                                    <td><fmt:formatDate value="${list.modifyDate}" pattern="yy년MM월d일 HH:mm"/></td>
                                    <td>
                                        <div id="div-${list.id}" class="btn btn-outline-primary"
                                             onclick="buttonClick(${list.id},${movieDTO.id})">수정
                                        </div>
                                    </td>
                                    <td>
                                        <a href="/reply/delete/${list.id}" class="btn btn-outline-warning">삭제</a>
                                    </td>
                                </form>
                            </c:when>
                            <c:otherwise>
                                <td colspan="2">${list.content}</td>
                                <td>${list.nickname}</td>
                                <td><fmt:formatDate value="${list.modifyDate}" pattern="yy년MM월d일 HH:mm"/></td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</div>
<script>
    function deleteMovie(id) {
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
                    location.href = '/movie/delete/' + id;
                })
            }
        })
    }

    let updateUse = false;

    function buttonClick(replyId, movieId) {
        updateUse = true;
        if (updateUse) {
            disableButton(replyId);
            let div = document.getElementById("div-" + replyId);
            let td = document.createElement("td");
            let form = document.getElementById("form-" + movieId);
            let input = document.createElement("input");
            let btnSubmit = document.createElement("input");
            form.setAttribute("action", "/reply/update/" + replyId);
            input.setAttribute("type", "text")
            input.setAttribute("name", "content")
            input.setAttribute("value", "샘플")

            btnSubmit.setAttribute("type", "submit");
            btnSubmit.setAttribute("value", "수정");
            btnSubmit.className = div.className;

            form.appendChild(input);
            form.appendChild(btnSubmit);
            $(td).attr('colspan', '7');

            td.appendChild(form);

            let tds = $('#tr-'+replyId).children();
            for(e of tds){
                $(e).hide();
            }

            $('#tr-' + replyId).append(td);
        }
    }

    function disableButton(id) {
        console.log(id);
        // 수정 div를 submit 버튼으로 변환
        // inputContent에 disabled attribute 삭제
        $('#form-' + id).removeAttr('disabled');
    }
</script>
</body>
</html>
