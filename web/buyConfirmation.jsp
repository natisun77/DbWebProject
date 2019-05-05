<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 04.05.2019
  Time: 23:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Подтверждение покупки</title>
</head>
<body>
<center>
    <h2>Введите одноразовый код</h2>
<form action="/buy" method="post">
    <input hidden type="text" name="goodId" value="<c:out value="${goodId}"/>">
    <input type="password" title="Код" name="code" />
    <input type="submit"/>
</form>

</center>
</body>
</html>
