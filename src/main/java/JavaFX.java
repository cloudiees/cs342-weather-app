import javafx.application.Application;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import weather.Period;
import weather2.MyWeatherAPI;

import java.text.DateFormatSymbols;
import java.util.ArrayList;

public class JavaFX extends Application {
	private ArrayList<Period> forecast;
	private Double innitLat = 41.7988, innitLon = -87.5787;
	private HomeScene home;
	private ThreeDayScene threeDay;
	private DetailedScene detailed;
	private SettingsScene settings;

	public static void main(String[] args) {
		launch(args);
	}

	// Loads the forecast given a latitude and longitude
	public static ArrayList<Period> loadForecast(Double latitude, Double longitude){
		// Getting the forecast with latitude and longitude
		ArrayList<Period> nForecast = MyWeatherAPI.getForecastPoints(latitude, longitude);
		// Making sure there is something in the forecast
		if (nForecast == null){
			throw new RuntimeException("Forecast did not load");
		}
		// Return the forecast
		return nForecast;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Ibrahim's Weather App");

		// Creating instance of every scene
		forecast = loadForecast(innitLat, innitLon);
		home = new HomeScene(forecast, primaryStage);
		settings = new SettingsScene(innitLat, innitLon, primaryStage);
		detailed = new DetailedScene(forecast, primaryStage);
		threeDay = new ThreeDayScene(forecast, primaryStage);

		// Linking necessary scenes together
		home.link(threeDay, detailed, settings);
		settings.link(home, detailed, threeDay);
		threeDay.link(home, detailed);

		// Initializing said scenes
		home.innitScene();
		settings.innitScene();
		detailed.innitScene();
		threeDay.innitScene();

		// Showing
		primaryStage.setMinHeight(500);
		primaryStage.setMinWidth(800);
		primaryStage.setScene(home.home);
		primaryStage.show();
	}
}