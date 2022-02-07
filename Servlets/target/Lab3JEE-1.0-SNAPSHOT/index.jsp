<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello World!" %>
</h1>
<br/>
<a href="hello-servlet">Hello Servlet</a>
<a href="HelloWorldAnnotation">Servlet</a>

<form action="<%= request.getContextPath() %>/hello-servlet/Servlet" method="post">
    Имя: <input type="text" name="fname">
    <br> <br>

    Фамилия: <input type="text" name="lname">
    <br> <br>

    Группа: <input type="text" name="group">
    <br> <br>

    Почта: <input type="email" name="emailId">
    <br>
    <br>
    <input type="submit" value="Отправить">
</form>

</body>
</html>