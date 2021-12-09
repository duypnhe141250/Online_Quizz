/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.AccountDAO;
import dao.AnswerDAO;
import dao.QuestionDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modal.Account;
import modal.Answer;
import modal.Question;

/**
 *
 * @author Dell Inc
 */
public class MakeQuiz extends BaseAuthentication {

    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Account account = (Account) request.getSession().getAttribute("account");
        AccountDAO ac = new AccountDAO();
        try {
            if (ac.checkTeacherAccount(account.getUsername()).equals("Teacher")) {
                request.getRequestDispatcher("makeQuiz.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Student cant access here");
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        } catch (Exception ex) {
            Logger.getLogger(MakeQuiz.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            QuestionDAO questiondao = new QuestionDAO();
            AnswerDAO answerdao = new AnswerDAO();

            String question = request.getParameter("question");
            String option1 = request.getParameter("option1");
            String option2 = request.getParameter("option2");
            String option3 = request.getParameter("option3");
            String option4 = request.getParameter("option4");

            String cbOption1 = request.getParameter("cbOption1");
            String cbOption2 = request.getParameter("cbOption2");
            String cbOption3 = request.getParameter("cbOption3");
            String cbOption4 = request.getParameter("cbOption4");
            //the user needs to enter question
            if (question == null || question.length() == 0) {
                request.setAttribute("result", "You have to enter question");
                request.getRequestDispatcher("makeQuiz.jsp").forward(request, response);
            }
            //the user needs to enter all options
            if ((option1 == null || option1.length() == 0)
                    || (option2 == null || option2.length() == 0)
                    || (option3 == null || option3.length() == 0)
                    || (option4 == null || option4.length() == 0)) {
                request.setAttribute("result", "You have to enter all options");
                request.getRequestDispatcher("makeQuiz.jsp").forward(request, response);
            }
            //checkBox must check at least 1 times and maximum is 3 times
            if (checkQualityOfCheckBox(cbOption1, cbOption2, cbOption3, cbOption4)) {
                long millis = System.currentTimeMillis();
                Date date = new Date(millis);
                Account account = (Account) request.getSession().getAttribute("account");
                Question q = new Question();
//                q.setContent(question);
//                q.setDate(date);
//                q.setUsername(account.getUsername());
                questiondao.insertQuestion(account.getUsername(), question, date);
                int qid = questiondao.getLatestQid();
                ArrayList<Answer> answers = new ArrayList<>();
                answers.add(new Answer(qid, option1, isCheck(cbOption1)));
                answers.add(new Answer(qid, option2, isCheck(cbOption2)));
                answers.add(new Answer(qid, option3, isCheck(cbOption3)));
                answers.add(new Answer(qid, option4, isCheck(cbOption4)));
                //loop for add answers for question
                for (Answer answer : answers) {
                    answerdao.insertAnswer(answer);
                }
                response.sendRedirect("manageQuiz");
            } else {
                request.setAttribute("cbOption1", cbOption1);
                request.setAttribute("cbOption2", cbOption2);
                request.setAttribute("cbOption3", cbOption3);
                request.setAttribute("cbOption4", cbOption4);
                request.setAttribute("result", "You have to check at least 1 option and maximum 3 options");
                request.getRequestDispatcher("makeQuiz.jsp").forward(request, response);
            }
        } catch (Exception ex) {
            request.setAttribute("error", "Sorry! There is an error now");
            request.getRequestDispatcher("error.jsp").forward(request, response);
// response.setContentType("text/html;charset=UTF-8");
//        try (PrintWriter out = response.getWriter()) {
//            Account account = (Account) request.getSession().getAttribute("account");
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet AddCategoryServlet</title>");
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet AddCategoryServlet at " + ex.getMessage()+ "</h1>");
//            out.println("</body>");
//            out.println("</html>");
//        }

        }
    }

    public boolean isCheck(String checkBox) {
        //checkBox is null then checkBox do not checked
        if (checkBox == null) {
            return false;
        } else {
            return checkBox.equals("true");
        }
    }

    public boolean checkQualityOfCheckBox(String cb1, String cb2, String cb3, String cb4) {
        int number = 0;
        //check that checkBox 1 is checked or not
        if (isCheck(cb1)) {
            ++number;
        }
        //check that checkBox 2 is checked or not
        if (isCheck(cb2)) {
            ++number;
        }
        //check that checkBox 3 is checked or not
        if (isCheck(cb3)) {
            ++number;
        }
        //check that checkBox 4 is checked or not
        if (isCheck(cb4)) {
            ++number;
        }
        return number >= 1 && number <= 3;
    }
}
