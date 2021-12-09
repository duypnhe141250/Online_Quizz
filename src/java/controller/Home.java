/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.AccountDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modal.Account;

/**
 *
 * @author Dell Inc
 */
public class Home extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Account account = (Account) request.getSession().getAttribute("account");
        
        //user must be login if not
        if (account == null) {
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("home.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            //users must not be blank
            if (username.isEmpty() || password.isEmpty()) {
                request.setAttribute("message", "Users must not be blank!");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
            AccountDAO accountdao = new AccountDAO();
            Account account = accountdao.getAccount(username, password);
            //user must enter the correct username and password
            if (account != null) {
                request.getSession().setAttribute("account", account);
                request.getRequestDispatcher("home.jsp").forward(request, response);
            } else {
                request.setAttribute("message", "Wrong username or password");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (Exception ex) {
            request.setAttribute("error", "Sorry! There is an error now");
            request.getRequestDispatcher("error.jsp").forward(request, response);

          
        }
    }

}
