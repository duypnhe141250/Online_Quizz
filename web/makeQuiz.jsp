

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/base.css" rel="stylesheet" type="text/css"/>
        <link href="css/makeQuiz.css" rel="stylesheet" type="text/css"/>
        <title>Make Quiz</title>
    </head>
    <body>
        <div class="container">
            <%@include file="header.jsp"%>
            <div class="content">
                <form action="makeQuiz" method="POST">
                    <div class="item_question">
                        <p class="label">Question:</p>
                        <textarea class="question" name="question">${param.question}</textarea>
                    </div>
                    <div class="item">
                    <p class="label">Option1:</p>
                    <textarea class="option" name="option1">${param.option1}</textarea>
                    </div>
                    <div class="item">
                    <p class="label">Option2:</p>
                    <textarea class="option" name="option2">${param.option2}</textarea>
                    </div>
                    <div class="item">
                    <p class="label">Option3:</p>
                    <textarea class="option" name="option3">${param.option3}</textarea>
                    </div>
                    <div class="item">
                    <p class="label">Option4:</p>
                    <textarea class="option" name="option4">${param.option4}</textarea>
                    </div>
                    <div class="item">
                    <p class="label">Answer(s):</p>
                    <input class="checkbox" type="checkbox" name="cbOption1" value="true"
                           ${requestScope.cbOption1==null?"":"checked=\"checked\""}
                           >
                    <p class="cbText">Option 1</p>
                    <input class="checkbox" type="checkbox" name="cbOption2" value="true"
                           ${requestScope.cbOption2==null?"":"checked=\"checked\""}
                           >
                    <p class="cbText">Option 2</p>
                    <input class="checkbox" type="checkbox" name="cbOption3" value="true"
                           ${requestScope.cbOption3==null?"":"checked=\"checked\""}
                           >
                    <p class="cbText">Option 3</p>
                    <input class="checkbox" type="checkbox" name="cbOption4" value="true"
                           ${requestScope.cbOption4==null?"":"checked=\"checked\""}
                           >
                    <p class="cbText">Option 4</p>
                    </div>
                    <c:if test="${requestScope.result != null}">
                        <p class="mess">${requestScope.result}</p>
                    </c:if>
                    <input class="save" type="submit" value="Save">
                </form>
            </div>
        </div>
    </body>
</html>
