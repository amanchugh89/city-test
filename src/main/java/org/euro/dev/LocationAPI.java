package org.euro.dev;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by aman on 4/10/15.
 */
public class LocationAPI {
    public final static Logger log = Logger.getLogger(LocationAPI.class.getName());
    public static final String URL = "http://api.goeuro.com/api/v2/position/suggest/en/";
    public static CsvMapper mapper  = new CsvMapper();
    private RestTemplate restTemplate = new RestTemplate();
    public static String fileType = ".csv";

    /*
    * get all cities for the keyword
    */
    public List<City> allCities(String city){
        if(city!=null && city.trim().length()>0 ) {
            City[] cities = restTemplate.getForObject(URL.concat(city), City[].class);
            return Arrays.asList(cities);
        }
        else
            throw new IllegalArgumentException();
    }

    public void writeToCSV(List<?> objects, CsvSchema schema, String fileName) throws  IOException {
        ObjectWriter writer = mapper.writer(schema.withLineSeparator("\n"));
        writer.writeValue(new File(fileName), objects);
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Please pass city name....");
            System.exit(0);
        }
        String city = args[0];
        LocationAPI restConsumer = new LocationAPI();
        try {
            CsvSchema schema = mapper.schemaFor(City.class).withHeader();
            List<City> cities = restConsumer.allCities(city);
            restConsumer.writeToCSV(cities, schema, city.concat(fileType));
        } catch (IOException e) {
            log.severe(String.format("Exception while writing to a file. %s", e.getMessage()));
        } catch (IllegalArgumentException e) {
            log.severe(String.format("Invalid arguments. %s", e.getMessage()));
        }
    }
}
