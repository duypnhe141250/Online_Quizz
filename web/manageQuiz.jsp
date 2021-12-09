<%-- 
    Document   : manageQuiz
    Created on : Jun 14, 2021, 9:54:27 AM
    Author     : Dell Inc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/base.css" rel="stylesheet" type="text/css"/>
        <link href="css/manageQuiz.css" rel="stylesheet" type="text/css"/>
        <script src="js/manageQuiz.js" type="text/javascript"></script>
        <title>Manage Quiz</title>
    </head>
    <body>
        <div class="container">
            <%@include file="header.jsp"%>
            <div class="content">
                <div class="form_number">
                    <p class="label">Number of questions: </p>
                    <span class="name">${requestScope.totalQuestion}</span>
                </div>
                <c:choose>
                    <c:when test="${requestScope.result != null}">
                        <p class="error">${requestScope.result}</p>
                    </c:when>
                    <c:otherwise>
                        <ul class="form_item">
                            <li><p class="column">Question</p></li>
                            <li><p class="column margin_Question">DateCreated</p></li>
                        </ul>
                        <c:forEach items="${requestScope.questions}" var="q">
                            <ul class="form_content">
                                <li class="left"><p class="q_content">${q.content}</p></li>
                                <li class="right">
                                    <p class="q_content">${q.getDateString()}</p>
                                    <a href="#" onclick="mess(${q.qid})">Delete</a>
                                </li>

                            </ul>
                        </c:forEach>
                        <div id="botpager" class="pager"></div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </body>
    <script>
        rederPager("botpager",${requestScope.pageindex},${requestScope.totalPage}, 2);
    </script>
</html>
