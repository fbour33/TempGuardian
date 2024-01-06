package tempGuardian;

/**
 * Seuil d'une donnée météorologique pour une adresse
 **/

public interface IWeatherThreshold {

    WeatherDataName getWeatherDataName();
    double getMaxThreshold();
    double getMinThreshold();
    boolean isThresholdExceeded(IWeatherData weatherData);
}
