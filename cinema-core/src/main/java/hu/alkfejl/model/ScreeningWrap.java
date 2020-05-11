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
    private IntegerProperty room = new SimpleIntegerProperty();
    private StringProperty movieTitle = new SimpleStringProperty();

    public ScreeningWrap(Integer id, Integer movieId, String date, String time, Integer room, String movieTitle) {
        this.id.set(id);
        this.movieId.set(movieId);
        this.date.set(date);
        this.time.set(time);
        this.room.set(room);
        this.movieTitle.set(movieTitle);
    }

    public ScreeningWrap(Screening sc, String movieTitle) {
        this.id.set(sc.getId());
        this.movieId.set(sc.getMovieId());
        this.date.set(sc.getDate());
        this.time.set(sc.getTime());
        this.room.set(sc.getRoom());
        this.movieTitle.set(movieTitle);
    }

    public ScreeningWrap() {
    }

    public Screening toScreening() {
        return new Screening(this.getId(), this.getMovieId(), this.getDate(), this.getTime(), this.getRoom());
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public int getMovieId() {
        return movieId.get();
    }

    public void setMovieId(int movieId) {
        this.movieId.set(movieId);
    }

    public IntegerProperty movieIdProperty() {
        return movieId;
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public StringProperty dateProperty() {
        return date;
    }

    public String getTime() {
        return time.get();
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    public StringProperty timeProperty() {
        return time;
    }

    public int getRoom() {
        return room.get();
    }

    public void setRoom(int room) {
        this.room.set(room);
    }

    public IntegerProperty roomProperty() {
        return room;
    }

    public String getMovieTitle() {
        return movieTitle.get();
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle.set(movieTitle);
    }

    public StringProperty movieTitleProperty() {
        return movieTitle;
    }

    public String getDateTime() {
        return this.getDate() + " at: " + this.getTime();
    }

    @Override
    public String toString() {
        return "ScreeningWrap{" +
                "id=" + id +
                ", movieId=" + movieId +
                ", date=" + date +
                ", time=" + time +
                ", room=" + room +
                ", movieTitle=" + movieTitle +
                '}';
    }
    public void copyTo(ScreeningWrap w){
        w.idProperty().bindBidirectional(this.idProperty());
        w.movieIdProperty().bindBidirectional(this.movieIdProperty());
        w.dateProperty().bindBidirectional(this.dateProperty());
        w.timeProperty().bindBidirectional(this.timeProperty());
        w.roomProperty().bindBidirectional(this.roomProperty());
        w.movieTitleProperty().bindBidirectional(this.movieTitleProperty());
    }
}
