package tempGuardian;

public class WindThreshold implements IWeatherThreshold{
    private final double minThreshold;
    private final double maxThreshold;

    public WindThreshold(double minThreshold, double maxThreshold){
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
        double wind = weatherData.getWind();
        return wind > maxThreshold || wind < minThreshold;
    }

    @Override
    public String generateThresholdMessage(IWeatherData weatherData) {
        double wind = weatherData.getWind();
        String message = (wind > maxThreshold) ? "Maximum" : ((wind < minThreshold) ? "Minimum" : "No");
        return "[Wind] " + message + " threshold exceeded";
    }

    @Override
    public String generateThresholdDataMessage(IWeatherData weatherData) {
        double wind = weatherData.getWind();
        if (wind > maxThreshold) return wind + " > " + maxThreshold + "km/h";
        if (wind < minThreshold) return wind + " < " + minThreshold + "km/h";
        return "ERROR: No data!";
    }

    @Override
    public String toString() {
        return "WindThreshold{" +
                "minThreshold=" + minThreshold +
                ", maxThreshold=" + maxThreshold +
                '}';
    }
}
