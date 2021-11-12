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
        <%@include file="/WEB-INF/css/sortTable.css" %>
    </style>
    <title><fmt:message key="sign_in" bundle="${bundle}"/></title>
</head>
<body>
<header>
    <%@include file="/WEB-INF/jsp/includes/navbar.jsp" %>
</header>
<main>
    <div class="container my-3">
        <div class="d-flex justify-content-end">
            <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#myModal">
                <fmt:message key="add"
                             bundle="${bundle}"/>
            </button>
        </div>

        <table class="table table-light table-sortable">
            <thead>
            <tr>
                <th class="columnToSort th-sort-asc" scope="col"><fmt:message key="name" bundle="${bundle}"/></th>
                <th class="columnToSort th-sort-asc" scope="col"><fmt:message key="category" bundle="${bundle}"/></th>
                <th class="columnToSort th-sort-asc" scope="col"><fmt:message key="price" bundle="${bundle}"/></th>
                <th class="columnToSort th-sort-asc" scope="col"><fmt:message key="size" bundle="${bundle}"/></th>
                <th class="columnToSort th-sort-asc" scope="col"><fmt:message key="color" bundle="${bundle}"/></th>
                <th class="columnToSort th-sort-asc" scope="col"><fmt:message key="sex" bundle="${bundle}"/></th>
                <th class="col"></th>
            </tr>
            </thead>
            <tbody>
            <c:set var="firstIndex" value="${(param.getOrDefault('page', 1) - 1) * 5}"/>

            <c:choose>
                <c:when test="${requestScope.products.size() - firstIndex > 4}"><c:set var="lastIndex"
                                                                                       value="${firstIndex + 4}"/></c:when>
                <c:otherwise>
                    <c:set var="lastIndex" value="${requestScope.products.size()}"/>
                </c:otherwise>
            </c:choose>

            <c:forEach items="${requestScope.products}" var="product" begin="${firstIndex}" end="${lastIndex}">
                <tr>
                    <td>
                            ${product.name}
                    </td>
                    <td>
                            ${product.category.name}
                    </td>
                    <td class="price">
                            ${pageContext.request.getSession(false).getAttribute("lang") == 'ukr' ? 26 * product.price : product.price}
                        <fmt:message key="currency" bundle="${bundle}"/>
                    </td>
                    <td>
                            ${product.size.size}
                    </td>
                    <td>
                            ${product.color.color}
                    </td>
                    <td>
                            ${product.sex.toString()}
                    </td>
                    <td>
                        <a href="${url}/deleteProduct?productId=${product.id}"
                           class="btn btn-sm btn-danger"><fmt:message key="delete"
                                                                      bundle="${bundle}"/></a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <nav aria-label="Page navigation example">
            <ul class="pagination justify-content-center">
                <c:set var="i" value="0"/>
                <c:forEach items="${requestScope.products}" var="product" begin="1">
                    <c:if test="${requestScope.products.indexOf(product) % 4 == 0}">
                        <li class="page-item "><a class="page-link" aria-disabled="true"
                                                  href="?page=${i = i + 1}">${i}</a>
                        </li>
                    </c:if>
                </c:forEach>
                <c:if test="${requestScope.products.size() % 5 != 0}">
                <li class="page-item"><a class="page-link"
                                         href="?page=${i = i + 1}">${i}</a></c:if>
            </ul>
        </nav>
    </div>
    <div class="modal fade" tabindex="-1" id="myModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel"><fmt:message key="add"
                                                                                bundle="${bundle}"/></h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form action="${url}/addProduct" method="post" enctype="multipart/form-data" autocomplete="off">
                        <select class="form-select" name="category" required>
                            <option disabled selected hidden><fmt:message key="category" bundle="${bundle}"/></option>
                            <c:forEach items="${requestScope.categories}" var="category">
                                <option value="${category.id}">${category.name}</option>
                            </c:forEach>
                        </select>
                        <br/>
                        <select class="form-select" name="color" required>
                            <option disabled selected hidden><fmt:message key="color" bundle="${bundle}"/></option>
                            <c:forEach items="${requestScope.colors}" var="color">
                                <option value="${color.id}">${color.color}</option>
                            </c:forEach>
                        </select>
                        <br/>
                        <select class="form-select" name="size" required>
                            <option disabled selected hidden><fmt:message key="size" bundle="${bundle}"/></option>
                            <c:forEach items="${requestScope.sizes}" var="size">
                                <option value="${size.id}">${size.size}</option>
                            </c:forEach>
                        </select>
                        <br/>
                        <select class="form-select" name="size" required>
                            <option disabled selected hidden><fmt:message key="sex" bundle="${bundle}"/></option>
                            <option value="Male">Male</option>
                            <option value="Female">Female</option>
                            <option value="Unisex">Unisex</option>
                        </select>
                        <br/>
                        <div class="input-group">
                            <input class="form-control" type="text" name="price" pattern="\d+" required
                                   placeholder="<fmt:message key="price" bundle="${bundle}"/>">
                        </div>
                        <br/>
                        <div class="input-group">
                            <input class="form-control" type="text" name="name" required
                                   placeholder="<fmt:message key="name" bundle="${bundle}"/>">
                        </div>
                        <br/>
                        <div class="input-group">
                            <input class="form-control" type="file" name="image"
                                   accept="image/x-png,image/gif,image/jpeg" required>
                        </div>
                        <button class="w-100 btn btn-lg btn-primary" type="submit"><fmt:message key="accept"
                                                                                                bundle="${bundle}"/></button>

                    </form>
                </div>
            </div>
        </div>
    </div>
</main>
</body>
<script>
    <%@include file="/WEB-INF/js/ManageProducts.js" %>
    <%@include file="/WEB-INF/js/TableScripts.js" %>
</script>
</html>
