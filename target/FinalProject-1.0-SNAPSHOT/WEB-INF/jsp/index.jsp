<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
            crossorigin="anonymous"></script>
    <title><fmt:message key="main_page" bundle="${bundle}"/></title>
</head>
<body>

<style>
    <%@include file="/WEB-INF/css/main.css"%>
</style>

<header>
    <%@include file="includes/navbar.jsp" %>
</header>

<main>
    <body>

    <div class="container">
        <div class="card-header my-3"><fmt:message key="all_products" bundle="${bundle}"/></div>
        <div class="row">
            <c:forEach items="${pageContext.request.getAttribute('products')}" var="product">
                <div class="col-md-3 my-3">
                    <div class="card w-100">
                        <img class="card-img-top" src="/././product-image/${product.img}"
                             alt="Card image cap">
                        <div class="card-body">
                            <h5 class="card-title">${product.name}</h5>
                            <h6 class="price"><fmt:message key="price" bundle="${bundle}"/>:
                             ${pageContext.request.getSession(false).getAttribute("lang") == 'ukr' ? 26 * product.price : product.price}
                             <fmt:message key="currency" bundle="${bundle}"/></h6>
                            <div class="mt-3 d-flex justify-content-between">
                                <a class="btn btn-dark" href="${pageContext.request.contextPath}/app/addToCart?productId=${product.id}"><fmt:message key="add_to_cart"
                                                                                            bundle="${bundle}"/></a> <a
                                    class="btn btn-primary" href="${pageContext.request.contextPath}/app/orderNow?quantity=1&id="><fmt:message key="buy_now"
                                                                                                         bundle="${bundle}"/></a>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
    </body>
</main>
</body>
</html>