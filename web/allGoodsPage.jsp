<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 30.04.2019
  Time: 23:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Товары для покупки</title>
</head>
<body>

<table>
    <tr>
        <td>ID</td>
        <td>Название товара</td>
        <td>Описание</td>
        <td>Цена</td>
        <td>Купить!</td>
    </tr>

    <c:forEach items="${goods}" var="good">
        <tr>
            <td><c:out value="${good.id}"/></td>

            <td><c:out value="${good.name}"/></td>

            <td><c:out value="${good.description}"/></td>

            <td><c:out value="${good.price}"/></td>

            <td><a href='buy?id=${good.id}'>Купить!</a></td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
