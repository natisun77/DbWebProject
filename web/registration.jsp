<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 21.04.2019
  Time: 23:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<center>
    Форма регистрации
    <br>
<c:set var="message" value='${requestScope["message"]}' />
<c:if test="${not empty message}"><p>${message}</p></c:if>

<form action="register" method="post">
    Имя <input type="text" name="name"/>
    Имеил <input type="text" name="email"/>
    Пароль <input type="password" name="password"/>
    <input type="submit">
</form>

<a href="index.jsp">Войти в систему</a>
</center>
</body>
</html>
