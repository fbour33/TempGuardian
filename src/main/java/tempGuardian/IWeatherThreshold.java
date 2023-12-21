package tempGuardian;

/**
 * Seuil d'une donnée météorologique pour une adresse
 **/

public interface IWeatherThreshold {

    public String getWeatherDataName();
    public double getMaxThreshold();
    public double getMinThreshold();
    public boolean isThresholdExceeded(IWeatherData weatherData);
}
