<%@include file="/WEB-INF/jsp/includes/standartVariables.jsp" %>
<html>
<head>
    <title><fmt:message key="error" bundle="${bundle}"/></title>
</head>
<body>
<h1><fmt:message key="block_error" bundle="${bundle}"/></h1>
<a href="${url}/logout"><fmt:message key="logout" bundle="${bundle}"/></a>
</body>
</html>

