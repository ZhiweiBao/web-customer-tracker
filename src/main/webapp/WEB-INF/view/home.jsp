<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <title>Company Home Page</title>
</head>
<body>
<h2>Company Home Page</h2>
<hr>
<p>Welcome to the company home page</p>
<hr>
<security:authorize access="isAuthenticated()">
    User: <security:authentication property="principal.username"/><br>
    Role(s): <security:authentication property="principal.authorities"/>
    <hr>
</security:authorize>
<security:authorize access="hasRole('MANAGER')">
    <p>
        <a href="${pageContext.request.contextPath}/leaders">Leadership Meeting</a> (Only for
        Managers)
    </p>
</security:authorize>
<security:authorize access="hasRole('ADMIN')">
    <p>
        <a href="${pageContext.request.contextPath}/systems">IT Systems Meeting</a> (Only for Admin
        peeps)
    </p>
</security:authorize>
<security:authorize access="!isAuthenticated()">
    <form:form action="${pageContext.request.contextPath}/showLoginPage" method="get">
        <input type="submit" value="Login">
    </form:form>
</security:authorize>
<security:authorize access="isAuthenticated()">
    <form:form action="${pageContext.request.contextPath}/logout" method="post">
        <input type="submit" value="Logout">
    </form:form>
</security:authorize>
</body>
</html>
