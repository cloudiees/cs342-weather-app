package weather2;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PointsProperties {
    public String gridId;
    public int gridX;
    public int gridY;
}
