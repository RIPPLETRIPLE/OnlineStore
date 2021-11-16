<%@include file="/WEB-INF/jsp/includes/standartVariables.jsp" %>
<html>
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
    <div class="container">
        <div class="d-flex justify-content-between card-header my-3">
            <div><fmt:message key="all_products" bundle="${bundle}"/></div>
            <input type="search" id="search" onkeyup="search()" class="form-control rounded search"
                   placeholder="<fmt:message key="search_by_name" bundle="${bundle}"/>"
                   aria-label="Search"
                   aria-describedby="search-addon"/>
        </div>
        <div class="btn-group">
            <button class="btn btn-secondary btn-sm dropdown-toggle" type="button" data-bs-toggle="dropdown"
                    aria-expanded="false">
                <fmt:message key="sort_by" bundle="${bundle}"/>
            </button>
            <ul class="dropdown-menu">
                <li><a class="dropdown-item sortCards asc"
                       href="${url}/mainPage?sortBy=name&order=desc&page=${param.getOrDefault("page", "1")}"><fmt:message
                        key="by_name_high" bundle="${bundle}"/></a></li>
                <li><a class="dropdown-item sortCards asc"
                       href="${url}/mainPage?sortBy=name&order=asc&page=${param.getOrDefault("page", "1")}"><fmt:message
                        key="by_name_low" bundle="${bundle}"/></a></li>
                <li><a class="dropdown-item sortCards asc"
                       href="${url}/mainPage?sortBy=price&order=asc&page=${param.getOrDefault("page", "1")}"><fmt:message
                        key="by_price_low" bundle="${bundle}"/></a></li>
                <li><a class="dropdown-item sortCards"
                       href="${url}/mainPage?sortBy=price&order=desc&page=${param.getOrDefault("page", "1")}"><fmt:message
                        key="by_price_high" bundle="${bundle}"/></a></li>
                <li><a class="dropdown-item sortCards asc"
                       href="${url}/mainPage?sortBy=date&order=asc&page=${param.getOrDefault("page", "1")}"><fmt:message
                        key="by_date_low" bundle="${bundle}"/></a></li>
                <li><a class="dropdown-item sortCards"
                       href="${url}/mainPage?sortBy=date&order=desc&page=${param.getOrDefault("page", "1")}"><fmt:message
                        key="by_date_high" bundle="${bundle}"/></a></li>
            </ul>
        </div>
        <br/>

        <div class="row">
            <c:forEach items="${products}" var="product">
                <div class="col-md-3 my-3">
                    <div class="card w-100">
                        <img class="card-img-top" src="/././product-image/${product.img}"
                             alt="Card image cap" style="max-height: 300px;
                                                         min-height: 300px;
                                                         height: auto;
                                                         width: auto;">
                        <div class="card-body">
                            <h1 class="name" hidden>${product.name}</h1>
                            <h1 class="id" hidden>${product.id}</h1>
                            <h5 class="card-title">${product.name}</h5>
                            <h6><fmt:message key="price" bundle="${bundle}"/>:
                                <span class="price">${pageContext.request.getSession(false).getAttribute("lang") == 'ukr' ? 26 * product.price : product.price}</span>
                                <fmt:message key="currency" bundle="${bundle}"/></h6>
                            <div class="mt-3 d-flex justify-content-between">
                                <a class="btn btn-dark"
                                   href="${url}/addToCart?productId=${product.id}"><fmt:message
                                        key="add_to_cart"
                                        bundle="${bundle}"/></a>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
        <nav aria-label="Page navigation example">
            <ul class="pagination justify-content-center">
                <c:forEach items="${requestScope.pages}" var="page">
                    <li class="page-item">
                        <a class="page-link"
                           href="${url}/mainPage?sortBy=${param.getOrDefault("sortBy", "date")}&order=${param.getOrDefault("order", "asc")}&page=${page}">${page}</a>
                    </li>
                </c:forEach>
            </ul>
        </nav>
    </div>
</main>
<script>
    <%@include file="/WEB-INF/js/MainPage.js" %>
</script>
</body>
</html>