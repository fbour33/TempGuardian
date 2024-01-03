package tempGuardian;

public class WeatherData implements IWeatherData {

    private final double temperature;
    private final double rain;
    private final double wind;

    public WeatherData(double temperature, double rain, double wind){
        this.temperature = temperature;
        this.rain = rain;
        this.wind = wind;
    }

    @Override
    public double getTemperature() {
        return temperature;
    }

    @Override
    public double getRain() {
        return rain;
    }

    @Override
    public double getWind() {
        return wind;
    }

    @Override
    public String toString() {
        return "{ " +
                "temperature_2m: " + temperature +
                ", rain: " + rain +
                ", wind_speed_10m: " + wind +
                " }";
    }
}
