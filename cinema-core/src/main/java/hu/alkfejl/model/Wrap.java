package hu.alkfejl.model;

import javafx.beans.property.IntegerProperty;
import org.sqlite.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Wrap {
    private Movie movie;
    private Reservation reservation;
    private Screening screening;
    List<Integer> seats = new ArrayList<Integer>();

    public Wrap(Movie movie, Reservation reservation, Screening screening, List<Integer> seats) {
        this.movie = movie;
        this.reservation = reservation;
        this.screening = screening;
        this.seats = seats;
    }

    public Wrap() {
        this.movie = new Movie();
        this.reservation = new Reservation();
        this.screening = new Screening();
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
    public String getMovieTitle(){
        return this.movie.getTitle();
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
    public Integer getReservationId(){
        return this.reservation.getId();
    }
    public String getReservationUsername(){
        return this.reservation.getUsername();
    }

    public Screening getScreening() {
        return screening;
    }

    public String getScreeningDate(){
        return this.screening.getDate().split("T")[0] + " " +this.screening.getDate().split("T")[1];
    }

    public void setScreening(Screening screening) {
        this.screening = screening;
    }

    public Integer getRoom(){
        return this.screening.getRoom();
    }

    public List<Integer> getSeats() {
        return seats;
    }

    public void setSeats(List<Integer> seats) {
        this.seats = seats;
    }

    public String getSeatsAsString(){
        String res ="";
        for(int a : this.seats){
            res += a + ",";
        }
        if(res.length()>0){
            res = res.substring(0,res.length()-1);
        }
        return res;
    }
}
