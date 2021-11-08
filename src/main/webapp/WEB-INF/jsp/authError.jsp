<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<fmt:setLocale value='${pageContext.request.getSession(false).getAttribute("lang")}'/>
<fmt:setBundle basename="translate" var="bundle"/>
<c:url value="" var="EnLang">
    <c:param name="lang" value="en"/>
</c:url>

<c:url value="" var="UkrLang">
    <c:param name="lang" value="ukr"/>
</c:url>
<c:set var="role" value="${sessionScope.user}"/>
<head>
    <title><fmt:message key="error" bundle="${bundle}"/></title>
</head>
<body>
<h1><fmt:message key="rights_error" bundle="${bundle}"/></h1>
<a href="${pageContext.request.contextPath}/app/user/logout"><fmt:message key="main_page" bundle="${bundle}"/></a>
</body>
</html>
