package org.euro.dev;


import com.fasterxml.jackson.annotation.JsonGetter;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Created by aman on 4/10/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(value = {"_id", "name", "type", "latitude", "longitude"})

public class City {
    @JsonProperty("_id")
    private long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("type")
    private String type;

    @JsonProperty("geo_position")
    private GeoPosition location;

    @JsonGetter("_id")
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Double getLatitude() {
        return location.getLatitude();
    }

    public Double getLongitude() {
        return location.getLongitude();
    }

    /**
     * Returns comma separated values "_id,name,type,latitude,longitude".
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(id).append(',').append(name).append(',').append("type").append(',').append(getLatitude()).append(',').append(getLongitude());
        return sb.toString();
    }

    private static class GeoPosition {

        private double longitude,latitude;

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }
    }
}
