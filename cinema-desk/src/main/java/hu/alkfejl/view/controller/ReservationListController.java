package hu.alkfejl.view.controller;

import hu.alkfejl.controller.CinemaController;
import hu.alkfejl.model.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ReservationListController implements Initializable {
    @FXML
    private TableView<Wrap> reservationTable;

    @FXML
    private TableColumn<Wrap,Integer> resIdCol;

    @FXML
    private TableColumn<Wrap, String> userCol;

    @FXML
    private TableColumn<Wrap,String> titleCol;

    @FXML
    private TableColumn<Wrap, String> dateCol;

    @FXML
    private TableColumn<Wrap, Integer> roomCol;

    @FXML
    private TableColumn<Wrap, String> seatsCol;

    @FXML
    private TableColumn<Wrap, Void> actionsCol;

    public void refreshTable() {
        List<Reservation> resList = CinemaController.getInstance().listAllReservations();
        List<Wrap> wraps = new ArrayList<>();
        for(Reservation r : resList){
            Wrap w = new Wrap();
            w.setReservation(r);
            w.getMovie().setTitle(CinemaController.getInstance().movieTitleByReservationId(r.getId()));
            w.getScreening().setDate(CinemaController.getInstance().screeningDateByReservationId(r.getId()));
            w.setSeats(CinemaController.getInstance().seatsByReservationId(r.getId()));
            w.getScreening().setRoom(CinemaController.getInstance().roomByReservationId(r.getId()));
            wraps.add(w);
        }
        reservationTable.setItems(FXCollections.observableList(wraps));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refreshTable();
        resIdCol.setCellValueFactory(new PropertyValueFactory<Wrap,Integer>("reservationId"));
        userCol.setCellValueFactory(new PropertyValueFactory<Wrap,String>("reservationUsername"));
        titleCol.setCellValueFactory(new PropertyValueFactory<Wrap,String>("movieTitle"));
        dateCol.setCellValueFactory(new PropertyValueFactory<Wrap,String>("screeningDate"));
        seatsCol.setCellValueFactory(new PropertyValueFactory<Wrap,String>("seatsAsString"));
        roomCol.setCellValueFactory(new PropertyValueFactory<Wrap,Integer>("room"));
        actionsCol.setCellFactory(param -> new TableCell<>() {
            private final Button deleteBtn = new Button("Delete");
            private final Button editBtn = new Button("Edit");

            {
                deleteBtn.setOnAction(event -> {
                    Wrap w = getTableView().getItems().get(getIndex());
                    //delete(w);
                    refreshTable();
                });

                editBtn.setOnAction(event -> {
                    Wrap w = getTableView().getItems().get(getIndex());
                    //edit(w);
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
