package hu.alkfejl.view.controller;

import hu.alkfejl.controller.CinemaController;
import hu.alkfejl.model.Movie;
import hu.alkfejl.model.Room;
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

public class RoomListController implements Initializable {
    @FXML
    private TableView<Room> roomTable;

    @FXML
    private TableColumn<Room, Integer> idCol;

    @FXML
    private TableColumn<Room, Integer> rowsCol;

    @FXML
    private TableColumn<Room, Integer> columnsCol;
    @FXML
    private TableColumn<Room, Void> actionsCol;

    private void delete(Room r){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this room?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait().ifPresent(buttonType -> {
            if(buttonType.equals(ButtonType.YES)){
                CinemaController.getInstance().removeRoom(r);
                System.out.println("asd");
            }
        });
    }
    private void edit(Room r){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/hu/alkfejl/roomAdd.fxml"));
            Parent root = loader.load();
            RoomAddController controller = loader.getController();
            controller.initRoom(r);
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
    public void refreshTable() {
        List<Room> list = CinemaController.getInstance().listAllRooms();
        roomTable.setItems(FXCollections.observableList(list));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Room> list = CinemaController.getInstance().listAllRooms();
        roomTable.setItems(FXCollections.observableList(list));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        rowsCol.setCellValueFactory(new PropertyValueFactory<>("rows"));
        columnsCol.setCellValueFactory(new PropertyValueFactory<>("columns"));
        actionsCol.setCellFactory(param -> new TableCell<>() {
            private final Button deleteBtn = new Button("Delete");
            private final Button editBtn = new Button("Edit");

            {
                deleteBtn.setOnAction(event -> {
                    Room r = getTableView().getItems().get(getIndex());
                    delete(r);
                    refreshTable();
                });

                editBtn.setOnAction(event -> {
                    Room r = getTableView().getItems().get(getIndex());
                    edit(r);
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
