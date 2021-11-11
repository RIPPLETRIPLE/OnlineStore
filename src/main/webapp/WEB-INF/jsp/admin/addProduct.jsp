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
<header>
    <%@include file="/WEB-INF/jsp/includes/navbar.jsp" %>
</header>
<main>
    <div class="container my-3">
        <div class="d-flex py-3">
            <form action="${url}/addProduct" method="post" enctype="multipart/form-data" autocomplete="off">
                <select name="category" required>
                    <c:forEach items="${requestScope.categories}" var="category">
                        <option value="${category.id}">${category.name}</option>
                    </c:forEach>
                </select>
                <br/>
                <select name="color" required>
                    <c:forEach items="${requestScope.colors}" var="color">
                        <option value="${color.id}">${color.color}</option>
                    </c:forEach>
                </select>
                <br/>
                <select name="size" required>
                    <c:forEach items="${requestScope.sizes}" var="size">
                        <option value="${size.id}">${size.size}</option>
                    </c:forEach>
                </select>
                <br/>
                <select name="size" required>
                    <option value="Male">Male</option>
                    <option value="Female">Female</option>
                    <option value="Unisex">Unisex</option>
                </select>
                <br/>
                <input type="text" name="price" pattern="\d+" required placeholder="<fmt:message key="price" bundle="${bundle}"/>">
                <br/>
                <input type="text" name="name" required placeholder="<fmt:message key="name" bundle="${bundle}"/>">
                <input type="file" name="image" accept="image/x-png,image/gif,image/jpeg" required>
                <br/>
                <button class="w-100 btn btn-lg btn-primary" type="submit"><fmt:message key="accept"
                                                                                        bundle="${bundle}"/></button>

            </form>
        </div>
    </div>
</main>
</body>
</html>
