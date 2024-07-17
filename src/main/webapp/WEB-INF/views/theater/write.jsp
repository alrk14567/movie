<%@page language="java" contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>극장 등록하기</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous">
    </script>
    <script src="https://cdn.ckeditor.com/ckeditor5/41.4.2/classic/ckeditor.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script src="https://ckeditor.com/apps/ckfinder/3.5.0/ckfinder.js"></script>

</head>
<body>
<div class="container-fluid">
    <form method="post" action="/theater/write" enctype="multipart/form-data">
        <div class="table">
            <div class="row justify-content-center mb-3">
                <div class="col-6">
                    <div class="form-floating">
                        <input type="text" class="form-control" id="input_theaterName" name="theaterName" placeholder="theaterName">
                        <label for="input_theaterName">극장 이름</label>
                    </div>
                </div>
            </div>
            <div class="row justify-content-center mb-3">
                <div class="col-6">
                    <div class="form-floating">
                        <input type="text" class="form-control" id="input_theaterLocation" name="theaterLocation" placeholder="theaterLocation">
                        <label for="input_theaterLocation">극장 주소</label>
                    </div>
                </div>
            </div>
            <div class="row justify-content-center mb-3">
                <div class="col-6">
                    <div class="form-floating">
                        <input type="text" class="form-control" id="input_theaterTel" name="theaterTel" placeholder="theaterTel">
                        <label for="input_theaterTel">전화번호</label>
                    </div>
                </div>
            </div>

            <div class="row justify-content-center">
                <div class="col-6">
                    <label for="input_file">첨부 파일</label>
                    <input type="file" class="form-control" id="input_file" name="file" multiple>
                </div>
            </div>
            <div class="row justify-content-center">
                <div class="col-6">
                    <input type="submit" class="btn btn-outline-primary w-100" value="등록하기">
                </div>
            </div>
        </div>
    </form>
</div>

</body>
</html>
