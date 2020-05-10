package hu.alkfejl.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
    private StringProperty seatsP = new SimpleStringProperty();

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

    public String getSeatsP() {
        return seatsP.get();
    }

    public StringProperty seatsPProperty() {
        return seatsP;
    }

    public void setSeatsP(String seatsP) {
        this.seatsP.set(seatsP);
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
    public void setSeatsAsString(String s){
        seats.clear();
        for(String a : s.split(",")){
            seats.add(Integer.parseInt(a));
        }
    }

    public StringProperty usernameProperty(){
        return this.reservation.usernameProperty();
    }

    public StringProperty titleProperty(){
        return this.movie.titleProperty();
    }

    public StringProperty seatsProperty(){
        return this.seatsP;
    }
    public StringProperty screeningProperty(){
        return new SimpleStringProperty( "Room: " + this.screening.getRoom() + ", start: " + this.screening.getDate() + " " + this.screening.getTime());
    }

    @Override
    public String toString() {
        return "Wrap{" +
                " resId=" + reservation.getId() +
                " ,user=" + reservation.getUsername() +
                ", movie=" + movie.getTitle() +
                ", screening=" + screening.getRoom() + " " + screening.getDate() + " - " + screening.getTime() +
                ", seats=" + seatsP +
                ", seatsArray" + seats.toString()+
                ", sc " + screeningProperty().getValue()+
                '}';
    }



    public void copyTo(Wrap w){
        w.reservation.idProperty().bindBidirectional(this.reservation.idProperty());
        w.reservation.screeningIdProperty().bind(this.reservation.screeningIdProperty());
        w.reservation.usernameProperty().bindBidirectional(this.reservation.usernameProperty());
        w.movie.idProperty().bindBidirectional(this.movie.idProperty());
        w.movie.titleProperty().bindBidirectional(this.movie.titleProperty());
        w.screening.roomProperty().bindBidirectional(this.screening.roomProperty());
        w.screening.dateProperty().bindBidirectional(this.screening.dateProperty());
        w.screening.timeProperty().bindBidirectional(this.screening.timeProperty());
        w.seats = this.seats;
        w.screeningProperty().bind(this.screeningProperty());
    }
}
