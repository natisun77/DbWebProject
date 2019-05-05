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
    <title>ShowInformation</title>
</head>
<body>

<c:set var="user" value='${requestScope["user"]}' />

Вы успешно зашли в систему, <c:out value="${user.name}" /> !


</body>
</html>
