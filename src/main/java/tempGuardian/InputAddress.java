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

    public Double getMinTemp() {
        return minTemp;
    }

    public Double getMaxTemp() {
        return maxTemp;
    }

    public Double getMinWind() {
        return minWind;
    }

    public Double getMaxWind() {
        return maxWind;
    }

    public Double getMinRainfall() {
        return minRainfall;
    }

    public Double getMaxRainfall() {
        return maxRainfall;
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
