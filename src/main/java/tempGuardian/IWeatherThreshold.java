package tempGuardian;

/**
 * Seuil d'une donnée météorologique pour une adresse
 **/

public interface IWeatherThreshold {

    double getMaxThreshold();
    double getMinThreshold();
    boolean isThresholdExceeded(IWeatherData weatherData);
    String generateThresholdMessage(IWeatherData weatherData);
    String generateThresholdDataMessage(IWeatherData weatherData);
}
