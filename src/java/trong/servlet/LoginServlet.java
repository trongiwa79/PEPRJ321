/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trong.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import trong.registration.RegistrationDAO;
import trong.registration.RegistrationDTO;

/**
 *
 * @author DELL
 */
public class LoginServlet extends HttpServlet {
    private final String INVALID_PAGE = "invalidPage";
    private final String SEARCH_PAGE = "searchPage";
    private final String SHOPPING_PAGE = "shoppingPage";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String url = INVALID_PAGE;
        
        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");
        
        try {
            RegistrationDAO dao = new RegistrationDAO();
            dao.checkLogin(username, password);
            RegistrationDTO user = dao.getUser();
            if(user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("FULLNAME", user.getFullname());
                
                Cookie cookie = new Cookie(username, password);
                cookie.setMaxAge(60 * 60 * 2);
                response.addCookie(cookie);
                
                if(user.isRole()) {
                    session.setAttribute("ADMIN", user);
                    url = SEARCH_PAGE;
                } else {
                    url = SHOPPING_PAGE;
                }
            }
        } catch (NamingException e) {
            log("LoginServlet _ Naming " + e.getMessage());
        } catch (SQLException e) {
            log("LoginServlet _ SQL " + e.getMessage());
        } finally {
            response.sendRedirect(url);
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
