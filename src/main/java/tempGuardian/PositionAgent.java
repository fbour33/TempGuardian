package tempGuardian;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class PositionAgent implements IPositionAgent{

    private final OkHttpClient client;

    public PositionAgent(){
        this.client = new OkHttpClient();
    }

    // /!\ Need to do at most 1 request per second /!\
    @Override
    public Position getPositionFromAddress(IAddress address) {
        Request request = new Request.Builder()
                .url("https://geocode.maps.co/search?q=" + address.getLocation() + "&api_key=65996fc55b7b2131474693rlk09fe50")
                .build();
        try{
            Response response = client.newCall(request).execute();
            if(response.body() != null) {
                String responseBody = response.body().string();
                JSONArray jsonArray = new JSONArray(responseBody);
                if (jsonArray.isEmpty()){
                    System.out.println("No Position found for " + address.getLocation() + ": use default position");
                    return new Position(44.806433, -0.605182);
                }else{
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    double lat = jsonObject.getDouble("lat");
                    double lon = jsonObject.getDouble("lon");
                    System.out.println("New Position found for " + address.getLocation() + ": lat: " + lat + ", lon: " + lon);
                    return new Position(lat, lon);
                }
            }
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
