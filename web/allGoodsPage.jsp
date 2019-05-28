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

<c:set var="message" value='${requestScope["message"]}'/>
<c:if test="${not empty message}"><p>${message}</p></c:if>

<table>
    <tr>
        <table>
            <tr>
                <td>ID</td>
                <td>Название товара</td>
                <td>Описание</td>
                <td>Цена</td>
                <td>Покупка</td>
            </tr>

            <c:forEach items="${goods}" var="good">
                <tr>
                    <td><c:out value="${good.id}"/></td>

                    <td><c:out value="${good.name}"/></td>

                    <td><c:out value="${good.description}"/></td>

                    <td><c:out value="${good.price}"/></td>


                    <td>
                        <form action="${pageContext.request.contextPath}/chooseGood" method="post">
                            <input type="hidden" name="id" value="${good.id}"/>
                            <input type="submit" value="В корзину"/>
                        </form>
                    </td>
                </tr>
            </c:forEach>

        </table>
    </tr>
    <tr>
        <table>
            <tr>
                <td>ID</td>
                <td>Название товара</td>
                <td>Цена</td>
            </tr>

            <c:forEach items="${cart}" var="good">
                <tr>
                    <td><c:out value="${good.id}"/></td>

                    <td><c:out value="${good.name}"/></td>

                    <td><c:out value="${good.price}"/></td>
                </tr>
            </c:forEach>

        </table>
        <form action="${pageContext.request.contextPath}/buy" method="get">
            <input type="submit" value="Купить"/>
        </form>
    </tr>
</table>

</body>
</html>
