package hu.alkfejl.view.controller;

import hu.alkfejl.controller.CinemaController;
import hu.alkfejl.model.Movie;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MovieListController implements Initializable {
    @FXML
    private TableView<Movie> movieTable;

    @FXML
    private TableColumn<Movie, String> titleCol;

    @FXML
    private TableColumn<Movie, Integer> lengthCol;

    @FXML
    private TableColumn<Movie, Integer> ratingCol;

    @FXML
    private TableColumn<Movie, Integer> directorCol;

    @FXML
    private TableColumn<Movie, Integer> descriptionCol;
    @FXML
    private TableColumn<Movie, Integer> idCol;
    @FXML
    private TableColumn<Movie, Void> actionsCol;

    public void refreshTable() {
        List<Movie> list = CinemaController.getInstance().listAllMovies();
        movieTable.setItems(FXCollections.observableList(list));
    }
    private void delete(Movie m){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this movie?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait().ifPresent(buttonType -> {
            if(buttonType.equals(ButtonType.YES)){
                CinemaController.getInstance().removeMovie(m);
            }
        });
    }
    private void edit(Movie m){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/hu/alkfejl/movieAdd.fxml"));
            Parent root = loader.load();
            MovieAddController controller = loader.getController();
            controller.initMovie(m);
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Movie> list = CinemaController.getInstance().listAllMovies();
        movieTable.setItems(FXCollections.observableList(list));
        movieTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        lengthCol.setCellValueFactory(new PropertyValueFactory<>("length"));
        ratingCol.setCellValueFactory(new PropertyValueFactory<>("rating"));
        directorCol.setCellValueFactory(new PropertyValueFactory<>("director"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        actionsCol.setCellFactory(param -> new TableCell<>() {
            private final Button deleteBtn = new Button("Delete");
            private final Button editBtn = new Button("Edit");

            {
                deleteBtn.setOnAction(event -> {
                    Movie m = getTableView().getItems().get(getIndex());
                    delete(m);
                    refreshTable();
                });

                editBtn.setOnAction(event -> {
                    Movie m = getTableView().getItems().get(getIndex());
                    edit(m);
                    refreshTable();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox container = new HBox();
                    container.getChildren().addAll(deleteBtn, editBtn);
                    setGraphic(container);
                }
            }
        });
    }
}
