package hu.alkfejl.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ScreeningWrap {
    private IntegerProperty id = new SimpleIntegerProperty();
    private IntegerProperty movieId = new SimpleIntegerProperty();
    private StringProperty date = new SimpleStringProperty();
    private StringProperty time = new SimpleStringProperty();
    private IntegerProperty room =  new SimpleIntegerProperty();
    private StringProperty movieTitle = new SimpleStringProperty();

    public ScreeningWrap(Integer id, Integer movieId, String date, String time, Integer room,String movieTitle) {
        this.id.set(id);
        this.movieId.set(movieId);
        this.date.set(date);
        this.time.set(time);
        this.room.set(room);
        this.movieTitle.set(movieTitle);
    }
    public ScreeningWrap(Screening sc, String movieTitle){
        this.id.set(sc.getId());
        this.movieId.set(sc.getMovieId());
        this.date.set(sc.getDate());
        this.time.set(sc.getTime());
        this.room.set(sc.getRoom());
        this.movieTitle.set(movieTitle);
    }

    public ScreeningWrap() {
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getMovieId() {
        return movieId.get();
    }

    public IntegerProperty movieIdProperty() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId.set(movieId);
    }

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getTime() {
        return time.get();
    }

    public StringProperty timeProperty() {
        return time;
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    public int getRoom() {
        return room.get();
    }

    public IntegerProperty roomProperty() {
        return room;
    }

    public void setRoom(int room) {
        this.room.set(room);
    }

    public String getMovieTitle() {
        return movieTitle.get();
    }

    public StringProperty movieTitleProperty() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle.set(movieTitle);
    }
}
