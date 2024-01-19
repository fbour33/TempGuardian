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

    public double getMinTemp() {
        if (minTemp == null) return Double.NEGATIVE_INFINITY;
        return minTemp;
    }

    public double getMaxTemp() {
        if (maxTemp == null) return Double.POSITIVE_INFINITY;
        return maxTemp;
    }

    public boolean isTempDefined() {
        return minTemp != null || maxTemp != null;
    }

    public double getMinWind() {
        if (minWind == null) return Double.NEGATIVE_INFINITY;
        return minWind;
    }
    public double getMaxWind() {
        if (maxWind == null) return Double.POSITIVE_INFINITY;
        return maxWind;
    }
    public boolean isWindDefined() {
        return minWind != null || maxWind != null;
    }

    public double getMinRainfall() {
        if (minRainfall == null) return Double.NEGATIVE_INFINITY;
        return minRainfall;
    }

    public double getMaxRainfall() {
        if (maxRainfall == null) return Double.POSITIVE_INFINITY;
        return maxRainfall;
    }
    public boolean isRainFallDefined(){
        return minRainfall != null || maxRainfall != null;
    }

}
