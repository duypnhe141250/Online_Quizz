<%-- 
    Document   : home
    Created on : Jun 9, 2021, 9:47:23 AM
    Author     : Dell Inc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/base.css" rel="stylesheet" type="text/css"/>
        <title>Home</title>
    </head>
    <body>
        <div class="container">
            <%@include file="header.jsp"%>
            <div class="content">
                <p class="label">Welcome</p>
                <span class="name">${sessionScope.account.username}</span>
            </div>
        </div>
    </body>
</html>
