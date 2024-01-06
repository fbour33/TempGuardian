package tempGuardian;

import java.util.ArrayList;

public interface IAddress {

    String getLocation();
    void setLocation(String address);
    ArrayList<IWeatherThreshold> getThresholds();
}
