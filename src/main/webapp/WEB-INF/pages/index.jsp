<%@ page contentType="text/html; charset=UTF-8" %>
<!--<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>-->
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Bootstrap Example</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="${pageContext.request.contextPath}js/jquery-3.2.1.min.js"></script>
    <script src="${pageContext.request.contextPath}js/index.js"></script>

</head>
<body>
<div class="container">
    <p>Курс:</p>
    <select id="year" name="year">
        <option value="1">1</option>
        <option value="2">2</option>
        <option value="3">3</option>
        <option value="4">4</option>
        <option value="5">5</option>
    </select>
    <p>Семестр:</p>
    <select id="semester" name="semester">
        <option value="1">1</option>
        <option value="2">2</option>
    </select>
    <button type="submit" onclick="requestRatingDocument()">Створити</button>
    <div id="group">

    </div>
</div>

</body>
</html>