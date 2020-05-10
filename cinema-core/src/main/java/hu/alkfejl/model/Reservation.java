package hu.alkfejl.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Reservation {
    private IntegerProperty id = new SimpleIntegerProperty();
    private IntegerProperty screeningId = new SimpleIntegerProperty();
    private StringProperty username = new SimpleStringProperty();

    public Reservation(Integer id, Integer screeningId, String username) {
        this.id.set(id);
        this.screeningId.set(screeningId);
        this.username.set(username);
    }

    public Reservation() {
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

    public int getScreeningId() {
        return screeningId.get();
    }

    public IntegerProperty screeningIdProperty() {
        return screeningId;
    }

    public void setScreeningId(int screeningId) {
        this.screeningId.set(screeningId);
    }

    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", screeningId=" + screeningId +
                ", username=" + username +
                '}';
    }
}
