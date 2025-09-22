import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import weather.Period;

import java.text.DateFormatSymbols;
import java.util.ArrayList;

public abstract class SceneAbstract {
    Stage primaryStage;
    BorderPane root;
    int mode;

    // Ensures that every scene has an initialization function
    public abstract void innitScene();

    // Generates a spacer
    public static Region getSpacer(){
        Region toRet = new Region();
        HBox.setHgrow(toRet, Priority.ALWAYS);
        return toRet;
    }

    // Initializes arrays to grab strings for month and day names
    public static String[][] innitStringForTimes(){
        String[][] toRet = new String[2][];
        DateFormatSymbols dfs = new DateFormatSymbols();
        toRet[0] = dfs.getMonths();
        toRet[1] = dfs.getWeekdays();
        return toRet;
    }

    // With a forecast and period as input, this function returns a string to an image relation to the forecast icon of the given period
    // If no image is set for the specific relation then the time of day icon is returned
    public String getIcon(int period, ArrayList<Period> forecast) {
        String APIIcon = forecast.get(period).icon;
        String toRet = "";
        if(mode == 1){
            toRet = "L";
        }
        if (APIIcon.contains("/tsra")) {
            toRet += "thunderstorm.png";
        } else if (APIIcon.contains("/rain")) {
            toRet += "rainy.png";
        } else if (APIIcon.contains("/ovc")) {
            toRet += "cloudy.png";
        } else if (APIIcon.contains("day/few") || APIIcon.contains("day/sct") || APIIcon.contains("day/bkn")) {
            toRet += "sunnyCloud.png";
        } else if (APIIcon.contains("night/few") || APIIcon.contains("night/sct") || APIIcon.contains("night/bkn")) {
            toRet += "nightCloud.png";
        } else if (APIIcon.contains("/snow")) {
            toRet += "snowy.png";
        } else {
            if (APIIcon.contains("day")) {
                toRet += "sunny.png";
            } else {
                toRet += "night.png";
            }
        }
        return toRet;
    }

    // Returns an image given the file name
    public Image getImage(String imgName){
        return new Image(getClass().getClassLoader().getResourceAsStream(imgName));
    }
}
