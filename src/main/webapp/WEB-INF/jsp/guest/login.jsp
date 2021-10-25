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
    <title><fmt:message key="sign_in" bundle="${bundle}"/></title>
</head>
<body>

<header>
    <a class="link-primary margin_right_5px" href="${EnLang}">EN</a>
    <a class="link-primary" href="${UkrLang}">UKR</a>
</header>

<main class="form-signin">

    <style>
        <%@include file="/WEB-INF/css/style.css"%>
    </style>

    <form autocomplete="off" method="post" action="${pageContext.request.contextPath}/app/login" class="text-center">
        <h1 class="h3 mb-3 fw-normal"><fmt:message key="sign_in" bundle="${bundle}"/></h1>

        <div class="form-floating">
            <input type="text" class="form-control" id="floatingInput" name="login" required
                   placeholder="<fmt:message key="login" bundle="${bundle}"/>">
            <label for="floatingInput"><fmt:message key="login" bundle="${bundle}"/></label>
        </div>
        <div class="form-floating">
            <input type="password" class="form-control" id="floatingPassword" name="password" required
                   placeholder="<fmt:message key="password" bundle="${bundle}"/>">
            <label for="floatingPassword"><fmt:message key="password" bundle="${bundle}"/></label>
        </div>

        <button class="w-100 btn btn-lg btn-primary" type="submit"><fmt:message key="sign_in"
                                                                                bundle="${bundle}"/></button>
        <a href="${pageContext.request.contextPath}/app/registration"><fmt:message key="sign_up"
                                                                                   bundle="${bundle}"/></a>

        <c:if test="${sessionScope.error}">
            ${sessionScope.remove("error")}
            <div class="modal fade" tabindex="-1" id="myModal">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel"><fmt:message key="error"
                                                                                        bundle="${bundle}"/></h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <c:if test="${sessionScope.userAlreadyLogged}">
                                ${sessionScope.remove("userAlreadyLogged")}
                                <fmt:message key="user_already_logged" bundle="${bundle}"/>
                            </c:if>

                            <c:if test="${sessionScope.wrongData}">
                                ${sessionScope.remove("wrongData")}
                                <fmt:message key="wrong_data" bundle="${bundle}"/>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </c:if>
    </form>
</main>
<script>
    function showModal() {
        let myModal = new bootstrap.Modal(document.getElementById('myModal'), {});
        myModal.toggle()
    }

    window.onload = showModal;
</script>
</body>
</html>