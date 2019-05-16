<%-- Created by IntelliJ IDEA. --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Информация о пользователях</title>
</head>
<body>


<table>
    <tr>
        <td>ID</td>
        <td>Имя</td>
        <td>Имейл</td>
        <td>Пароль</td>
        <td>Роль</td>
        <td>Соль</td>
    </tr>
    <c:forEach items="${users}" var="user">
        <tr>
            <td><c:out value="${user.id}"/></td>
            <td><c:out value="${user.name}"/></td>
            <td><c:out value="${user.email}"/></td>
            <td><c:out value="${user.password}"/></td>
            <td><c:out value="${user.role}"/></td>
            <td><c:out value="${user.salt}"/></td>

            <td>
                <form action="${pageContext.request.contextPath}/user" method="get">
                    <input type="hidden" name="id" value="${user.id}"/>
                    <input type="submit" value="Редактировать пользователя">
                </form>
            </td>

            <td>
                <form action="${pageContext.request.contextPath}/user" method="post">
                    <input type="hidden" name="action" value="delete"/>
                    <input type="hidden" name="id" value="${user.id}"/>
                    <input type="submit" value="Удалить пользователя">
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

<form action="${pageContext.request.contextPath}/addUser.jsp" method="get">
    <input type="submit" value="Добавить пользователя"/>
</form>

<form action="${pageContext.request.contextPath}/adminGoods" method="get">
    <input type="submit" value="Товары"/>
</form>

</body>
</html>