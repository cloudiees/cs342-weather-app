import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import weather.Period;

import java.util.ArrayList;
import java.util.Calendar;

public class HomeScene extends SceneAbstract{
    // Initializes the home scene
    Button infoButton;
    Scene home;
    TextField temperature;
    TextArea briefDesc, precipChance;
    ArrayList<Period> forecast;
    ImageView imageViewRain, imageViewWeather;
    SettingsScene settings;
    String[] days, months;
    ThreeDayScene threeDay;
    DetailedScene detailed;
    HomeHeader header;

    public HomeScene(ArrayList<Period> forecast, Stage primaryStage){
        mode = 0;
        String[][] temp = innitStringForTimes();
        days = temp[1];
        months = temp[0];
        this.forecast = forecast;
        this.primaryStage = primaryStage;
    }

    public class HomeHeader extends Header{
        public Button settingsButton;
        // Constructor
        public HomeHeader(String headerTxt) {
            // Calling parent constructor
            super(headerTxt);
        }

        @Override
        public void rightItemAndCreate() {
            // Generating the settings button
            settingsButton = new Button();
            // Grabbing the image for the settings button
            ImageView settingsImgView = new ImageView(new Image(getClass().getResourceAsStream("settings.png")));
            // Setting the size for the settings button
            settingsImgView.setFitHeight(40);
            settingsImgView.setFitWidth(40);
            // Setting the image for the settings button
            settingsButton.setGraphic(settingsImgView);
            // Creating the HBox for all elements
            hbox = new HBox(backButton, spacer, header,spacer2, settingsButton);
        }
    }

    public void link(ThreeDayScene threeDay, DetailedScene detailed, SettingsScene settings){
        this.threeDay = threeDay;
        this.detailed = detailed;
        this.settings = settings;
    }

    private class InfoButton extends Button {
        InfoButton(Stage primaryStage, int width, int height, int period) {
			super();
			ImageView imageView = new ImageView(getImage("info.png"));
			imageView.setFitHeight(width);
			imageView.setFitWidth(height);
			this.setGraphic(imageView);
			this.setOnAction(e->{
                detailed.prevScene = home;
				detailed.reloadScene(0);
				primaryStage.setScene(detailed.detailed);
			});
		}
	}

    public void innitScene(){
        // None of these objects will be adjusted at all outside of initialization
        HBox weatherHBoxHome, precipHBoxHome, descHBoxHome, footerHBoxHome, weatherStyleHBoxHome, descStyleHBoxHome;
        VBox mainVboxHome;
        Button threeDayButton;

        // Making the header menu
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(forecast.get(0).startTime);
        String headerTxt = days[calendar.get(Calendar.DAY_OF_WEEK)] + ", " + months[calendar.get(Calendar.MONTH)] + " " + calendar.get(Calendar.DATE);
        header = new HomeHeader(headerTxt);
        header.backButton.setOnAction(e->{
            Platform.exit();
            System.exit(0);
        });

        // Making settings button
        header.settingsButton.setOnAction(e->{
            primaryStage.setScene(settings.settings);
        });

        // Setting up main temp stuff
        temperature = new TextField();
        temperature.setEditable(false);
        temperature.setText(forecast.get(0).temperature + "°F");
        temperature.getStyleClass().add("temp");
        temperature.setPrefColumnCount(temperature.getText().length() / 2 + 1);

        imageViewWeather = new ImageView(getImage(getIcon(0, forecast)));
        imageViewWeather.setFitHeight(125);
        imageViewWeather.setFitWidth(125);
        weatherStyleHBoxHome = new HBox(10, temperature, imageViewWeather);
        weatherStyleHBoxHome.setPadding(new Insets(10));
        weatherStyleHBoxHome.setAlignment(Pos.CENTER);
        weatherStyleHBoxHome.getStyleClass().add("elevatedBox");
        weatherHBoxHome = new HBox(getSpacer(), weatherStyleHBoxHome,getSpacer());

        // Setting up precipitation stuff
        precipChance = new TextArea();
        precipChance.setEditable(false);
        precipChance.setText(forecast.get(0).probabilityOfPrecipitation.value + "% chance of precipitation");
        precipChance.setPrefColumnCount(precipChance.getText().length() / 3);
        precipChance.setPrefHeight(50);
        precipChance.setWrapText(true);
        imageViewRain = new ImageView(getImage("water.png"));
        imageViewRain.setFitHeight(30);
        imageViewRain.setFitWidth(30);
        precipHBoxHome = new HBox(getSpacer(), imageViewRain, precipChance, getSpacer());
        precipHBoxHome.setAlignment(Pos.CENTER);

        // Setting up brief description stuff
        briefDesc = new TextArea();
        briefDesc.setEditable(false);
        briefDesc.setText(forecast.get(0).shortForecast);
        briefDesc.setWrapText(true);
        briefDesc.setPrefWidth(200);
        briefDesc.setPrefHeight(50);

        infoButton = new InfoButton(primaryStage, 20, 20, 0);
        infoButton.getStyleClass().add("elevatedButton");

        descStyleHBoxHome = new HBox(15, briefDesc, infoButton);
        descStyleHBoxHome.setAlignment(Pos.TOP_CENTER);
        descStyleHBoxHome.setPadding(new Insets(10));
        descStyleHBoxHome.getStyleClass().add("elevatedBox");
        descHBoxHome = new HBox(getSpacer(), descStyleHBoxHome, getSpacer());

        // Setting up main VBox
        mainVboxHome = new VBox(15, weatherHBoxHome, precipHBoxHome, descHBoxHome);
        mainVboxHome.setAlignment(Pos.CENTER);

        // Setting up 3 day forecast button
        threeDayButton = new Button("Three Day Forecast");
        threeDayButton.setOnAction(e->{
            primaryStage.setScene(threeDay.threeDay);
        });
        footerHBoxHome = new HBox(getSpacer(), threeDayButton);
        footerHBoxHome.setPadding(new Insets(10));
        footerHBoxHome.setAlignment(Pos.CENTER);

        // Setting up root
        root = new BorderPane();
        root.setTop(header.hbox);
        root.setCenter(mainVboxHome);
        root.setBottom(footerHBoxHome);
        root.getStyleClass().add("bp");
        home = new Scene(root, 800, 500);
        home.getStylesheets().add(getClass().getResource("main.css").toExternalForm());
    }

    // Reloads any possibly changed data for the home scene
    public void reloadScene(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(forecast.get(0).startTime);
        header.header.setText(days[calendar.get(Calendar.DAY_OF_WEEK)] + ", " + months[calendar.get(Calendar.MONTH)] + " " + calendar.get(Calendar.DATE));
        temperature.setText(forecast.get(0).temperature + "°F");
        precipChance.setText(forecast.get(0).probabilityOfPrecipitation.value + "% chance of\nprecipitation");
        imageViewWeather.setImage(getImage(getIcon(0, forecast)));
        briefDesc.setText(forecast.get(0).shortForecast);
    }

}
