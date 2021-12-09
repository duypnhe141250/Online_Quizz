/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.AccountDAO;
import dao.QuestionDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modal.Account;
import modal.Question;

/**
 *
 * @author Dell Inc
 */
public class ManageQuiz extends BaseAuthentication {

    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Account account = (Account) request.getSession().getAttribute("account");
        AccountDAO ac = new AccountDAO();
        try {
            if (ac.checkTeacherAccount(account.getUsername()).equals("Teacher")) {
                QuestionDAO questiondao = new QuestionDAO();
                String p_size = getServletContext().getInitParameter("pageSize");
                int pagesize = Integer.parseInt(p_size);
                //get total question
                int totalQuestion = questiondao.countQuestion();
                // get total page
                int totalPage = (totalQuestion % pagesize == 0) ? (totalQuestion / pagesize)
                        : (totalQuestion / pagesize) + 1;
                ArrayList<Question> questions = new ArrayList<>();
                String p_index = request.getParameter("pageindex");
                int pageindex = 0;
                //set p_index for the first access
                if (p_index == null) {
                    p_index = "1";
                }
                //handle when pageindex not number
                try {
                    pageindex = Integer.parseInt(p_index);
                } catch (Exception ex) {
                    request.setAttribute("result", "Pageindex is number!");
                    request.getRequestDispatcher("manageQuiz.jsp").forward(request, response);
                }

                //handle when pageindex out of size
                if (pageindex > totalPage || pageindex < 1) {
                    request.setAttribute("result", "Pageindex out of size!");
                } else {
                    questions = questiondao.getListQuestions(pageindex, pagesize);
                }
                request.setAttribute("pageindex", pageindex);
                request.setAttribute("totalQuestion", totalQuestion);
                request.setAttribute("totalPage", totalPage);
                request.setAttribute("questions", questions);
                request.getRequestDispatcher("manageQuiz.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Student cant access here");
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }

        } catch (Exception ex) {
            request.setAttribute("error", "Sorry! There is an error now");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}
