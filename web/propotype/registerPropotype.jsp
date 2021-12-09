<%-- 
    Document   : register
    Created on : Jun 12, 2021, 3:13:26 PM
    Author     : Dell Inc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="../css/base.css" rel="stylesheet" type="text/css"/>
        <title>Register</title>
    </head>
    <body>
        <div class="container">
            <%@include file="../header.jsp"%>
            <div class="content">
                <p class="text">Registration Form</p>
                <form action="register" method="POST">
                    <p class="label">User Name:</p>
                    <input class="border margin_username" type="text" name="username" value="${param.username}">
                    <br>
                    <p class="label">Password:</p>
                    <input class="border margin_password" type="password" name="password" value="${param.password}">
                    <br>
                    <p class="label">User Type:</p>
                    <select class="comboxBox" name="type">
                        <option value="1" 
                                <c:if test="${requestScope.gid == null || requestScope.gid == 1}">
                                    selected="selected"
                                </c:if>
                                >Teacher</option>
                        <option value="2"
                                <c:if test="${requestScope.gid == 2}">
                                    selected="selected"
                                </c:if>
                                >Student</option>
                    </select>
                    <br>
                    <p class="label">Email:</p>
                    <input class="border margin_email" type="text" name="email" value="${param.email}">
                    <br>
                    <c:if test="${requestScope.message != null}">
                        <p class="mess">${requestScope.message}</p>
                    </c:if>
                    <input type="submit" class="button margin_register" value="Register">
                </form>
            </div>
        </div>
    </body>
</html>
