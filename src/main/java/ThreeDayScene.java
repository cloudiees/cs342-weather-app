import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import weather.Period;

import java.util.ArrayList;
import java.util.Calendar;

public class ThreeDayScene extends SceneAbstract{
    Scene threeDay;
    ImageView[][] rainIV;
    ImageView[] sunIV, moonIV, imageViewWeather;
    TextField[] dayNames;
    TextField[][] temperatures, precip;
    ArrayList<Period> forecast;
    DetailedScene detailed;
    HomeScene home;
    String[] days, months;
    Header header;

    public ThreeDayScene(ArrayList<Period> forecast, Stage primaryStage) {
        mode = 0;
        String[][] temp = innitStringForTimes();
        days = temp[1];
        months = temp[0];
        this.forecast = forecast;
        this.primaryStage = primaryStage;
    }


    public void link(HomeScene home, DetailedScene detailed){
        this.home = home;
        this.detailed = detailed;
    }

    // Initializes the threeDay scene
    public void innitScene() {
        // None of these objects will be adjusted outside of initialization
        HBox allDays;
        Rectangle spacerV;
        HBox[] dnHbox;
        HBox[][] precipHBox;
        VBox spacingVBox;
        VBox[] daysVBox;
        VBox[][] dnVBox;
        TextField instruc;
        int[] forecastIdxs;

        // Setting up header
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(forecast.get(0).startTime);
        String headerTxt = days[calendar.get(Calendar.DAY_OF_WEEK)] + ", " + months[calendar.get(Calendar.MONTH)] + " " + calendar.get(Calendar.DATE);
        header = new Header(headerTxt);
        header.backButton.setOnAction(e->{
            primaryStage.setScene(home.home);
        });

        // Setting up needed arrays
        rainIV = new ImageView[3][2];
        sunIV = new ImageView[3];
        moonIV = new ImageView[3];
        dayNames = new TextField[3];
        precip = new TextField[3][2];
        imageViewWeather = new ImageView[3];
        temperatures = new TextField[3][2];
        forecastIdxs = new int[3];
        dnHbox = new HBox[3];
        precipHBox = new HBox[3][2];
        daysVBox = new VBox[3];
        dnVBox = new VBox[3][2];

        // Getting the next 3 forecast indexes
        forecastIdxs[0] = 1;
        while(forecast.get(0).startTime.getDay() == forecast.get(forecastIdxs[0]).startTime.getDay()){
            forecastIdxs[0]++;
        }
        forecastIdxs[1] = forecastIdxs[0];
        while(forecast.get(forecastIdxs[0]).startTime.getDay() == forecast.get(forecastIdxs[1]).startTime.getDay()){
            forecastIdxs[1]++;
        }
        forecastIdxs[2] = forecastIdxs[1];
        while(forecast.get(forecastIdxs[1]).startTime.getDay() == forecast.get(forecastIdxs[2]).startTime.getDay()){
            forecastIdxs[2]++;
        }

        Image imageRain = getImage("water.png");
        Image imageSun = getImage("sunny.png");
        Image imageNight = getImage("night.png");
        // Filling all the object arrays
        for(int i = 0; i < 3; i++){
            rainIV[i][0] = new ImageView(imageRain);
            rainIV[i][0].setFitHeight(15);
            rainIV[i][0].setFitWidth(15);

            rainIV[i][1] = new ImageView(imageRain);
            rainIV[i][1].setFitHeight(15);
            rainIV[i][1].setFitWidth(15);

            sunIV[i] = new ImageView(imageSun);
            sunIV[i].setFitHeight(25);
            sunIV[i].setFitWidth(25);

            moonIV[i] = new ImageView(imageNight);
            moonIV[i].setFitHeight(25);
            moonIV[i].setFitWidth(25);

            calendar.setTime(forecast.get(forecastIdxs[i]).startTime);
            dayNames[i] = new TextField(days[calendar.get(Calendar.DAY_OF_WEEK)]);
            dayNames[i].getStyleClass().add("miniHeader");
            dayNames[i].setPrefColumnCount(dayNames[i].getText().length() / 2 + 2);
            dayNames[i].setEditable(false);

            temperatures[i][0] = new TextField(forecast.get(forecastIdxs[i]).temperature + "°F");
            temperatures[i][0].setPrefColumnCount(temperatures[i][0].getText().length() / 2 + 1);
            temperatures[i][0].setEditable(false);
            temperatures[i][0].getStyleClass().add("textEmph");

            temperatures[i][1] = new TextField(forecast.get(forecastIdxs[i] + 1).temperature + "°F");
            temperatures[i][1].setPrefColumnCount(temperatures[i][1].getText().length() / 2 + 1);
            temperatures[i][1].setEditable(false);
            temperatures[i][1].getStyleClass().add("textEmph");

            imageViewWeather[i] = new ImageView(getImage(getIcon(forecastIdxs[i], forecast)));
            imageViewWeather[i].setFitHeight(50);
            imageViewWeather[i].setFitWidth(50);

            precip[i] = new TextField[2];
            precip[i][0] = new TextField(forecast.get(forecastIdxs[i]).probabilityOfPrecipitation.value + "%");
            precip[i][0].setEditable(false);
            precip[i][0].setPrefColumnCount(precip[i][0].getText().length() / 2 + 2);
            precip[i][0].getStyleClass().add("leftTxt");
            precip[i][1] = new TextField(forecast.get(forecastIdxs[i] + 1).probabilityOfPrecipitation.value + "%");
            precip[i][1].setEditable(false);
            precip[i][1].setPrefColumnCount(precip[i][1].getText().length() / 2 + 2);
            precip[i][1].getStyleClass().add("leftTxt");
        }

        // Filling up all the HBox/VBox arrays
        for(int i = 0; i < dnHbox.length; i++){
            precipHBox[i][0] = new HBox(rainIV[i][0], precip[i][0]);
            precipHBox[i][1] = new HBox(rainIV[i][1], precip[i][1]);
            precipHBox[i][0].setPadding(new Insets(0, 0,5, 5));
            precipHBox[i][1].setPadding(new Insets(0, 0,5, 5));
            precipHBox[i][0].setAlignment(Pos.CENTER);
            precipHBox[i][1].setAlignment(Pos.CENTER);

            dnVBox[i][0] = new VBox(sunIV[i], temperatures[i][0], precipHBox[i][0]);
            dnVBox[i][1] = new VBox(moonIV[i], temperatures[i][1], precipHBox[i][1]);
            dnVBox[i][0].setAlignment(Pos.CENTER);
            dnVBox[i][1].setAlignment(Pos.CENTER);

            Rectangle divider = new Rectangle(2, 60);
            divider.getStyleClass().add("divider");

            dnHbox[i] = new HBox(dnVBox[i][0],divider,dnVBox[i][1]);
            dnHbox[i].setAlignment(Pos.CENTER);

            daysVBox[i] = new VBox(10, dayNames[i], imageViewWeather[i], dnHbox[i]);
            daysVBox[i].setAlignment(Pos.CENTER);
            daysVBox[i].setPadding(new Insets(10));
            daysVBox[i].getStyleClass().add("elevatedBoxClickable");
            daysVBox[i].setPrefWidth(200);
            daysVBox[i].setPrefHeight(300);
        }

        // Adding event handlers to make the chips clickable
        daysVBox[0].setOnMouseClicked(actionEvent -> {
            detailed.reloadScene(forecastIdxs[0]);
            primaryStage.setScene(detailed.detailed);
            detailed.prevScene = this.threeDay;
        });
        daysVBox[1].setOnMouseClicked(actionEvent -> {
            detailed.reloadScene(forecastIdxs[1]);
            primaryStage.setScene(detailed.detailed);
            detailed.prevScene = this.threeDay;
        });
        daysVBox[2].setOnMouseClicked(actionEvent -> {
            detailed.reloadScene(forecastIdxs[2]);
            primaryStage.setScene(detailed.detailed);
            detailed.prevScene = this.threeDay;
        });

        // Setting up the main hbox with spacers
        allDays = new HBox(getSpacer(), daysVBox[0], getSpacer(), daysVBox[1], getSpacer(), daysVBox[2], getSpacer());

        // Setting up the main vbox so the main hbox doesnt stretch all the way and so there is instructions for the user
        spacerV = new Rectangle(10,10);
        spacerV.getStyleClass().add("invisSpacer");
        instruc = new TextField("Click a day for more details");
        instruc.setEditable(false);
        instruc.setPrefColumnCount(instruc.getLength() / 2);
        instruc.getStyleClass().add("miniHeader");
        spacingVBox = new VBox(instruc, allDays, spacerV);
        spacingVBox.setAlignment(Pos.CENTER);

        // Setting up the root
        root = new BorderPane();
        root.setTop(header.hbox);
        root.setCenter(spacingVBox);
        root.getStyleClass().add("bp");
        threeDay = new Scene(root, 800, 500);
        threeDay.getStylesheets().add(getClass().getResource("main.css").toExternalForm());
    }

    // Reloads 3-day forecast when info possibly changes
    public void reloadScene(){
        Calendar calendar = Calendar.getInstance();
        int[] forecastIdxs = new int[3];
        forecastIdxs[0] = 1;
        while(forecast.get(0).startTime.getDay() == forecast.get(forecastIdxs[0]).startTime.getDay()){
            forecastIdxs[0]++;
        }
        forecastIdxs[1] = forecastIdxs[0];
        while(forecast.get(forecastIdxs[0]).startTime.getDay() == forecast.get(forecastIdxs[1]).startTime.getDay()){
            forecastIdxs[1]++;
        }
        forecastIdxs[2] = forecastIdxs[1];
        while(forecast.get(forecastIdxs[1]).startTime.getDay() == forecast.get(forecastIdxs[2]).startTime.getDay()){
            forecastIdxs[2]++;
        }
        calendar.setTime(forecast.get(0).startTime);
        header.header.setText(days[calendar.get(Calendar.DAY_OF_WEEK)] + ", " + months[calendar.get(Calendar.MONTH)] + " " + calendar.get(Calendar.DATE));
        for(int i = 0; i < 3; i++){
            calendar.setTime(forecast.get(forecastIdxs[i]).startTime);
            dayNames[i].setText(days[calendar.get(Calendar.DAY_OF_WEEK)]);

            temperatures[i][0].setText(forecast.get(forecastIdxs[i]).temperature + "°F");

            temperatures[i][1].setText(forecast.get(forecastIdxs[i] + 1).temperature + "°F");

            imageViewWeather[i].setImage(getImage(getIcon(forecastIdxs[i], forecast)));

            precip[i][0].setText(forecast.get(forecastIdxs[i]).probabilityOfPrecipitation.value + "%");
            precip[i][1].setText(forecast.get(forecastIdxs[i] + 1).probabilityOfPrecipitation.value + "%");
        }
    }

}
