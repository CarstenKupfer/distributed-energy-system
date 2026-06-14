package at.uastw.disysenergyproducer.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class WeatherService {

    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public double getCloudCover(){
        try {
            String url = "https://api.open-meteo.com/v1/forecast"
                    + "?latitude=48.239471"
                    + "&longitude=16.378000"
                    + "&current=cloud_cover";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JsonNode root = objectMapper.readTree(response.body());

            return root
                    .get("current")
                    .get("cloud_cover")
                    .asDouble();
        }catch (Exception e){
            System.out.println("Weather API unavailable, using fallback production.");
            return 50.0;
        }
    }
}
