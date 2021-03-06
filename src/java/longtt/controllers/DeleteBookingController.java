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
import longtt.daos.BookingDAO;
import longtt.dtos.UserDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author Long
 */
public class DeleteBookingController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(DeleteBookingController.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                UserDTO userDTO = (UserDTO) session.getAttribute("USER");
                if (userDTO != null && userDTO.getRole() == 1) {
                    String bookingId = request.getParameter("txtBookingId");
                    BookingDAO bookingDAO = new BookingDAO();
                    if (bookingDAO.deleteBookingById(bookingId)) {
                        request.setAttribute("NOTICE", "Deleted Booking ID " + bookingId + " successfully.");
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("ERROR at DeleteBookingController: " + e.getMessage());
        } finally {
            request.getRequestDispatcher("SearchBookingController").forward(request, response);
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
