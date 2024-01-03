package tempGuardian;

import java.util.ArrayList;
import java.util.Arrays;

public class Address implements IAddress{

    private String location;
    private ArrayList<IWeatherThreshold> thresholds;

    // Each Threshold is created in the InputAddress
    // in order to have the dataName and the right threshold's number
    // (Sometimes there is 2 thresholds ou 3 thresholds ...)
    public Address(String location, IWeatherThreshold... weatherThresholds){
        setLocation(location);
        if(weatherThresholds != null)
            thresholds.addAll(Arrays.asList(weatherThresholds));
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public ArrayList<IWeatherThreshold> getThresholds() {
        return thresholds;
    }
}
