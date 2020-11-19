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
import javax.servlet.http.HttpSession;
import trong.registration.RegistrationDAO;
import trong.registration.RegistrationDTO;
import trong.registration.RegistrationDeleteAccountError;

/**
 *
 * @author DELL
 */
public class DeleteAccountServlet extends HttpServlet {
    private final String SEARCH_LASTNAME_CONTROLLER = "SearchLastnameServlet";

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
        
        String url = SEARCH_LASTNAME_CONTROLLER;
        
        String pk = request.getParameter("pk");
        String searchValue = request.getParameter("lastSearchValue");
        
        boolean errFound = false;
        RegistrationDeleteAccountError errors = new RegistrationDeleteAccountError();
        
        try {
            
            HttpSession session = request.getSession(false);
            if(session != null) {
                RegistrationDTO user = (RegistrationDTO) session.getAttribute("ADMIN");
                if(pk.equals(user.getUsername())) {
                    errFound = true;
                    errors.setDeleteYourselfErr("Warning!! Cannot delete yourself!!");
                }
            }
            
            RegistrationDAO dao = new RegistrationDAO();
            dao.checkIfAdmin(pk);
            boolean admin = dao.isAdmin();
            if(admin) {
                errFound = true;
                errors.setDeleteAdminErr("Do not have permission to delete other admin!!");
            }
           
            if(errFound) {
                request.setAttribute("DELETEERROR", errors);
            } else {
                boolean result = dao.deleteAccount(pk);
            
                if(result) {
                    url = "searchLastname"
                        + "?txtSearchValue=" + searchValue;
                }
            }
        } catch (NamingException e){
            log("DeleteAccountServlet _ Naming " + e.getMessage());
        } catch (SQLException e){
            log("DeleteAccountServlet _ SQL " + e.getMessage());
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
