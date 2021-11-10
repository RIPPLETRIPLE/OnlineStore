<%@include file="/WEB-INF/jsp/includes/standartVariables.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
            crossorigin="anonymous"></script>
    <style>
        <%@include file="/WEB-INF/css/cartOrder.css" %>
    </style>
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
        <table class="table table-light table-sortable">
            <thead>
            <tr>
                <th class="columnToSort th-sort-asc" scope="col"><fmt:message key="name" bundle="${bundle}"/></th>
                <th class="columnToSort th-sort-asc" scope="col"><fmt:message key="category" bundle="${bundle}"/></th>
                <th class="columnToSort th-sort-asc" scope="col"><fmt:message key="price" bundle="${bundle}"/></th>
                <th class="columnToSort th-sort-asc" scope="col"><fmt:message key="status" bundle="${bundle}"/></th>
                <th scope="col"></th>
            </tr>
            </thead>
            <tbody>
            <c:set var="firstIndex" value="${(param.getOrDefault('page', 1) - 1) * 5}"/>

            <c:choose>
                <c:when test="${requestScope.orders.size() - firstIndex > 4}"><c:set var="lastIndex"
                                                                                     value="${firstIndex + 4}"/></c:when>
                <c:otherwise>
                    <c:set var="lastIndex" value="${requestScope.orders.size()}"/>
                </c:otherwise>
            </c:choose>

            <c:forEach items="${requestScope.orders}" var="order" begin="${firstIndex}" end="${lastIndex}">
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
                        <c:if test="${order.status == 'Registered'}">
                            <a href="${pageContext.request.contextPath}/app/${role}/cancelRegisteredOrder?action=remove&orderId=${order.id}"
                               class="btn btn-sm btn-danger"><fmt:message key="cancel"
                                                                          bundle="${bundle}"/></a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <nav aria-label="Page navigation example">
            <ul class="pagination justify-content-center">
                <c:set var="i" value="0"/>
                <c:forEach items="${requestScope.orders}" var="order" begin="1">
                    <c:if test="${requestScope.orders.indexOf(order) % 4 == 0}">
                        <li class="page-item "><a class="page-link" aria-disabled="true"
                                                  href="?page=${i = i + 1}">${i}</a>
                        </li>
                </c:if>
                </c:forEach>
                <c:if test="${requestScope.orders.size() % 5 != 0}">
                <li class="page-item"><a class="page-link"
                                         href="?page=${i = i + 1}">${i}</a></c:if>
            </ul>
        </nav>
    </div>
</main>
</body>
<script>
    <%@include file="/WEB-INF/js/CartOrder.js" %>
</script>
</html>
