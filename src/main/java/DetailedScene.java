import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import weather.Period;

import java.util.ArrayList;
import java.util.Calendar;

public class DetailedScene extends SceneAbstract{
    TextField temperature;
    Scene prevScene, detailed;
    ImageView imageViewWeather, imageViewRain, imageViewWind;
    ArrayList<Period> forecast;
    TextArea precipChance, windDesc, detDesc;
    String[] days, months;
    Header header;

    public DetailedScene(ArrayList<Period> forecast, Stage primaryStage) {
        String[][] temp = innitStringForTimes();
        days = temp[1];
        months = temp[0];
        mode = 0;
        this.forecast = forecast;
        this.primaryStage = primaryStage;
    }

    // Initializes the detailed scene
    public void innitScene(){
        // None of these objects will be adjusted outside of initialization
        Rectangle footerBlank;
        HBox weatherHBox, weatherStyleHBox, precipWindHBox, descHBox, descStyleHBox, headerHBox;
        VBox mainVBox;

        // Setting up the header
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(forecast.get(0).startTime);
        String headerTxt = days[calendar.get(Calendar.DAY_OF_WEEK)] + ", " + months[calendar.get(Calendar.MONTH)] + " " + calendar.get(Calendar.DATE);
        header = new Header(headerTxt);
        header.backButton.setOnAction(e->{
            primaryStage.setScene(prevScene);
        });
        // Setting up the temperature box
        temperature = new TextField();
        temperature.setEditable(false);
        temperature.setText(forecast.get(0).temperature + "°F");
        temperature.getStyleClass().add("temp");
        temperature.setPrefColumnCount(temperature.getText().length() / 2 + 1);

        imageViewWeather = new ImageView(getImage(getIcon(0, forecast)));
        imageViewWeather.setFitHeight(125);
        imageViewWeather.setFitWidth(125);
        weatherStyleHBox = new HBox(10, temperature, imageViewWeather);
        weatherStyleHBox.setPadding(new Insets(10));
        weatherStyleHBox.setAlignment(Pos.CENTER);
        weatherStyleHBox.getStyleClass().add("elevatedBox");
        weatherHBox = new HBox(getSpacer(), weatherStyleHBox, getSpacer());

        // Setting up the precipitation box
        precipChance = new TextArea();
        precipChance.setEditable(false);
        precipChance.setText(forecast.get(0).probabilityOfPrecipitation.value + "% chance of precipitation");
        precipChance.setPrefColumnCount(precipChance.getText().length() / 3);
        precipChance.setPrefHeight(50);
        precipChance.setWrapText(true);
        imageViewRain = new ImageView(getImage("water.png"));
        imageViewRain.setFitHeight(30);
        imageViewRain.setFitWidth(30);

        // Setting up the wind box
        windDesc = new TextArea();
        windDesc.setEditable(false);
        windDesc.setText(forecast.get(0).windSpeed + "\n" + forecast.get(0).windDirection);
        windDesc.setPrefColumnCount(precipChance.getText().length() / 4);
        windDesc.setPrefHeight(50);
        windDesc.setWrapText(true);
        imageViewWind = new ImageView(getImage("wind.png"));
        imageViewWind.setFitHeight(30);
        imageViewWind.setFitWidth(30);

        // Setting up the precipitation + wind box
        precipWindHBox = new HBox(getSpacer(), imageViewRain, precipChance, getSpacer(), imageViewWind, windDesc, getSpacer());
        precipWindHBox.setAlignment(Pos.CENTER);

        // Setting up the detailed description box
        detDesc = new TextArea();
        detDesc.setEditable(false);
        detDesc.setText(forecast.get(0).detailedForecast);
        detDesc.setWrapText(true);
        detDesc.setPrefWidth(550);
        detDesc.setPrefHeight(75);

        descStyleHBox = new HBox(15, detDesc);
        descStyleHBox.setAlignment(Pos.TOP_CENTER);
        descStyleHBox.setPadding(new Insets(10));
        descStyleHBox.getStyleClass().add("elevatedBox");
        descHBox = new HBox(getSpacer(), descStyleHBox, getSpacer());

        // Setting up the main VBox
        footerBlank = new Rectangle(8,8);
        footerBlank.getStyleClass().add("invisSpacer");
        mainVBox = new VBox(15, weatherHBox, precipWindHBox, descHBox, footerBlank);
        mainVBox.setAlignment(Pos.CENTER);

        // Setting up the root node
        root = new BorderPane();
        root.setTop(header.hbox);
        root.setCenter(mainVBox);
        root.getStyleClass().add("bp");

        detailed = new Scene(root, 800, 500);
        detailed.getStylesheets().add(getClass().getResource("main.css").toExternalForm());
    }

    // Reloads detailed whenever detailed is loaded with the requested period info
    public void reloadScene(int period){
        windDesc.setText(forecast.get(period).windSpeed + "\n" + forecast.get(period).windDirection);
        precipChance.setText(forecast.get(period).probabilityOfPrecipitation.value + "% chance of precipitation");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(forecast.get(period).startTime);
        header.header.setText(days[calendar.get(Calendar.DAY_OF_WEEK)] + ", " + months[calendar.get(Calendar.MONTH)] + " " + calendar.get(Calendar.DATE));
        imageViewWeather.setImage((getImage(getIcon(period, forecast))));
        temperature.setText(forecast.get(period).temperature + "°F");
        detDesc.setText(forecast.get(period).detailedForecast);
    }

}
