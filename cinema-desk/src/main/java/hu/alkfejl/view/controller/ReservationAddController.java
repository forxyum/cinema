package hu.alkfejl.view.controller;

import hu.alkfejl.controller.CinemaController;
import hu.alkfejl.model.Movie;
import hu.alkfejl.model.Screening;
import hu.alkfejl.model.Seat;
import hu.alkfejl.model.Wrap;
import hu.alkfejl.utils.Utils;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
import java.util.concurrent.TimeUnit;

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
    private IntegerProperty screeningId = new SimpleIntegerProperty();
    private Integer screeningIndex;
    private List<Integer> oldSeats;

    private Wrap w = new Wrap();

    @FXML
    void cancel() {
        Stage stage = (Stage) movieBox.getScene().getWindow();
        stage.close();
    }

    @FXML
    void submit() {
        if (usernameField.getText().isEmpty()) {
            Utils.showWarning("You must enter you username");
            return;
        } else {
            List<String> usernames = CinemaController.getInstance().listAllUsernames();
            if (!usernames.contains(usernameField.getText())) {
                Utils.showWarning("User does not exits");
                return;
            }
        }
        if (movieBox.getValue() == null) {
            Utils.showWarning("Movie must be selected");
            return;
        }
        if (screeningBox.getValue() == null) {
            Utils.showWarning("Screening must be selected");
            return;
        }
        if (seatsField.getText().isEmpty()) {
            Utils.showWarning("You must add at least one seat");
            return;
        } else {
            boolean right = true;
            List<String> wrongs = new ArrayList<>();
            for (String seat : seatsField.getText().split(",")) {
                if (occupiedSeats.contains(seat) || Integer.parseInt(seat) > (dimensions.get(0) * dimensions.get(1)) || Integer.parseInt(seat) < 1) {
                    right = false;
                    wrongs.add(seat);
                }
            }
            if (!right) {
                Utils.showWarning("The seats numbered " + String.join(",", wrongs) + " are not in the list of available seats");
            }
        }
        if (w.getReservation().getId() == 0) {
            boolean succ = CinemaController.getInstance().addReservation(w.getReservation());
            if (succ) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Integer lastId = CinemaController.getInstance().lastReservationId();
                boolean succ2 = true;
                for (String seat : w.seatsProperty().getValue().split(",")) {
                    System.out.println(seat);
                    if (!CinemaController.getInstance().addSeat(new Seat(lastId, Integer.parseInt(seat)))) {
                        succ2 = false;
                    }
                }
                if (succ2) {
                    cancel();
                } else {
                    Utils.showWarning("Failed to save");
                }
            } else {
                Utils.showWarning("Failed to save");
            }
        } else {
            System.out.println(w.getReservation());
            CinemaController.getInstance().updateReservation(w.getReservationId(),w.getReservation());
            if (w.seatsProperty().getValue().length() > 0) {
                for (Integer seat : oldSeats) {
                    if (!w.seatsProperty().getValue().contains(seat.toString())) {
                        CinemaController.getInstance().removeSeat(new Seat(w.getReservationId(),seat));
                    }
                }
                for (String seat : w.seatsProperty().getValue().split(",")) {
                    if (!oldSeats.contains(Integer.parseInt(seat))) {
                        CinemaController.getInstance().addSeat(new Seat(w.getReservationId(),Integer.parseInt(seat)));
                    }
                }
            }
        }
    }

    public void initReservation(Wrap w) {
        w.copyTo(this.w);
        screeningBox.getSelectionModel().select(w.screeningProperty().getValue());
        StringBuilder seats = new StringBuilder();
        for (Integer a : w.getSeats()) {
            seats.append(a).append(",");
        }
        seatsField.textProperty().setValue(seats.substring(0, seats.length() - 1));
        oldSeats = CinemaController.getInstance().seatsByReservationId(w.getReservationId());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        movieList = CinemaController.getInstance().listAllMovies();
        for (Movie mov : movieList) {
            movieBox.getItems().add(mov.getTitle());
        }
        movieBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            movieIndex = newValue.intValue();
            screeningBox.getItems().clear();
            screeningList = CinemaController.getInstance().screeningsByMovieId(movieList.get(movieIndex).getId());
            for (Screening s : screeningList) {
                screeningBox.getItems().add("Room: " + s.getRoom() + ", start: " + s.getDate() + " " + s.getTime());
            }
        });
        screeningBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            screeningIndex = newValue.intValue();
            availableView.getItems().clear();
            if (screeningBox.getSelectionModel().selectedIndexProperty().intValue() >= 0) {
                occupiedSeats = CinemaController.getInstance().seatsByScreeningId(screeningList.get(newValue.intValue()).getId());
                screeningId.unbind();
                w.getReservation().screeningIdProperty().unbind();
                screeningId.set(screeningList.get(newValue.intValue()).getId());
                w.getReservation().screeningIdProperty().setValue(screeningList.get(newValue.intValue()).getId());
                dimensions = CinemaController.getInstance().roomDimensionsByScreeningId(screeningList.get(newValue.intValue()).getId());
                for (int i = 1; i <= dimensions.get(0) * dimensions.get(1); i++) {
                    if (!occupiedSeats.contains(i)) {
                        availableView.getItems().add(i);
                    }
                }
            }
        });
        usernameField.textProperty().bindBidirectional(w.usernameProperty());
        movieBox.valueProperty().bindBidirectional(w.titleProperty());
        seatsField.textProperty().bindBidirectional(w.seatsPProperty());
        screeningId.bindBidirectional(w.getReservation().screeningIdProperty());
    }
}
