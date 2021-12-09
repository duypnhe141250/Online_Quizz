
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
                <p class="label border">Welcome</p>
                <span class="name">${sessionScope.account.username}</span>
                <form action="takeQuiz" method="GET">
                    <p class="label">Enter number of questions:</p>
                    <br>
                    <input class="input" type="text" name="number">
                    <c:if test="${requestScope.result != null}">
                        <p class="mess">${requestScope.result}</p>
                    </c:if>
                    <br>
                    <input class="button_takeQ" type="submit" name="submit" value="Start">
                </form>
            </div>
        </div>
    </body>
</html>
