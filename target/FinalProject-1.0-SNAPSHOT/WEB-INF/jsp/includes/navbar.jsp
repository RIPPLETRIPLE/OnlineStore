<c:set var="role" value="${sessionScope.user}"/>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container">
        <a class="navbar-brand" href="/app/mainPage"><fmt:message key="online_shop" bundle="${bundle}"/></a>
        <button class="navbar-toggler" type="button" data-toggle="collapse"
                data-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false"
                aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/app/cartPage"><fmt:message key="cart"
                                                                                        bundle="${bundle}"/></a></li>

                <c:if test="${not empty role}">
                    <li class="nav-item"><a class="nav-link" href="orders.jsp"><fmt:message key="orders"
                                                                                            bundle="${bundle}"/></a>
                    </li>
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/app/logout"><fmt:message key="logout"
                                                                                             bundle="${bundle}"/></a>
                    </li>
                </c:if>
                <c:if test="${empty role}">
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/app/login"><fmt:message key="sign_in"
                                                                                            bundle="${bundle}"/></a>
                    </li>
                </c:if>
            </ul>
        </div>
        <div class="localizationBtn">
            <a class="link-primary" href="${EnLang}">EN</a>
            <a class="link-primary" href="${UkrLang}">UKR</a>
        </div>

    </div>
</nav>

