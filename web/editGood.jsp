<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 08.05.2019
  Time: 16:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Изменение товара</title>
</head>
<body>

<form action="${pageContext.request.contextPath}/good" method="post">
    <input type="hidden" name="action" value="put"/>
    <input type="hidden" name="id" value="${good.id}"/>
    Нзвание <input type="text" name="name" value="${good.name}"/>
    Описание <input type="text" name="description" value="${good.description}"/>
    Цена <input type="text" name="price" value="${good.price}"/>
    <input type="submit">
</form>

</body>
</html>
