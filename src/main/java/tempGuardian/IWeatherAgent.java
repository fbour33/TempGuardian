package tempGuardian;

public interface IWeatherAgent {

    IWeatherData getWeatherData(Position position) throws ApiCommunicationError;

}
