package hu.alkfejl.controller;

import hu.alkfejl.dao.CinemaDAO;
import hu.alkfejl.dao.CinemaDAOImp;
import hu.alkfejl.model.*;

import java.util.List;

public class CinemaController {
    private static CinemaController single_instance = null;
    private CinemaDAO dao = new CinemaDAOImp();

    public CinemaController() {
    }

    public static CinemaController getInstance() {
        if (single_instance == null) {
            single_instance = new CinemaController();
        }
        return single_instance;
    }

    public List<Movie> listAllMovies() {
        return dao.listAllMovies();
    }

    public boolean addMovie(Movie m) {
        return dao.addMovie(m);
    }

    public boolean updateMovie(Integer id, Movie m) {
        return dao.updateMovie(id, m);
    }

    public boolean removeMovie(Movie m) {
        return dao.deleteMovie(m);
    }

    public Integer lastMovieId() {
        return dao.getLastMovieId();
    }

    public List<Room> listAllRooms() {
        return dao.listAllRooms();
    }

    public boolean addRoom(Room r) {
        return dao.addRoom(r);
    }

    public boolean updateRoom(Integer id, Room r) {
        return dao.updateRoom(id, r);
    }

    public boolean removeRoom(Room r) {
        return dao.deleteRoom(r);
    }

    public boolean addActor(Actor a) {
        return dao.addActor(a);
    }

    public List<String> listActorNamesOfMovie(Integer movieId) {
        return dao.listActorNamesOfMovie(movieId);
    }

    public boolean removeActor(Actor a) {
        return dao.deleteActor(a);
    }

    public List<Reservation> listAllReservations() {
        return dao.listAllReservations();
    }
    public String movieTitleByReservationId(Integer resId){
        return dao.getMovieTitleByReservationId(resId);
    }
    public String screeningDateByReservationId(Integer resId){
        return dao.getScreeningDateTimeByReservationId(resId);
    }
    public List<Integer> seatsByReservationId(Integer resId){
        return dao.getSeatsByReservationId(resId);
    }
    public Integer roomByReservationId(Integer resId){
        return dao.getRoomByReservationId(resId);
    }
    public List<Screening> screeningsByMovieId(Integer movieId){
        return dao.getScreeningsByMovieId(movieId);
    }
    public List<Integer> seatsByScreeningId(Integer screeningId){
        return dao.getSeatsByScreeningId(screeningId);
    }
    public List<Integer> roomDimensionsByScreeningId(Integer screeningId){
        return dao.getRoomDimensionsByScreeningId(screeningId);
    }
}
