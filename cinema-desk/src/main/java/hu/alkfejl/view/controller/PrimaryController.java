package hu.alkfejl.view.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PrimaryController implements Initializable {

    public PrimaryController() {
    }

    @FXML
    private AnchorPane tableAnchor;

    private void display(String which){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/hu/alkfejl/"+ which +"Table.fxml"));
            pane.setPrefWidth(tableAnchor.getWidth());
            pane.setPrefHeight(tableAnchor.getHeight());
            tableAnchor.getChildren().setAll(pane.getChildren());
        } catch (IOException e) {
            System.out.println("you're fucked buddy");
            e.printStackTrace();
        }
    }
    private void add(String which, String title){
        try {
            Pane pane = FXMLLoader.load(getClass().getResource("/hu/alkfejl/"+ which +"Add.fxml"));
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(pane));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.setOnHidden(event -> display(which));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void displayMovies(){
        this.display("movie");
    }
    @FXML
    public void addMovie(){
        this.add("movie","Add a new movie");
    }
    @FXML
    public void displayRooms(){
        this.display("room");
    }
    @FXML
    public void addRoom(){
        this.add("room","Add a new room");
    }
    @FXML
    public void addReservation(){
        this.add("reservation","Add a new reservation");
    }
    @FXML
    public void displayReservations(){
        this.display("reservation");
    }
    @FXML
    public void addScreening(){
        this.add("screening","Add a new screening");
    }
    @FXML
    public void displayScreenings(){
        this.display("screening");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
