<%@page language="java" contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>영화 등록하기</title>
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
    <form method="post" action="/movie/write" enctype="multipart/form-data">
        <div class="table">
            <div class="row justify-content-center mb-3">
                <div class="col-6">
                    <div class="form-floating">
                        <input type="text" class="form-control" id="input_title" name="title" placeholder="title">
                        <label for="input_title">title</label>
                    </div>
                </div>
            </div>
            <div class="row justify-content-center mb-3">
                <div class="col-6">
                    <div class="form-floating">
                        <select name="movieGrade" id="selectMovieGrade" class="form-control" required>
                            <option value="전체이용가">전체이용가</option>
                            <option value="12세이상관람가">12세이상관람가</option>
                            <option value="15세이상관람가">15세이상관람가</option>
                            <option value="청소년관람불가">청소년관람불가</option>
                            <option value="제한상영가">제한상영가</option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="row justify-content-center mb-3">
                <div class="col-6">
                    <textarea name="story" id="input_story"></textarea>
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
<script>
    ClassicEditor
        .create(document.querySelector('#input_story'), {
            ckfinder: {
                uploadUrl: '/movie/uploads'
            }
        })      // 인풋 데이터로 받을 수 있게 선언
        .catch(error => {
            console.log(error)
        })
</script>
</body>
</html>
