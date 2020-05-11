package hu.alkfejl.view.controller;

import hu.alkfejl.controller.CinemaController;
import hu.alkfejl.model.*;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class ScreeningListController implements Initializable {
    @FXML
    private TableView<ScreeningWrap> screeningTable;

    @FXML
    private TableColumn<ScreeningWrap, String> titleCol;

    @FXML
    private TableColumn<ScreeningWrap, String> dateCol;

    @FXML
    private TableColumn<ScreeningWrap, Integer> roomCol;

    @FXML
    private TableColumn<ScreeningWrap, Void> actionsCol;

    private void refreshTable(){
        List<Screening> screenings = CinemaController.getInstance().listAllScreenings();
        List<ScreeningWrap> wraps = new ArrayList<>();
        for(Screening s : screenings){
            String title = CinemaController.getInstance().movieTitleByScreeningId(s.getId());
            wraps.add(new ScreeningWrap(s,title));
        }
        screeningTable.setItems(FXCollections.observableList(wraps));
    }
    private void delete(Screening s){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this reservation?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait().ifPresent(buttonType -> {
            if(buttonType.equals(ButtonType.YES)){
                CinemaController.getInstance().removeScreening(s);
            }
        });
    }
    private void edit(ScreeningWrap w){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/hu/alkfejl/screeningAdd.fxml"));
            Parent root = loader.load();
            ScreeningAddController controller = loader.getController();
            controller.initScreening(w);
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
        refreshTable();
        titleCol.setCellValueFactory(new PropertyValueFactory<>("movieTitle"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        roomCol.setCellValueFactory(new PropertyValueFactory<>("room"));
        actionsCol.setCellFactory(param -> new TableCell<>() {
            private final Button deleteBtn = new Button("Delete");
            private final Button editBtn = new Button("Edit");
            {
                deleteBtn.setOnAction(event -> {
                    ScreeningWrap w = getTableView().getItems().get(getIndex());
                    delete(w.toScreening());
                    refreshTable();
                });

                editBtn.setOnAction(event -> {
                    ScreeningWrap w = getTableView().getItems().get(getIndex());
                    edit(w);
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
