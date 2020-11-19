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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import trong.registration.RegistrationCreateAccountError;
import trong.registration.RegistrationDAO;

/**
 *
 * @author DELL
 */
public class CreateAccountServlet extends HttpServlet {
    private final String CREATE_ACCOUNT_FAIL_PAGE = "createAccountFailPage";
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
        
        String url = CREATE_ACCOUNT_FAIL_PAGE;
        
        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");
        String confirm = request.getParameter("txtConfirm");
        String fullname = request.getParameter("txtFullname");
        
        boolean errFound = false;
        RegistrationCreateAccountError errors = new RegistrationCreateAccountError();
        
        try {
            
            if(username.length() < 6 || username.length() > 30) {
                errFound = true;
                errors.setUsernameLengthErr("Username length has 6 - 30 chars");
            }
            if(password.length() < 6 || password.length() > 20) {
                errFound = true;
                errors.setPasswordLengthErr("Password length has 6 - 20 chars");
            } else if(!confirm.equals(password)){
                errFound = true;
                errors.setConfirmNotMatch("Confirm not match");
            }
            if(fullname.length() < 2 || fullname.length() > 50) {
                errFound = true;
                errors.setFullnameLengthErr("Fullname length has 2 - 50 chars");
            }
            
            if(errFound) {
                url = "createAccountFail.jsp";
                request.setAttribute("CREATEERRORS", errors);
            } else {
                RegistrationDAO dao = new RegistrationDAO();
                boolean result = dao.createAccount(username, password, fullname, false);
                
                if(result) {
                    url = "loginPage";
                }
            }
        } catch (NamingException e) {
            log("CreateAccountServlet _ Naming " + e.getMessage());
        } catch (SQLException e) {
            String errMsg = e.getMessage();
            log("CreateAccountServlet _ SQL " + errMsg);
            if(errMsg.contains("duplicate")) {
                errFound = true;
                errors.setUsernameIsExisted("Username is existed");
            }
        } finally {
            if(errFound) {
                RequestDispatcher rd = request.getRequestDispatcher(url);
                rd.forward(request, response);
            } else {
                response.sendRedirect(url);
            }
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
