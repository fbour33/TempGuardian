package tempGuardian;

public class TempThreshold implements IWeatherThreshold{
    private final double minThreshold;
    private final double maxThreshold;

    public TempThreshold(double minThreshold, double maxThreshold){
        this.minThreshold = minThreshold;
        this.maxThreshold = maxThreshold;
    }

    @Override
    public double getMaxThreshold() {
        return maxThreshold;
    }

    @Override
    public double getMinThreshold() {
        return minThreshold;
    }

    @Override
    public boolean isThresholdExceeded(IWeatherData weatherData) {
        double temp = weatherData.getTemperature();
        return temp > maxThreshold || temp < minThreshold;
    }

    @Override
    public String generateThresholdMessage(IWeatherData weatherData) {
        double temp = weatherData.getTemperature();
        String message = (temp > maxThreshold) ? "Maximum" : ((temp < minThreshold) ? "Minimum" : "No");
        return "[Temperature] " + message + " threshold exceeded";
    }

    @Override
    public String generateThresholdDataMessage(IWeatherData weatherData) {
        double temp = weatherData.getTemperature();
        if (temp > maxThreshold) return temp + " > " + maxThreshold + "°C";
        if (temp < minThreshold) return temp + " < " + minThreshold + "°C";
        return "ERROR: No data!";
    }


    @Override
    public String toString() {
        return "TempThreshold{" +
                "minThreshold=" + minThreshold +
                ", maxThreshold=" + maxThreshold +
                '}';
    }
}
