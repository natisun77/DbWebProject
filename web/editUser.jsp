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
    <title>Изменение пользователя</title>
</head>
<body>

<form action="${pageContext.request.contextPath}/user" method="post">
    <input type="hidden" name="action" value="put"/>
    <input type="hidden" name="id" value="${user.id}"/>
    Имя <input type="text" name="name" value="${user.name}"/>
    Имейл <input type="text" name="email" value="${user.email}"/>
    Пароль <input type="text" name="password" value="${user.password}"/>
    Роль <input type="text" name="role" value="${user.role}"/>
    Соль <input type="text" name="salt" value="${user.salt}"/>

    <input type="submit">
</form>

</body>
</html>
