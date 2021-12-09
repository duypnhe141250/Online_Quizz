package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modal.Account;
import modal.Feature;

/**
 *
 * @author Dell Inc
 */
public abstract class BaseAuthentication extends HttpServlet {

    private boolean checkAuthentication(HttpServletRequest request) {
        Account account = (Account) request.getSession().getAttribute("account");
//        String url = request.getServletPath();
//        for (Feature feature : account.getGroup().getFeatures()) {
//            if(feature.getUrl().equals(url)){
//                return true;
//            }
//        }
        if (account != null) {
            return true;
        }
        return false;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (checkAuthentication(request)) {
            processGet(request, response);
        } else {
            response.getWriter().println("Access denied!");
        }
    }

    protected abstract void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (checkAuthentication(request)) {
            processPost(request, response);
        } else {
            response.getWriter().println("Access denied!");
        }
    }

    protected abstract void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;

}
