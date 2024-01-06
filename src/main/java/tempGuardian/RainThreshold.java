package tempGuardian;

public class RainThreshold implements IWeatherThreshold{
    private final double minThreshold;
    private final double maxThreshold;

    public RainThreshold(double minThreshold, double maxThreshold){
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
        double rain = weatherData.getRain();
        return rain > maxThreshold || rain < minThreshold;
    }

    @Override
    public String generateThresholdMessage(IWeatherData weatherData) {
        double rain = weatherData.getRain();
        String message = (rain > maxThreshold) ? "Maximum" : ((rain < minThreshold) ? "Minimum" : "No");
        return "[Rain fall] " + message + " threshold exceeded";
    }

    @Override
    public String generateThresholdDataMessage(IWeatherData weatherData) {
        double rain = weatherData.getRain();
        if (rain > maxThreshold) return rain + " > " + maxThreshold + "mm";
        if (rain < minThreshold) return rain + " < " + minThreshold + "mm";
        return "ERROR: No data!";
    }

    @Override
    public String toString() {
        return "RainThreshold{" +
                "minThreshold=" + minThreshold +
                ", maxThreshold=" + maxThreshold +
                '}';
    }
}
