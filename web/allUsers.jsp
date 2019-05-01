<%-- Created by IntelliJ IDEA. --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<c:choose>
    <c:when test="${not empty param.deleted && param.deleted}">Вы успешно удалили пользователя.</c:when>
    <c:when test="${not empty param.deleted && !param.deleted}">Во время удаления произошла ошибка, повторите позже.</c:when>
    <c:when test="${not empty param.updated && param.updated}">Вы успешно отредактировали пользователя.</c:when>
    <c:when test="${not empty param.updated && !param.updated}">Во время редактирования произошла ошибка, повторите позже.</c:when>
</c:choose>

<table>
    <%--@elvariable id="users" type="java.util.List<com.nataliia.model.User>"--%>
    <c:forEach items="${users}" var="user">
        <tr>
            <td><c:out value="${user.id}"/></td>
            <td><c:out value="${user.name}"/></td>
            <td><c:out value="${user.password}"/></td>

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

</body>
</html>