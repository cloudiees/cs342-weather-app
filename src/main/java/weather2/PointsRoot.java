package weather2;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PointsRoot{
    public String id;
    public String type;
    public PointsGeometry geometry;
    public PointsProperties properties;
}
