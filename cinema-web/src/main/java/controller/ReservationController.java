package controller;

import hu.alkfejl.model.Reservation;
import hu.alkfejl.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/ReservationController")
public class ReservationController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        User user = (User) req.getSession().getAttribute("currentUser");
        if(user == null){
            return;
        }
        List<Reservation> resList = WebController.getInstance().reservationsByUser(user);
        req.setAttribute("reservationList",resList);
        List<String> titleList = new ArrayList<>();
        List<String> startList = new ArrayList<>();
        List<Integer> roomList = new ArrayList<>();
        List<String> seatList = new ArrayList<>();
        for(Reservation r : resList){
            titleList.add(WebController.getInstance().movieTitleByReservationId(r.getId()));
            startList.add(WebController.getInstance().screeningDateByReservationId(r.getId()));
            roomList.add(WebController.getInstance().roomByReservationId(r.getId()));
            seatList.add(WebController.getInstance().seatsByReservationId(r.getId()).stream().map(String::valueOf).collect(Collectors.joining(",")));
        }
        req.setAttribute("titleList",titleList);
        req.setAttribute("startList",startList);
        req.setAttribute("roomList",roomList);
        req.setAttribute("seatList",seatList);

    }
}
