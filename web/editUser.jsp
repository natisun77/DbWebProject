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
    <title>Title</title>
</head>
<body>

<form action="${pageContext.request.contextPath}/user" method="post">
    <input type="hidden" name="action" value="put"/>
    <input type="hidden" name="id" value="${user.id}"/>
    Имя <input type="text" name="name" value="${user.name}"/>
    Пароль <input type="password" name="password" value="${user.password}"/>
    <input type="submit">
</form>

</body>
</html>
