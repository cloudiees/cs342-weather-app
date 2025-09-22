import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import weather.Period;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class SettingsScene extends SceneAbstract{
    HomeScene home;
    Scene settings;
    ThreeDayScene threeDay;
    DetailedScene detailed;
    Double lat, lon;
    SettingsHeader header;

    public SettingsScene(Double lat, Double lon, Stage primaryStage) {
        this.lat = lat;
        this.lon = lon;
        mode = 0;
        this.primaryStage = primaryStage;
    }

    public class SettingsHeader extends Header{
        public Button lmdmButton;
        // Constructor
        public SettingsHeader(String headerTxt) {
            // Calling parent constructor
            super(headerTxt);
        }

        @Override
        public void rightItemAndCreate() {
            // Generating the light mode/dark mode button
            lmdmButton = new Button();
            // Grabbing an image for the light/dark mode button
            ImageView imageLD = new ImageView(new Image(getClass().getResourceAsStream("night.png")));
            // Setting the size of the light/dark mode button
            imageLD.setFitHeight(40);
            imageLD.setFitWidth(40);
            // Setting the image off the light/dark mode button
            lmdmButton.setGraphic(imageLD);
            // Creating the HBox for all elements
            hbox = new HBox(backButton, spacer, header, spacer2, lmdmButton);
        }
    }

    public void link(HomeScene home, DetailedScene detailed, ThreeDayScene threeDay) {
        this.home = home;
        this.detailed = detailed;
        this.threeDay = threeDay;
    }

    private void darkMode(){
        // Setting styles
        String darkMode = "-bgColor: #121212;-elevatedColor: #222222;-elevatedColor2: #3F3F3F;-elevatedColor3: #5F5F5F;-fontColor: white;";
        home.root.setStyle(darkMode);
        root.setStyle(darkMode);
        threeDay.root.setStyle(darkMode);
        detailed.root.setStyle(darkMode);
        // Getting images
        Image rainImg = getImage("water.png");
        Image backImg = getImage("back.png");
        Image settingsImg = getImage("settings.png");
        Image infoImg = getImage("info.png");
        Image windImg = getImage("wind.png");
        Image sunImg = getImage("sunny.png");
        Image moonImg = getImage("night.png");
        ImageView temp;
        // Putting the images in place
        temp = new ImageView(backImg);
        temp.setFitHeight(40);
        temp.setFitWidth(40);
        home.header.backButton.setGraphic(temp);
        home.imageViewRain.setImage(rainImg);
        temp = new ImageView(settingsImg);
        temp.setFitHeight(40);
        temp.setFitWidth(40);
        home.header.settingsButton.setGraphic(temp);
        temp = new ImageView(infoImg);
        temp.setFitHeight(20);
        temp.setFitWidth(20);
        home.infoButton.setGraphic(temp);

        detailed.imageViewWind.setImage(windImg);
        detailed.imageViewRain.setImage(rainImg);
        temp = new ImageView(backImg);
        temp.setFitHeight(40);
        temp.setFitWidth(40);
        detailed.header.backButton.setGraphic(temp);

        for(ImageView i : threeDay.moonIV){
            i.setImage(moonImg);
        }
        for(ImageView i : threeDay.sunIV){
            i.setImage(sunImg);
        }
        for(ImageView[] i : threeDay.rainIV){
            for(ImageView j : i){
                j.setImage(rainImg);
            }
        }
        temp = new ImageView(backImg);
        temp.setFitHeight(40);
        temp.setFitWidth(40);
        threeDay.header.backButton.setGraphic(temp);

        temp = new ImageView(backImg);
        temp.setFitHeight(40);
        temp.setFitWidth(40);
        header.backButton.setGraphic(temp);
    }

    private void lightMode(){
        // Setting styles
        String lightMode = "-bgColor: white;-elevatedColor: #DDDDDD;-elevatedColor2: #BFBFBF;-elevatedColor3: #8F8F8F;-fontColor: black;";
        home.root.setStyle(lightMode);
        root.setStyle(lightMode);
        threeDay.root.setStyle(lightMode);
        detailed.root.setStyle(lightMode);
        // Getting images
        Image rainImg = getImage("Lwater.png");
        Image backImg = getImage("Lback.png");
        Image settingsImg = getImage("Lsettings.png");
        Image infoImg = getImage("Linfo.png");
        Image windImg = getImage("Lwind.png");
        Image sunImg = getImage("Lsunny.png");
        Image moonImg = getImage("Lnight.png");
        ImageView temp;
        // Putting the images in place
        temp = new ImageView(backImg);
        temp.setFitHeight(40);
        temp.setFitWidth(40);
        home.header.backButton.setGraphic(temp);
        home.imageViewRain.setImage(rainImg);
        temp = new ImageView(settingsImg);
        temp.setFitHeight(40);
        temp.setFitWidth(40);
        home.header.settingsButton.setGraphic(temp);
        temp = new ImageView(infoImg);
        temp.setFitHeight(20);
        temp.setFitWidth(20);
        home.infoButton.setGraphic(temp);

        detailed.imageViewWind.setImage(windImg);
        detailed.imageViewRain.setImage(rainImg);
        temp = new ImageView(backImg);
        temp.setFitHeight(40);
        temp.setFitWidth(40);
        detailed.header.backButton.setGraphic(temp);

        for(ImageView i : threeDay.moonIV){
            i.setImage(moonImg);
        }
        for(ImageView i : threeDay.sunIV){
            i.setImage(sunImg);
        }
        for(ImageView[] i : threeDay.rainIV){
            for(ImageView j : i){
                j.setImage(rainImg);
            }
        }
        temp = new ImageView(backImg);
        temp.setFitHeight(40);
        temp.setFitWidth(40);
        threeDay.header.backButton.setGraphic(temp);

        temp = new ImageView(backImg);
        temp.setFitHeight(40);
        temp.setFitWidth(40);
        header.backButton.setGraphic(temp);
    }

    // Initializes the settings scene
    public void innitScene(){
        // None of these objects will be adjusted outside of initialization
        Button submit;
        TextField latLonTxt, latLonTxt2, newLat, newLon, feedback;
        HBox latLonHBox, newLatLonStyleHBox;
        VBox currLatLonStyleVBox, latLonVBox;

        // Setting up header

        header = new SettingsHeader("Settings");

        header.backButton.setOnAction(e->{
            primaryStage.setScene(home.home);
        });

        header.lmdmButton.setOnAction(e->{
            ImageView imageView;
            if(mode == 0){
                mode = 1;
                home.mode = mode;
                threeDay.mode = mode;
                detailed.mode = mode;
                lightMode();
                imageView = new ImageView(getImage("Lsunny.png"));
            }
            else{
                mode = 0;
                home.mode = mode;
                threeDay.mode = mode;
                detailed.mode = mode;
                darkMode();
                imageView = new ImageView(getImage("night.png"));
            }
            imageView.setFitHeight(40);
            imageView.setFitWidth(40);
            header.lmdmButton.setGraphic(imageView);
            // Reloading scenes
            home.reloadScene();
            threeDay.reloadScene();
        });

        // Setting up curr location
        latLonTxt2 = new TextField("Current latitude & longitude");
        latLonTxt2.setEditable(false);
        latLonTxt2.getStyleClass().add("miniHeader");
        latLonTxt2.setPrefColumnCount(latLonTxt2.getText().length() / 2);
        latLonTxt = new TextField(lat + ", " + lon);
        latLonTxt.setEditable(false);
        latLonTxt.setStyle("-fx-font-weight: bold;");
        currLatLonStyleVBox = new VBox(10, latLonTxt2, latLonTxt);
        currLatLonStyleVBox.setMinWidth(latLonTxt2.getWidth());
        currLatLonStyleVBox.setPadding(new Insets(10));
        currLatLonStyleVBox.getStyleClass().add("elevatedBox");
        currLatLonStyleVBox.setAlignment(Pos.CENTER);

        // Setting up input fields for new lat+lon
        newLat = new TextField();
        newLat.setPromptText("Enter new latitude");
        newLat.getStyleClass().add("inputTxt");
        newLon = new TextField();
        newLon.setPromptText("Enter new longitude");
        newLon.getStyleClass().add("inputTxt");
        newLatLonStyleHBox = new HBox(20, newLat, newLon);
        newLatLonStyleHBox.setPadding(new Insets(10));
        newLatLonStyleHBox.getStyleClass().add("elevatedBox");
        newLatLonStyleHBox.setAlignment(Pos.CENTER);

        // Feedback field
        feedback = new TextField();
        feedback.setEditable(false);

        // Submit button for the lat+long
        submit = new Button("Submit");
        submit.setOnAction(e->{
            String nLatStr = newLat.getText(), nLonStr = newLon.getText();
            Double nLat, nLon;
            // Checking + converting if the values inputted are doubles
            try {
                nLat = Double.valueOf(nLatStr);
                nLon = Double.valueOf(nLonStr);
            } catch (Exception ex) {
                feedback.setText("Invalid latitude or longitude");
                feedback.setStyle("-fx-text-fill: red;");
                newLat.clear();
                newLon.clear();
                return;
            }
            // Checking if the values are valid latitudes and longitudes
            if(nLat >= -90 && nLat <= 90 && nLon >= -180 && nLon <= 180){
                feedback.setText("Please wait");
                feedback.setStyle("-fx-text-fill: orange;");
                // Use multithreading as to not make the GUI lag
                Thread loadForecastThread = new Thread(() -> {
                    Thread showLife = new Thread(() -> {
                       int i = 0;
                       while(true){
                           try {
                               sleep(500);
                           } catch (InterruptedException ex) {
                               throw new RuntimeException(ex);
                           }
                           StringBuilder toShow = new StringBuilder("Please wait");
                           for(int j = 0; j < i; j++){
                               toShow.append(".");
                           }
                           i++;
                           if(i > 3){i = 0;}
                           feedback.setText(toShow.toString());
                       }
                    });
                    showLife.start();
                    header.backButton.setDisable(true);
                    newLat.setDisable(true);
                    newLon.setDisable(true);
                    submit.setDisable(true);
                    // Loading the forecast
                    ArrayList<Period> nForecast;
                    try {
                        nForecast = JavaFX.loadForecast(nLat, nLon);
                    } catch (Exception ex) {
                        showLife.stop();
                        showLife = null;
                        feedback.setText("Try a different longitude and latitude");
                        feedback.setStyle("-fx-text-fill: red;");
                        newLat.clear();
                        newLon.clear();
                        header.backButton.setDisable(false);
                        submit.setDisable(false);
                        newLat.setDisable(false);
                        newLon.setDisable(false);
                        return;
                    }
                    // Updating necessary objects
                    showLife.stop();
                    showLife = null;
                    home.forecast = nForecast;
                    threeDay.forecast = nForecast;
                    detailed.forecast = nForecast;
                    feedback.setText("Successfully submitted");
                    feedback.setStyle("-fx-text-fill: green;");
                    lat = nLat;
                    lon = nLon;
                    latLonTxt.setText(lat + ", " + lon);
                    newLat.clear();
                    newLon.clear();
                    home.reloadScene();
                    threeDay.reloadScene();
                    submit.setDisable(false);
                    header.backButton.setDisable(false);
                    newLat.setDisable(false);
                    newLon.setDisable(false);
                });
                loadForecastThread.start();
                loadForecastThread = null;
            }
            else{
                feedback.setText("Invalid latitude or longitude");
                feedback.setStyle("-fx-text-fill: red;");
                newLat.clear();
                newLon.clear();
            }
        });

        // Setting up warning
        TextField warning = new TextField("Warning: This lags a lot so be patient");
        warning.setEditable(false);
        warning.setMinWidth(420);
        warning.setStyle("-fx-font-weight: bold;-fx-text-fill: red;");

        // Setting up main VBox with spacing
        Rectangle invisSpacer = new Rectangle(75, 75);
        invisSpacer.getStyleClass().add("invisSpacer");
        latLonVBox = new VBox(10, warning,currLatLonStyleVBox, newLatLonStyleHBox, submit, feedback, invisSpacer);
        latLonVBox.setAlignment(Pos.CENTER);
        latLonHBox = new HBox(getSpacer(), latLonVBox, getSpacer());
        latLonHBox.setMaxHeight(50);
        root = new BorderPane();
        root.setTop(header.hbox);
        root.setCenter(latLonHBox);

        // So feedback doesnt remain after leaving the page
        header.backButton.addEventHandler(ActionEvent.ACTION, e->{
            feedback.clear();
        });

        root.getStyleClass().add("bp");
        settings = new Scene(root, 800, 500);
        settings.getStylesheets().add(getClass().getResource("main.css").toExternalForm());
    }

}
