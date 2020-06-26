package controller;

import hu.alkfejl.dao.CinemaDAO;
import hu.alkfejl.dao.CinemaDAOImp;
import hu.alkfejl.dao.UserDAO;
import hu.alkfejl.dao.UserDAOImp;
import hu.alkfejl.model.*;

import java.util.List;

public class WebController {
    private static WebController single_instance = null;
    private CinemaDAO dao = new CinemaDAOImp();
    private UserDAO userDAO = new UserDAOImp();

    public WebController() {
    }

    public static WebController getInstance() {
        if (single_instance == null) {
            single_instance = new WebController();
        }
        return single_instance;
    }
    public void register(User user){
        userDAO.register(user);
    }
    public User login(String username,String password){
        return userDAO.login(username,password);
    }
    public List<Reservation> reservationsByUser(User u){
        return dao.getReservationsByUser(u);
    }
    public String movieTitleByReservationId(Integer resId) {
        return dao.getMovieTitleByReservationId(resId);
    }
    public String screeningDateByReservationId(Integer resId) {
        return dao.getScreeningDateTimeByReservationId(resId);
    }
    public List<Integer> seatsByReservationId(Integer resId) {
        return dao.getSeatsByReservationId(resId);
    }
    public Integer roomByReservationId(Integer resId) {
        return dao.getRoomByReservationId(resId);
    }
    public boolean removeReservation(Reservation r){return dao.deleteReservation(r);}
    public Reservation getReservationById(Integer id){return  dao.getReservationById(id);}
    public List<Movie> getAllMovies(){return dao.listAllMovies();}
    public List<Screening> screeningsByMovieId(Integer movieId) {
        return dao.getScreeningsByMovieId(movieId);
    }
    public List<Integer> seatsByScreeningId(Integer screeningId) {
        return dao.getSeatsByScreeningId(screeningId);
    }
    public List<Integer> roomDimensionsByScreeningId(Integer screeningId) {
        return dao.getRoomDimensionsByScreeningId(screeningId);
    }
    public boolean addReservation(Reservation r) {
        return dao.addReservation(r);
    }
    public boolean addSeat(Seat s) {
        return dao.addSeat(s);
    }
    public Integer lastReservationId() {
        return dao.getLastReservationId();
    }
}
