package tempGuardian;

import java.util.ArrayList;
import java.util.Arrays;

public class Address implements IAddress {

    private String location;
    private final ArrayList<IWeatherThreshold> thresholds = new ArrayList<>();

    public Address(String location, IWeatherThreshold... weatherThresholds){
        setLocation(location);
        if(weatherThresholds != null){
            thresholds.addAll(Arrays.asList(weatherThresholds));
        }
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Address address = (Address) obj;
        return this.location.equals(address.location);
    }

    @Override
    public String toString() {
        return "Address{" +
                "location='" + location + '\'' +
                ", thresholds=" + thresholds +
                '}';
    }
}
