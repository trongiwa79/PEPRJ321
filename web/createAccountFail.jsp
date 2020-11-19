<%-- 
    Document   : createAccountFail
    Created on : Nov 8, 2020, 8:40:00 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Account</title>
    </head>
    <body>
        <h1>Create New Account</h1>
        <form action="createAccount" method="POST">
            <c:set var="errors" value="${requestScope.CREATEERRORS}"/>
            Username* <input type="text" name="txtUsername" value="${param.txtUsername}" />(6 - 30 chars)<br/>
            <c:if test="${not empty errors.usernameLengthErr}">
                <font color="red">
                    ${errors.usernameLengthErr}
                </font><br/>
            </c:if>
            Password* <input type="password" name="txtPassword" value="" />(6 - 20 chars)<br/>
            <c:if test="${not empty errors.passwordLengthErr}">
                <font color="red">
                    ${errors.passwordLengthErr}
                </font><br/>
            </c:if>
            Confirm* <input type="password" name="txtConfirm" value="" /><br/>
            <c:if test="${not empty errors.confirmNotMatch}">
                <font color="red">
                    ${errors.confirmNotMatch}
                </font><br/>
            </c:if>
            Full name* <input type="text" name="txtFullname" value="${param.txtFullname}" />(2 - 50 chars)<br/>
            <c:if test="${not empty errors.fullnameLengthErr}">
                <font color="red">
                    ${errors.fullnameLengthErr}
                </font><br/>
            </c:if>
            <input type="submit" value="Create Account" />
            <input type="reset" value="Reset" />
        </form><br/>
        <c:if test="${not empty errors.usernameIsExisted}}">
            <font color="red">
                ${errors.usernameIsExisted}
            </font>
        </c:if>
    </body>
</html>
