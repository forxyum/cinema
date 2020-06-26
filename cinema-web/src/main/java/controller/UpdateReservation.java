package controller;

import hu.alkfejl.model.Reservation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/UpdateReservation")
public class UpdateReservation extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String resId = req.getParameter("resId");

        if(resId != null && !resId.isEmpty()){
            int id = Integer.parseInt(resId);
            Reservation res = WebController.getInstance().getReservationById(id);
            req.setAttribute("reservation",res);
        }
        req.getRequestDispatcher("pages/add_reservation").forward(req,resp);
    }
}
