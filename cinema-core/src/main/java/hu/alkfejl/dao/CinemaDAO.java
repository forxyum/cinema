package hu.alkfejl.dao;

import hu.alkfejl.model.*;

import java.util.List;

public interface CinemaDAO {
    //Movie
    boolean addMovie(Movie m);
    boolean deleteMovie(Movie m);
    boolean updateMovie(int id,Movie m);
    List<Movie> advSearchMovies(String title, Integer length, String rating, String director);
    List<Movie> listAllMovies();
    Integer getLastMovieId();
    //Screening
    List<Screening> listScreenings(int movieId);
    boolean addScreening(Screening s);
    boolean deleteScreening(Screening s);
    boolean updateScreening(int id,Screening s);
    List<Screening> listAllScreenings();
    //Room
    boolean addRoom(Room r);
    boolean deleteRoom(Room r);
    boolean updateRoom(int id,Room r);
    List<Room> listAllRooms();
    //Reservation
    boolean addReservation(Reservation r);
    boolean deleteReservation(Reservation r);
    boolean updateReservation(int id,Reservation r);
    Integer getLastReservationId();
    List<Reservation> listAllReservations();
    List<Reservation> advSearchReservations(String name, Integer screeningId);
    boolean addActor(Actor a);
    boolean deleteActor(Actor a);
    List<User> listAllUsers();
    List<String> listAllUsernames();
    List<String> listActorNamesOfMovie(Integer movieId);
    String getMovieTitleByReservationId(Integer reservationId);
    String getMovieTitleByScreeningId(Integer screeningId);
    String getScreeningDateTimeByReservationId(Integer reservationId);
    List<Integer> getSeatsByReservationId(Integer reservationId);
    Integer getRoomByReservationId(Integer reservationId);
    List<Screening> getScreeningsByMovieId(Integer movieId);
    List<Integer> getSeatsByScreeningId(Integer screeningId);
    List<Integer> getRoomDimensionsByScreeningId(Integer screeningId);
    boolean addSeat(Seat s);
    boolean deleteSeat(Seat s);
}
