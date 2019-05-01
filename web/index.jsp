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
    <title>
        Login
    </title>
</head>
<body>

<c:set var="message" value='${requestScope["message"]}' />
<c:if test="${not empty message}"><p>${message}</p></c:if>

<form action="login" method="post">
    Имя <input type="text" name="name"/>
    Пароль <input type="password" name="password"/>
    <input type="submit" value="Войти">
</form>

<a href="registration.jsp">Зарегистрироваться</a>

</body>
</html>
