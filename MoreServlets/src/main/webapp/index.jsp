<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Lab4JEE</title>
</head>
<body>
<h1><%= "Laboratory work 4!" %>
</h1>
<br/>
<h1> Форма регистрации</h1>
<form action="<%= request.getContextPath() %>/servlet1" method="post">
    Имя: <input type="text" name="fName">
    <br> <br>

    Фамилия: <input type="text" name="lName">
    <br> <br>

    Группа: <input type="text" name="group">
    <br> <br>

    Почта: <input type="email" name="emailId">
    <br> <br>

    <input type="submit" value="Отправить">
</form>

</body>
</html>