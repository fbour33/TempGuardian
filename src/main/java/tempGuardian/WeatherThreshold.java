package tempGuardian;

public class WeatherThreshold implements IWeatherThreshold{

    private final WeatherDataName weatherDataName;
    private final double minThreshold;
    private final double maxThreshold;

    public WeatherThreshold(WeatherDataName weatherDataName, double minThreshold, double maxThreshold){
        this.weatherDataName = weatherDataName;
        this.minThreshold = minThreshold;
        this.maxThreshold = maxThreshold;
    }

    @Override
    public WeatherDataName getWeatherDataName() {
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
        switch (weatherDataName){

            case TEMP:
                return weatherData.getTemperature() > maxThreshold || weatherData.getTemperature() < minThreshold;
            case WIND:
                return weatherData.getRain() > maxThreshold || weatherData.getRain() < minThreshold;
            case RAIN:
                return weatherData.getWind() > maxThreshold || weatherData.getWind() < minThreshold;
        }
        return false;
    }

    @Override
    public String toString() {
        return "WeatherThreshold{" +
                "weatherDataName=" + weatherDataName +
                ", minThreshold=" + minThreshold +
                ", maxThreshold=" + maxThreshold +
                '}';
    }
}
