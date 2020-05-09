package hu.alkfejl.view.controller;

import hu.alkfejl.controller.CinemaController;
import hu.alkfejl.model.Movie;
import hu.alkfejl.model.Screening;
import javafx.beans.property.IntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ReservationAddController implements Initializable {
    @FXML
    private ChoiceBox<String> movieBox;

    @FXML
    private TextField usernameField;

    @FXML
    private ChoiceBox<String> screeningBox;

    @FXML
    private ListView<Integer> availableView;

    @FXML
    private TextField seatsField;

    private List<Movie> movieList = new ArrayList<>();
    private Integer movieIndex;
    private List<Screening> screeningList = new ArrayList<>();
    private List<Integer> occupiedSeats = new ArrayList<>();
    private List<Integer> dimensions = new ArrayList<>();

    @FXML
    void cancel() {
        Stage stage = (Stage) movieBox.getScene().getWindow();
        stage.close();
    }

    @FXML
    void submit() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        movieList = CinemaController.getInstance().listAllMovies();
        for(Movie mov : movieList){
            movieBox.getItems().add(mov.getTitle());
        }
        movieBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            movieIndex = newValue.intValue();
            screeningBox.getItems().clear();
            screeningList = CinemaController.getInstance().screeningsByMovieId(movieList.get(movieIndex).getId());
            for(Screening s : screeningList){
                screeningBox.getItems().add("Room: " + s.getRoom() + ", start: " + s.getDate() + " " + s.getTime());
            }
        });
        screeningBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            availableView.getItems().clear();
            occupiedSeats = CinemaController.getInstance().seatsByScreeningId(screeningList.get(newValue.intValue()).getId());
            System.out.println(occupiedSeats);
            dimensions = CinemaController.getInstance().roomDimensionsByScreeningId(screeningList.get(newValue.intValue()).getId());
            for(int i=1;i<=dimensions.get(0)*dimensions.get(1);i++){
                if(!occupiedSeats.contains(i)){
                    availableView.getItems().add(i);
                }
            }
        });
    }
}
