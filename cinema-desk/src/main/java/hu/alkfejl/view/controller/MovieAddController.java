package hu.alkfejl.view.controller;

import hu.alkfejl.controller.CinemaController;
import hu.alkfejl.model.Actor;
import hu.alkfejl.model.Movie;
import hu.alkfejl.utils.Utils;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class MovieAddController implements Initializable {
    private Movie m = new Movie();

    @FXML
    private TextField titleField;
    @FXML
    private TextField lengthField;
    @FXML
    private TextField directorField;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private ChoiceBox<String> ratingChoice;
    @FXML
    private Button fileUpload;
    @FXML
    private TextArea actorsArea;
    @FXML
    private Button cancelButton;
    @FXML
    private ImageView coverView;

    private Image coverImage;
    private File coverFile;
    private StringProperty coverString = new SimpleStringProperty();
    private List<String> oldActors = new ArrayList<>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ratingChoice.getItems().removeAll(ratingChoice.getItems());
        ratingChoice.getItems().addAll("U", "PG", "12A", "15", "18", "R18");

        titleField.textProperty().bindBidirectional(m.titleProperty());
        lengthField.textProperty().bindBidirectional(m.lengthProperty().asObject(),new IntegerStringConverter());
        directorField.textProperty().bindBidirectional(m.directorProperty());
        descriptionArea.textProperty().bindBidirectional(m.descriptionProperty());
        ratingChoice.valueProperty().bindBidirectional(m.ratingProperty());
        coverString.bindBidirectional(m.coverProperty());

        ratingChoice.getSelectionModel().select("U");
    }
    public void initMovie(Movie m){
        m.copyTo(this.m);
        this.oldActors = CinemaController.getInstance().listActorNamesOfMovie(m.getId());
        this.actorsArea.textProperty().setValue(String.join(",",this.oldActors));
        this.coverView.setImage(Utils.decodeBase64(m.getCover()));
    }
    @FXML
    public void upload() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Upload a movie cover");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png", "*.gif", "*.bmp");
        fileChooser.getExtensionFilters().add(filter);
        this.coverFile = fileChooser.showOpenDialog(fileUpload.getScene().getWindow());
        this.coverImage = new Image(coverFile.toURI().toString());
        coverView.setDisable(false);
        coverView.setImage(coverImage);
    }
    @FXML
    public void enlarge(){
        Pane parent = new Pane();
        ImageView enlarged = new ImageView(coverImage);
        enlarged.setFitHeight(coverImage.getHeight()*0.7);
        enlarged.setFitWidth(coverImage.getWidth()*0.7);
        parent.getChildren().add(enlarged);
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    @FXML
    public void submit() {
        if (titleField.getText().isEmpty()) {
            Utils.showWarning("Title cannot be empty");
            return;
        }
        if (lengthField.getText().isEmpty()) {
            Utils.showWarning("Length cannot be empty");
            return;
        } else {
            if (!lengthField.getText().matches("[0-9]+")) {
                Utils.showWarning("Length must only contain numbers");
                return;
            }
        }
        if (directorField.getText().isEmpty()) {
            Utils.showWarning("Director cannot be empty");
            return;
        }
        if(coverFile!=null) {
            this.coverString = new SimpleStringProperty(Utils.encodeBase64(coverFile));
            m.setCover(this.coverString.getValue());
        }
        String[] actorsArray = actorsArea.getText().split(",");
        boolean res = false;
        if(m.getId()==0) {
            CinemaController.getInstance().addMovie(m);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Integer lastId = CinemaController.getInstance().lastMovieId();
            for (String actor : actorsArray) {
                res =  CinemaController.getInstance().addActor(new Actor(lastId, actor));
            }
        }
        else{
            if(oldActors.containsAll(Arrays.asList(actorsArray)) && Arrays.asList(actorsArray).containsAll(oldActors)){
                res = CinemaController.getInstance().updateMovie(m.getId(),m);
            }
            else{
                for(String actor : actorsArray){
                    if(!oldActors.contains(actor)){
                        res = CinemaController.getInstance().addActor(new Actor(m.getId(),actor));
                    }
                }
                for(String actor: oldActors){
                    if(!Arrays.asList(actorsArray).contains(actor)){
                        res = CinemaController.getInstance().removeActor(new Actor(m.getId(),actor));
                    }
                }
            }
        }
        if (res) {
            cancel();
        }
        else{
            Utils.showWarning("Failed to save");
        }
    }

    @FXML
    public void cancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

}
