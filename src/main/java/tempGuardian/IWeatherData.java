package tempGuardian;

/**
 * Données météorologiques récupérées de l'API
 **/

public interface IWeatherData {

    double getTemperature();
    double getRain();
    double getWind();
}
