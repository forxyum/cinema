package hu.alkfejl.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Screening {
    private IntegerProperty id = new SimpleIntegerProperty();
    private IntegerProperty movieId = new SimpleIntegerProperty();
    private StringProperty date = new SimpleStringProperty();
    private StringProperty time = new SimpleStringProperty();
    private IntegerProperty room =  new SimpleIntegerProperty();

    public Screening(Integer id, Integer movieId, String date, String time, Integer room) {
        this.id.set(id);
        this.movieId.set(movieId);
        this.date.set(date);
        this.time.set(time);
        this.room.set(room);
    }

    public Screening() {
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

    @Override
    public String toString() {
        return "Screening{" +
                "id=" + id +
                ", movieId=" + movieId +
                ", date=" + date +
                ", time=" + time +
                ", room=" + room +
                '}';
    }
}
