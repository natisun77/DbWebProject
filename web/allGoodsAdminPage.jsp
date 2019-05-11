<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 08.05.2019
  Time: 14:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Информация о товарах</title>
</head>
<body>
<table>
    <tr>
        <td>ID</td>
        <td>Название товара</td>
        <td>Описание</td>
        <td>Цена</td>
    </tr>

    <c:forEach items="${goods}" var="good">
        <tr>
            <td><c:out value="${good.id}"/></td>
            <td><c:out value="${good.name}"/></td>
            <td><c:out value="${good.description}"/></td>
            <td><c:out value="${good.price}"/></td>

            <td>
                <form action="${pageContext.request.contextPath}/good" method="get">
                    <input type="hidden" name="id" value="${good.id}"/>
                    <input type="submit" value="Редактировать товар">
                </form>
            </td>

            <td>
                <form action="${pageContext.request.contextPath}/good" method="post">
                    <input type="hidden" name="action" value="delete"/>
                    <input type="hidden" name="id" value="${good.id}"/>
                    <input type="submit" value="Удалить товар">
                </form>
            </td>
        </tr>

    </c:forEach>
</table>

<form action="${pageContext.request.contextPath}/addGood.jsp" method="post">
    <input type="submit" value="Добавить товар"/>
</form>

</body>
</html>
