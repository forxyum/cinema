package hu.alkfejl.view.controller;

import hu.alkfejl.controller.CinemaController;
import hu.alkfejl.model.Movie;
import hu.alkfejl.model.Screening;
import hu.alkfejl.model.ScreeningWrap;
import hu.alkfejl.utils.Utils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class ScreeningAddController implements Initializable {

    @FXML
    private ChoiceBox<String> movieBox;

    @FXML
    private TextField roomField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField timeField;

    private List<Movie> movieList = new ArrayList<>();
    private ScreeningWrap wrap = new ScreeningWrap();
    private List<Integer> movieIdList = new ArrayList<>();
    private DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

    @FXML
    private void submit() {
        if (movieBox.getSelectionModel().isEmpty()) {
            Utils.showWarning("You must select a movie");
            return;
        } else {
            wrap.setMovieId(movieIdList.get(movieBox.getSelectionModel().getSelectedIndex()));
        }
        if (datePicker.valueProperty().isNull().get()) {
            Utils.showWarning("You must select a date");
            return;
        }
        if (timeField.getText().isEmpty()) {
            Utils.showWarning("You must enter a starting time");
            return;
        } else if (!timeField.getText().matches("^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$")) {
            Utils.showWarning("Time format does not match hh:mm");
            return;
        }

        if (roomField.getText().isEmpty()) {
            Utils.showWarning("You must enter a room number");
            return;
        }
        if (wrap.getId() == 0) {
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Screening sc = wrap.toScreening();
            CinemaController.getInstance().addScreening(sc);
        } else {
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Screening sc = wrap.toScreening();
            CinemaController.getInstance().updateScreening(wrap.getId(), sc);
        }
        cancel();
    }

    @FXML
    private void cancel() {
        Stage stage = (Stage) movieBox.getScene().getWindow();
        stage.close();
    }

    public void initScreening(ScreeningWrap w) {
        w.copyTo(this.wrap);
        StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate object) {
                return object == null ? null : formatter.format(object);
            }

            @Override
            public LocalDate fromString(String string) {
                return string == null || string.isEmpty() ? null : LocalDate.parse(string, formatter);
            }
        };
        wrap.dateProperty().bindBidirectional(datePicker.valueProperty(), converter);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        movieList = CinemaController.getInstance().listAllMovies();
        for (Movie mov : movieList) {
            movieBox.getItems().add(mov.getTitle());
            movieIdList.add(mov.getId());
        }
        movieBox.valueProperty().bindBidirectional(wrap.movieTitleProperty());
        roomField.textProperty().bindBidirectional(wrap.roomProperty().asObject(), new IntegerStringConverter());
        //timeField.textProperty().bindBidirectional(wrap.timeProperty());
        wrap.timeProperty().bindBidirectional(timeField.textProperty());
        StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate object) {
                return object == null ? null : formatter.format(object);
            }

            @Override
            public LocalDate fromString(String string) {
                return string == null || string.isEmpty() ? null : LocalDate.parse(string, formatter);
            }
        };
        wrap.dateProperty().bindBidirectional(datePicker.valueProperty(), converter);
    }
}
