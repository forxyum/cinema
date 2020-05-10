package hu.alkfejl.view.controller;

import hu.alkfejl.model.Screening;
import hu.alkfejl.model.ScreeningWrap;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class ScreeningListController implements Initializable {
    @FXML
    private TableView<ScreeningWrap> screeningTable;

    @FXML
    private TableColumn<ScreeningWrap, String> Title;

    @FXML
    private TableColumn<ScreeningWrap, String> dateCol;

    @FXML
    private TableColumn<ScreeningWrap, Integer> roomCol;

    @FXML
    private TableColumn<ScreeningWrap, Void> actionsCol;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
