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
        <%@include file="/WEB-INF/css/table.css" %>
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
                <th scope="col"></th>
                <th scope="col"></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${cart}" var="order">
                <tr>
                    <td>
                            ${order.product.name}
                    </td>
                    <td>
                            ${order.product.category.name}
                    </td>
                    <td class="price">
                            ${pageContext.request.getSession(false).getAttribute("lang") == 'ukr' ? 26 * order.product.price * order.quantity : order.product.price * order.quantity}
                        <fmt:message key="currency" bundle="${bundle}"/>
                    </td>
                    <td>
                        <form action="${pageContext.request.contextPath}/app/${role}/order" method="post"
                              class="form-inline">
                            <input type="hidden" name="id" value="${order.product.id}" class="form-input">
                            <div class="form-group d-flex justify-content-between">
                                <a class="quantity-right-plus btn btn-success btn-number" data-type="plus" data-field=""
                                   href="${pageContext.request.contextPath}/app/${role}/changeProductQuantity?action=increment&productId=${order.product.id}">+</a>
                                <input type="text" name="quantity" class="form-control" value="${order.quantity}"
                                       readonly>
                                <a class="quantity-left-minus btn btn-danger btn-numbe" data-type="minus" data-field=""
                                   href="${pageContext.request.contextPath}/app/${role}/changeProductQuantity?action=decrement&productId=${order.product.id}">-</a>
                            </div>
                        </form>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/app/${role}/changeProductQuantity?action=remove&productId=${order.product.id}"
                           class="btn btn-sm btn-danger"><fmt:message key="delete"
                                                                      bundle="${bundle}"/></a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <c:if test="${not empty cart}">
            <div class="d-flex justify-content-end"><a href="${pageContext.request.contextPath}/app/${role}/buyFromCart"
                                                       class="btn btn-primary"><fmt:message key="buy"
                                                                                            bundle="${bundle}"/></a>
            </div>
        </c:if>
    </div>
</main>
</body>
<script>
    <%@include file="/WEB-INF/js/TableScripts.js" %>
</script>
</html>