import org.junit.jupiter.api.Test;
import tempGuardian.IWeatherAgent;
import tempGuardian.Position;
import tempGuardian.WeatherAgent;

public class WeatherAgentTest {

    @Test
    void is_weatherData_sent(){
        Position position = new Position(0, 0);
        IWeatherAgent weatherAgent = new WeatherAgent();
        weatherAgent.getWeatherData(position);
    }
}
