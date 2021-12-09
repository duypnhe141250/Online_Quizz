/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.AnswerDAO;
import dao.QuestionDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modal.Answer;

/**
 *
 * @author Dell Inc
 */
public class DeleteQuiz extends BaseAuthentication {

    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            int qid = Integer.parseInt(request.getParameter("qid"));
            QuestionDAO questiondao = new QuestionDAO();
            AnswerDAO answerdao = new AnswerDAO();
            ArrayList<Answer> answers = answerdao.getAnswers(qid);
            //loop delete the answer corresponding to the question
            for (Answer answer : answers) {
                answerdao.deleteAnswer(answer.getAid());
            }
            questiondao.deleteQuestion(qid);
            response.sendRedirect("manageQuiz");
        }catch(Exception ex){
            request.setAttribute("error", "Sorry! There is an error now");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    

}
