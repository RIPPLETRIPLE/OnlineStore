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
        <div class="d-flex py-3"><h3 id="totalPrice"><fmt:message key="total_price" bundle="${bundle}"/>:
            <fmt:message key="currency" bundle="${bundle}"/></h3></div>
        <table class="table table-light">
            <thead>
            <tr>
                <th scope="col"><fmt:message key="name" bundle="${bundle}"/></th>
                <th scope="col"><fmt:message key="category" bundle="${bundle}"/></th>
                <th scope="col"><fmt:message key="price" bundle="${bundle}"/></th>
                <th scope="col"><fmt:message key="status" bundle="${bundle}"/></th>
                <th scope="col"></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${requestScope.orders}" var="order">
                <tr>
                    <td>
                            ${order.product.name}
                    </td>
                    <td>
                        Category
                    </td>
                    <td class="price">
                            ${pageContext.request.getSession(false).getAttribute("lang") == 'ukr' ? 26 * order.product.price * order.quantity : order.product.price * order.quantity}
                        <fmt:message key="currency" bundle="${bundle}"/>
                    </td>
                    <td>
                        ${order.status}
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/app/changeProductQuantity?action=remove&productId=${order.product.id}"
                           class="btn btn-sm btn-danger"><fmt:message key="remove"
                                                                      bundle="${bundle}"/></a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</main>
</body>
<script>
    function totalPrice() {
        let prices = document.getElementsByClassName('price');
        let sum = 0;

        for (let i = 0; i < prices.length; i++) {
            sum += parseInt(prices.item(i).innerHTML);
        }
        return sum.toString();
    }

    document.getElementById("totalPrice").innerText = document.getElementById("totalPrice").innerText.replace(': ', ': ' + totalPrice());
</script>
</html>
