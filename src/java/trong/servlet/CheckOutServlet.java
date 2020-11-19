/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trong.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;
import java.util.Random;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import trong.cart.CartObj;
import trong.item.ItemDAO;
import trong.order.OrderDAO;
import trong.order.OrderDTO;

/**
 *
 * @author DELL
 */
public class CheckOutServlet extends HttpServlet {

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
        
        String searchItem = request.getParameter("lastSearchValue");
        
        try {
            if(searchItem == null) {
                searchItem = "";
            }
            HttpSession session = request.getSession(false);
            if(session != null) {
                CartObj cart = (CartObj) session.getAttribute("CARTOBJ");
                if(cart != null) {
                    Map<String, OrderDTO> items = cart.getItems();
                    if(items != null) {
                        OrderDAO dao = new OrderDAO();
                        ItemDAO itemDAO = new ItemDAO();
                        String custId = makeCustId();
                        
                        for(OrderDTO order : items.values()) {
                            order.setCustId(custId);
                            dao.checkOut(order);
                            
                            itemDAO.findItemQuantity(order.getItemId());
                            int oldQuantity = itemDAO.getOldQuantity();
                            itemDAO.updateItemQuantity(order.getItemId(), oldQuantity - order.getQuantity());
                        }
                        session.removeAttribute("CARTOBJ");
                    }
                }
            }
        } catch (NamingException e){
            log("CheckOutServlet _ Naming " + e.getMessage());
        } catch (SQLException e) {
            log("CheckOutServlet _ SQL " + e.getMessage());
        } finally {
            String urlRewriting = "searchItem"
                                + "?txtSearchItem=" + searchItem;
            response.sendRedirect(urlRewriting);
            out.close();
        }
    }
    
    private String makeCustId() {
        String allChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        while(builder.length() < 7) {
            int index = random.nextInt(allChars.length());
            builder.append(allChars.charAt(index));
        }
        return builder.toString();
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
