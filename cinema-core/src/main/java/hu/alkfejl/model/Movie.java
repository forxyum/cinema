package hu.alkfejl.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Movie{
    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty title = new SimpleStringProperty();
    private IntegerProperty length = new SimpleIntegerProperty();
    private StringProperty rating = new SimpleStringProperty();
    private StringProperty director = new SimpleStringProperty();
    private StringProperty description = new SimpleStringProperty();
    private StringProperty cover = new SimpleStringProperty();

    public Movie() {
    }

    public Movie(Integer id, String title,Integer length,String rating,String director,String description,String cover){
        this.id.set(id);
        this.title.set(title);
        this.length.set(length);
        this.rating.set(rating);
        this.director.set(director);
        this.description.set(description);
        this.cover.set(cover);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title=" + title +
                ", length=" + length +
                ", rating=" + rating +
                ", director=" + director +
                ", description=" + description +
                ", cover=" + cover +
                '}';
    }
    public void copyTo(Movie m){
        m.id.bindBidirectional(this.idProperty());
        m.title.bindBidirectional(this.titleProperty());
        m.length.bindBidirectional(this.lengthProperty());
        m.rating.bindBidirectional(this.ratingProperty());
        m.director.bindBidirectional(this.directorProperty());
        m.description.bindBidirectional(this.descriptionProperty());
        m.cover.bindBidirectional(this.coverProperty());
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

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public int getLength() {
        return length.get();
    }

    public IntegerProperty lengthProperty() {
        return length;
    }

    public void setLength(int length) {
        this.length.set(length);
    }

    public String getRating() {
        return rating.get();
    }

    public StringProperty ratingProperty() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating.set(rating);
    }

    public String getDirector() {
        return director.get();
    }

    public StringProperty directorProperty() {
        return director;
    }

    public void setDirector(String director) {
        this.director.set(director);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getCover() {
        return cover.get();
    }

    public StringProperty coverProperty() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover.set(cover);
    }
}
