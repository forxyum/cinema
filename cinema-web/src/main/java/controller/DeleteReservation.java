package controller;

import hu.alkfejl.model.Reservation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/DeleteReservation")
public class DeleteReservation extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int resId = Integer.parseInt(req.getParameter("resId"));
        Reservation toDelete = new Reservation();
        toDelete.setId(resId);
        WebController.getInstance().removeReservation(toDelete);
        resp.sendRedirect("pages/list_reservation.jsp");
    }
}
