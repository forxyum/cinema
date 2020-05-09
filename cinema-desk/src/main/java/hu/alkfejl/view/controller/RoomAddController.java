package hu.alkfejl.view.controller;

import hu.alkfejl.controller.CinemaController;
import hu.alkfejl.model.Movie;
import hu.alkfejl.model.Room;
import hu.alkfejl.utils.Utils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class RoomAddController implements Initializable {
    @FXML
    private TextField rowsField;

    @FXML
    private TextField columnsField;

    @FXML
    private Button cancelButton;

    private Room r = new Room();

    @FXML
    void cancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    public void initRoom(Room r){
        r.copyTo(this.r);
        System.out.println(this.r);
    }

    @FXML
    void submit() {
        if (rowsField.getText().isEmpty()) {
            Utils.showWarning("Rows cannot be empty");
        }
        else{
            if(Integer.parseInt(rowsField.getText())<=0){
                Utils.showWarning("The number of rows must be larger than 0");
            }
            else if(!rowsField.getText().matches("^[0-9]+$")){
                Utils.showWarning("The number of rows must be a number (duh)");
            }
        }
        if (columnsField.getText().isEmpty()) {
            Utils.showWarning("Columns cannot be empty");
        }
        else{
            if(Integer.parseInt(columnsField.getText())<=0){
                Utils.showWarning("The number of columns must be larger than 0");
            }
            else if(!columnsField.getText().matches("^[0-9]+$")){
                Utils.showWarning("The number of columns must be a number (duh)");
            }
        }
        boolean res = false;
        if(r.getId()==0){
            res = CinemaController.getInstance().addRoom(r);
        }
        else{
            res = CinemaController.getInstance().updateRoom(r.getId(),r);
        }
        if(res){
            cancel();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        rowsField.textProperty().bindBidirectional(r.rowsProperty().asObject(),new IntegerStringConverter());
        columnsField.textProperty().bindBidirectional(r.columnsProperty().asObject(),new IntegerStringConverter());
    }
}
