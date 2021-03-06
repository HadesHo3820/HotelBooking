/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longtt.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import longtt.dtos.RoomCart;
import org.apache.log4j.Logger;

/**
 *
 * @author Long
 */
public class CartUpdateController extends HttpServlet {

    private static final String SUCCESS = "detailCart.jsp";
    private static final String EMPTY_CART = "LoadAreaController";
    private static final Logger LOGGER = Logger.getLogger(CartUpdateController.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = SUCCESS;
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                int roomId = Integer.parseInt(request.getParameter("txtRoomId"));
                int cartQty = Integer.parseInt(request.getParameter("txtCartQty"));
                int quantity = Integer.parseInt(request.getParameter("txtQuantity"));
                boolean valid = true;
                if (cartQty > quantity) {
                    valid = false;
                    request.setAttribute("NOTICE", "Amount exceed maximum quantity of vacant rooms.");
                }
                if (valid) {
                    RoomCart cart = (RoomCart) session.getAttribute("CART");
                    if (cartQty == 0) {
                        cart.removeFromCart(roomId);
                    } else {
                        cart.updateCart(roomId, cartQty);
                    }

                    if (cart.getCart().isEmpty()) {
                        session.removeAttribute("CART");
                        url = EMPTY_CART;
                    } else {
                        session.setAttribute("CART", cart);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("ERROR at CartUpdateController: " + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
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
