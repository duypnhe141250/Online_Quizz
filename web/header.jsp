<%-- 
    Document   : header
    Created on : Jun 9, 2021, 9:58:38 AM
    Author     : Dell Inc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <div class="top_box"></div>
        <div class="menu">
            <ul>
                <li><a href="home">Home</a></li>
                <li><a href="takeQuiz">Take Quiz</a></li>
                <li><a href="makeQuiz">Make Quiz</a></li>
                <li><a href="manageQuiz">Manage Quiz</a></li>
                <c:if test="${sessionScope.account != null}">
                    <li><a href="logout">Log out</a></li>
                </c:if>
            </ul>
        </div>
    </body>
</html>
