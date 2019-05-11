<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 09.05.2019
  Time: 11:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Добавление товара</title>
</head>
<body>

<form action="${pageContext.request.contextPath}/good" method="post">
    Название <input type="text" name="name"/>
    Описание <input type="text" name="description"/>
    Цена <input type="text" name="price"/>
    <input type="submit">
</form>

</body>
</html>
