package tempGuardian;

import java.util.ArrayList;

public interface IAddress {

    public String getLocation();
    public void setLocation(String address);
    public ArrayList<IWeatherThreshold> getThresholds();

}
