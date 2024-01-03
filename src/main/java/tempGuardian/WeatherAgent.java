package tempGuardian;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class WeatherAgent implements IWeatherAgent {

    private OkHttpClient client;

    public WeatherAgent(){
        this.client = new OkHttpClient();
    }

    private String getFullUrl(double latitude, double longitude){
        return "https://api.open-meteo.com/v1/forecast?latitude=" + latitude +
                "&longitude=" + longitude + "&current=temperature_2m,rain,wind_speed_10m";
    }
    @Override
    public IWeatherData getWeatherData(Position position) {
        Request request = new Request.Builder()
                .url(getFullUrl(position.getLatitude(), position.getLongitude()))
                .build();
        try{
            Response response = client.newCall(request).execute();
            if(response.body() != null) {
                JSONObject jsonObject = new JSONObject(response.body().string());
                JSONObject current = jsonObject.getJSONObject("current");
                return new WeatherData(current.getDouble("temperature_2m"),
                        current.getDouble("rain"),
                        current.getDouble("wind_speed_10m"));
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
// Used for testing class
//    public static void main(String[] args) {
//        IAddress address = new Address("17 rue Saint Martin, 28100, Dreux", null);
//        IPositionAgent positionAgent = new PositionAgent();
//        IWeatherAgent weatherAgent = new WeatherAgent();
//        System.out.println(weatherAgent.getWeatherData(positionAgent.getPositionFromAddress(address)));
//    }
}
