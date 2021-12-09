
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/base.css" rel="stylesheet" type="text/css"/>
        <link href="css/takeQuiz.css" rel="stylesheet" type="text/css"/>
        <title>Take Quiz</title>
    </head>
    <body>
        <div class="container">
            <%@include file="header.jsp"%>
            <div class="content">
                <c:choose>
                    <c:when test = "${requestScope.result != null}">
                        <p class="mess">${requestScope.result}</p>
                    </c:when>
                    <c:otherwise>
                        <p class="label border">Your score</p>
                        <span class="name">${requestScope.displayScore}</span>
                    </c:otherwise>
                </c:choose>
                <br>
                <form action="takeQuiz" method="GET">
                    <p class="label">Take another test</p>
                    <input class="new_Start" type="submit" value="Start">
                </form>
            </div>
        </div>
    </body>
</html>
