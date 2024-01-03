package tempGuardian;

public class WeatherThreshold implements IWeatherThreshold{

    private final String weatherDataName;
    private final double minThreshold;
    private final double maxThreshold;

    public WeatherThreshold(String weatherDataName, double minThreshold, double maxThreshold){
        this.weatherDataName = weatherDataName;
        this.minThreshold = minThreshold;
        this.maxThreshold = maxThreshold;
    }

    @Override
    public String getWeatherDataName() {
        return weatherDataName;
    }

    @Override
    public double getMinThreshold() {
        return minThreshold;
    }

    @Override
    public double getMaxThreshold() {
        return maxThreshold;
    }

    @Override
    public boolean isThresholdExceeded(IWeatherData weatherData) {
        if(weatherDataName.equals("temperature"))
            return weatherData.getTemperature() > maxThreshold || weatherData.getTemperature() < minThreshold;
        if(weatherDataName.equals("rain"))
            return weatherData.getRain() > maxThreshold || weatherData.getRain() < minThreshold;
        return weatherData.getWind() > maxThreshold || weatherData.getWind() < minThreshold;
    }
}
