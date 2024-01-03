package tempGuardian;

/**
 * Données météorologiques réccupérées de l'API
 **/

public interface IWeatherData {

    public double getTemperature();
    public double getRain();
    public double getWind();
}
