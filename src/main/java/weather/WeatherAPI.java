package weather;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1


public class WeatherAPI {
    public static ArrayList<Period> getForecast(String region, int gridx, int gridy) {
        // Building the API request URL
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.weather.gov/gridpoints/"+region+"/"+String.valueOf(gridx)+","+String.valueOf(gridy)+"/forecast"))
                //.method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;
        // Getting the API response
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Getting the root of the JSON
        Root r = getObject(response.body());
        if(r == null){
            System.err.println("Failed to parse JSon");
            return null;
        }
        // Returning the forecast
        return r.properties.periods;
    }
    public static Root getObject(String json){
        ObjectMapper om = new ObjectMapper();
        Root toRet = null;
        // Making sure the JSON is read properly and the root of the JSON is stored in toRet
        try {
            toRet = om.readValue(json, Root.class);
            ArrayList<Period> p = toRet.properties.periods;

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return toRet;

    }
}



