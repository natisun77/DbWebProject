<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 28.04.2019
  Time: 23:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Добавление пользователя</title>
</head>
<body>

<form action="${pageContext.request.contextPath}/user" method="post">
    Имя <input type="text" name="name"/>
    Имейл <input type="text" name="email"/>
    Пароль <input type="password" name="password"/>
    <input type="submit">
</form>

</body>
</html>
