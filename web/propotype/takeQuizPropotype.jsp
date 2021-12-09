

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="../css/base.css" rel="stylesheet" type="text/css"/>
        <link href="../css/takeQuiz.css" rel="stylesheet" type="text/css"/>
        <title>Take Quiz</title>
    </head>
    <body>
        <div class="container">
            <%@include file="../header.jsp"%>
            <div class="content">
                <p class="label margin_take">Welcome</p>
                <span class="name">A</span>
                <div class="timer">
                    <p class="label">Time remaining</p>
                    <span id="time">1:00</span>
                </div>
                <div id="qa_container">

                    <div class="take_question none">
                        <p class="label_Question">Where is Tokyo?</p>

                        <input class="option" id="${a.aid}" type="checkbox"
                               name="${q.qid}"
                               value="${a.aid}"/>
                        <label class="a_label" for="${a.aid}">Japan</label>
                        <input class="option" id="${a.aid}" type="checkbox"
                               name="${q.qid}"
                               value="${a.aid}"/>
                        <label class="a_label" for="${a.aid}">USA</label>
                        <input class="option" id="${a.aid}" type="checkbox"
                               name="${q.qid}"
                               value="${a.aid}"/>
                        <label class="a_label" for="${a.aid}">VietNam</label>
                        <input class="option" id="${a.aid}" type="checkbox"
                               name="${q.qid}"
                               value="${a.aid}"/>
                        <label class="a_label" for="${a.aid}">China</label>
                        <br>

                    </div>


                    <button class="next_question new_Start" id="nextBtn">Next</button>   
                </div>
            </div>
            <form id="result_form" type="hidden" action="takeQuiz" method="POST">
                <input id="result" name="result" type="hidden"/>
            </form>
        </div>
        <script src="../js/takeQuiz.js" type="text/javascript"></script>
    </body>
</html>
