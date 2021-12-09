<%-- 
    Document   : login
    Created on : Jun 9, 2021, 9:47:35 AM
    Author     : Dell Inc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/base.css" rel="stylesheet" type="text/css"/>
        <title>Login</title>
    </head>
    <body>
        <div class="container">
            <%@include file="header.jsp"%>
            <div class="content">
                <p class="text">Login Form</p>
                <form action="home" method="POST">
                    <p class="label">User Name:</p>
                    <input class="width_input" type="text" name="username">
                    <br>
                    <p class="label">Password:</p>
                    <input class="width_input margin_left" type="password" name="password">
                    <br>
                    <c:if test="${requestScope.message != null}">
                        <p class="mess">${requestScope.message}</p>
                    </c:if>
                    <input type="submit" class="button" value="Sign in">
                    <a class="register" href="register" >Register</a>
                </form>

            </div>
        </div>
    </body>
</html>
