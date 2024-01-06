package tempGuardian;

import com.opencsv.bean.CsvBindByName;

public class InputAddress {
    @CsvBindByName(column = "username")
    private String username;

    @CsvBindByName(column = "address")
    private String address;

    @CsvBindByName(column = "minTemp(°C)")
    private Double minTemp;
    @CsvBindByName(column = "maxTemp(°C)")
    private Double maxTemp;

    @CsvBindByName(column = "minWind(km/h)")
    private Double minWind;
    @CsvBindByName(column = "maxWind(km/h)")
    private Double maxWind;

    @CsvBindByName(column = "minRainfall(mm/h)")
    private Double minRainfall;
    @CsvBindByName(column = "maxRainfall(mm/h)")
    private Double maxRainfall;

    public String getUsername() {
        return username;
    }

    public String getAddress() {
        return address;
    }

    // this method cant return a null value
    public double getValue(WeatherDataName dataName, boolean isMin) {
        Double value = null;
        double defaultValue = (isMin) ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
        switch (dataName){
            case TEMP:
                value = (isMin) ? minTemp : maxTemp;
                break;
            case WIND:
                value = (isMin) ? minWind : maxWind;
                break;
            case RAIN:
                value = (isMin) ? minRainfall : maxRainfall;
                break;
        }
        return (value != null) ? value : defaultValue;
    }

    @Override
    public String toString() {
        return "InputAddress{" +
                "username='" + username + '\'' +
                ", address='" + address + '\'' +
                ", minTemp=" + minTemp +
                ", maxTemp=" + maxTemp +
                ", minWind=" + minWind +
                ", maxWind=" + maxWind +
                ", minRainfall=" + minRainfall +
                ", maxRainfall=" + maxRainfall +
                '}';
    }
}
