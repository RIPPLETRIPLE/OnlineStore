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
<header>
    <%@include file="/WEB-INF/jsp/includes/navbar.jsp" %>
</header>
<main>
    <div class="container my-3">
        <div class="d-flex py-3"><h3><fmt:message key="total_price" bundle="${bundle}"/>: 0
            <fmt:message key="currency" bundle="${bundle}"/> </h3></div>
        <table class="table table-light">
            <thead>
            <tr>
                <th scope="col"><fmt:message key="name" bundle="${bundle}"/></th>
                <th scope="col"><fmt:message key="category" bundle="${bundle}"/></th>
                <th scope="col"><fmt:message key="price" bundle="${bundle}"/></th>
                <th scope="col"><fmt:message key="buy_now" bundle="${bundle}"/></th>
                <th scope="col"><fmt:message key="cancel" bundle="${bundle}"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${sessionScope.get('cart')}" var="cart">
                <tr>
                    <td>
                        ${cart.key.name}
                    </td>
                    <td>
                        Category
                    </td>
                    <td>
                            ${pageContext.request.getSession(false).getAttribute("lang") == 'ukr' ? 26 * cart.key.price : cart.key.price}
                    </td>
                    <td>
                        <form action="${pageContext.request.contextPath}/app/order" method="post" class="form-inline">
                            <input type="hidden" name="id" value="${cart.key.id}" class="form-input">
                            <div class="form-group d-flex justify-content-between">
                                <a class="quantity-right-plus btn btn-success btn-number" data-type="plus" data-field="" href="quantity-inc-dec?action=inc&productId=${cart.key.id}">+</a>
                                <input type="text" name="quantity" class="form-control" value="${cart.value}" readonly>
                                <a class="quantity-left-minus btn btn-danger btn-numbe" data-type="minus" data-field=""  href="quantity-inc-dec?action=dec&productId=${cart.key.id}">-</a>
                            </div>
                            <button type="submit" class="btn btn-primary btn-sm"><fmt:message key="buy" bundle="${bundle}"/></button>
                        </form>
                    </td>
                    <td><a href="remove-from-cart?id=" class="btn btn-sm btn-danger"><fmt:message key="remove" bundle="${bundle}"/></a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</main>
<footer></footer>
</body>
</html>