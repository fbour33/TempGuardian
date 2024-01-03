package tempGuardian;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class PositionAgent implements IPositionAgent{

    private OkHttpClient client;
    private final String POSITION_URL = "https://geocode.maps.co/search?q=";

    public PositionAgent(){
        this.client = new OkHttpClient();
    }

    // One possible issue : An API key is needed for some address,
    // method might return null because of that
    @Override
    public Position getPositionFromAddress(IAddress address) {
        Request request = new Request.Builder()
                .url(POSITION_URL + address.getLocation())
                .build();
        try{
            Response response = client.newCall(request).execute();
            if(response.body() != null) {
                JSONArray jsonArray = new JSONArray(response.body().string());
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                return new Position(jsonObject.getDouble("lat"), jsonObject.getDouble("lon"));
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
//Used for testing class
//    public static void main(String[] args) {
//        IAddress address = new Address("17 rue Saint Martin, 28100, Dreux");
//        IPositionAgent positionAgent = new PositionAgent();
//        System.out.println(positionAgent.getPositionFromAddress(address));
//    }
}
