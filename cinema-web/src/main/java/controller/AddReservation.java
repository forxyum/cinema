package controller;

import hu.alkfejl.model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@WebServlet("/AddReservation")
public class AddReservation extends HttpServlet {

    List<Screening> screeningList = new ArrayList<>();
    List<Integer> seatList = new ArrayList<>();
    List<Integer> dimensions = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        User user = (User) req.getSession().getAttribute("currentUser");
        if (user == null) {
            resp.sendRedirect("pages/login.jsp");
            return;
        }
        List<Movie> movies = WebController.getInstance().getAllMovies();
        req.getSession().setAttribute("movies", movies);
        if (screeningList.size() > 0) {
            req.getSession().setAttribute("screenings", screeningList);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        if (req.getParameter("movie") != null) {
            List<Movie> temp = (ArrayList<Movie>) req.getSession().getAttribute("movies");
            int movieIndex = Integer.parseInt(req.getParameter("movie"));
            req.getSession().setAttribute("movieIndex", movieIndex);
            screeningList = WebController.getInstance().screeningsByMovieId(temp.get(movieIndex).getId());
            System.out.println(screeningList);
            req.getSession().setAttribute("movieId",temp.get(movieIndex).getId());
            req.getSession().setAttribute("screenings", screeningList);
            resp.sendRedirect("pages/add_reservation.jsp");
        } else {
            return;
        }
        if (req.getParameter("screening") != null) {
            int screeningIndex = Integer.parseInt(req.getParameter("screening"));
            req.getSession().setAttribute("screeningIndex", screeningIndex);
            seatList = WebController.getInstance().seatsByScreeningId(screeningList.get(screeningIndex).getId());
            dimensions = WebController.getInstance().roomDimensionsByScreeningId(screeningList.get(screeningIndex).getId());
            req.getSession().setAttribute("screeningId",screeningList.get(screeningIndex).getId());
            req.getSession().setAttribute("rows", dimensions.get(0));
            req.getSession().setAttribute("cols", dimensions.get(1));
            req.getSession().setAttribute("rowsArr", IntStream.range(1, dimensions.get(0) + 1).toArray());
            req.getSession().setAttribute("colsArr", IntStream.range(1, dimensions.get(1) + 1).toArray());
            req.getSession().setAttribute("seats", seatList);
        } else {
            return;
        }
        if (req.getParameter("seatsIn").length()>0 && req.getParameter("seatsIn").matches("^[\\d+,]+$")) {
            List<Integer> givenSeats = (List<Integer>) req.getSession().getAttribute("seats");
            for(String s : req.getParameter("seatsIn").split(",")){
                if(givenSeats.contains(Integer.parseInt(s))){
                    System.out.println("seat error");
                    return;
                }
            }
            User user = (User) req.getSession().getAttribute("currentUser");
            Reservation res = new Reservation(0,Integer.parseInt(req.getSession().getAttribute("screeningId").toString()),user.getUsername());
            WebController.getInstance().addReservation(res);
            for(String a : req.getParameter("seatsIn").split(",")){
                WebController.getInstance().addSeat(new Seat(WebController.getInstance().lastReservationId(),Integer.parseInt(a)));
            }
            resp.sendRedirect("pages/list_reservation.jsp");

        }
    }
}
