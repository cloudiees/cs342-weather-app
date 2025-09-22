package weather2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import weather.Geometry;
import weather.Period;
import weather.Root;
import weather.WeatherAPI;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class MyWeatherAPI extends WeatherAPI {
    public static ArrayList<Period> getForecastPoints(Double lat, Double lon) {
        // Building the API request URL
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.weather.gov/points/"+String.valueOf(lat)+","+String.valueOf(lon)))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;
        // Getting the API response
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Getting the root of the JSON
        PointsRoot r = getObjPoints(response.body());
        if(r == null){
            System.err.println("Failed to parse JSon");
            return null;
        }
        // Getting the forecast from the WeatherAPI by getting the arguments from the root object
        return getForecast(r.properties.gridId, r.properties.gridX, r.properties.gridY);
    }

    public static PointsRoot getObjPoints(String json){
        ObjectMapper om = new ObjectMapper();
        PointsRoot toRet = null;
        // Making sure the JSON is read properly and the root of the JSON is stored in toRet
        try {
            toRet = om.readValue(json, PointsRoot.class);
            PointsProperties g = toRet.properties;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return toRet;

    }
}
