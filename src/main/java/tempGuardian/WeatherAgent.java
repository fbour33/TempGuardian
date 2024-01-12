package tempGuardian;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;

public class WeatherAgent implements IWeatherAgent {

    private final OkHttpClient client;

    public WeatherAgent(){
        this.client = new OkHttpClient();
    }

    private String getFullUrl(double latitude, double longitude){
        return "https://api.open-meteo.com/v1/forecast?latitude=" + latitude +
                "&longitude=" + longitude + "&current=temperature_2m,rain,wind_speed_10m";
    }
    @Override
    public IWeatherData getWeatherData(Position position) throws ApiCommunicationError {
        Request request = new Request.Builder()
                .url(getFullUrl(position.getLatitude(), position.getLongitude()))
                .build();
        try{
            Response response = client.newCall(request).execute();
            if(response.body() != null) {
                return deserializeData(response.body().string());
            }
        }catch (IOException e){
            throw new ApiCommunicationError("Weather API", e.getMessage());
        }
        return null;
    }

    private IWeatherData deserializeData(String body) throws ApiCommunicationError {
        try{
            JSONObject jsonObject = new JSONObject(body);
            JSONObject current = jsonObject.getJSONObject("current");
            return new WeatherData(current.getDouble("temperature_2m"),
                    current.getDouble("rain"),
                    current.getDouble("wind_speed_10m"));
        }catch (Exception e){
            throw new ApiCommunicationError("Weather API", "deserialization error on: " + body);
        }

    }
}
