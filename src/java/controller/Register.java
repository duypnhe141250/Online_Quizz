/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.AccountDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modal.Account;

/**
 *
 * @author Dell Inc
 */
public class Register extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            int gid = Integer.parseInt(request.getParameter("type"));
            //users must not be blank
            if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
                request.setAttribute("gid", gid);
                request.setAttribute("message", "User must not be blank!");
                request.getRequestDispatcher("register.jsp").forward(request, response);
            }

            AccountDAO accountdao = new AccountDAO();
            //check username already exist or not
            if (accountdao.checkUsernameExist(username)) {
                request.setAttribute("gid", gid);
                request.setAttribute("message", "Username has exist!");
                request.getRequestDispatcher("register.jsp").forward(request, response);
            } else {
                Account account = new Account();
                account.setUsername(username);
                account.setPassword(password);
                account.setEmail(email);
                accountdao.insertAccount(account);
                accountdao.insertAccountGroup(gid, username);
                response.sendRedirect("home");
            }
        } catch (Exception ex) {
            request.setAttribute("error", ex);
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}
