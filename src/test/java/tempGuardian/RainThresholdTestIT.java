package tempGuardian;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RainThresholdTestIT {
    IWeatherThreshold weatherThreshold;

    @BeforeEach
    void setup(){
        weatherThreshold = new RainThreshold(0, 10);
    }

    @Test
    void isThreshold_correctly_min_exceeded(){
        IWeatherData weatherData = new WeatherData(0, -1, 0);
        assertTrue(weatherThreshold.isThresholdExceeded(weatherData));
    }

    @Test
    void isThreshold_correctly_max_exceeded(){
        IWeatherData weatherData = new WeatherData(0, 11, 0);
        assertTrue(weatherThreshold.isThresholdExceeded(weatherData));
    }

    @Test
    void isThreshold_correctly_not_exceeded(){
        IWeatherData weatherData = new WeatherData(0, 5, 0);
        assertFalse(weatherThreshold.isThresholdExceeded(weatherData));
    }


    @Test
    void isThreshold_correctly_min_not_exceeded_at_bounds(){
        IWeatherData weatherData = new WeatherData(0, 0, 0);
        assertFalse(weatherThreshold.isThresholdExceeded(weatherData));
    }

    @Test
    void isThreshold_correctly_max_not_exceeded_at_bounds(){
        IWeatherData weatherData = new WeatherData(0, 10, 0);
        assertFalse(weatherThreshold.isThresholdExceeded(weatherData));
    }

    @Test
    void message_exceeded_min_correctly_generated(){
        IWeatherData weatherData = new WeatherData(0, 11, 0);
        String message = weatherThreshold.generateThresholdMessage(weatherData);
        assertTrue(message.contains("Rain"));
        assertTrue(message.contains("Maximum"));
        assertTrue(message.contains("threshold exceeded"));
    }

    @Test
    void message_exceeded_max_correctly_generated(){
        IWeatherData weatherData = new WeatherData(0, -1, 0);
        String message = weatherThreshold.generateThresholdMessage(weatherData);
        assertTrue(message.contains("Rain"));
        assertTrue(message.contains("Minimum"));
        assertTrue(message.contains("threshold exceeded"));
    }

    @Test
    void message_no_exceeded_correctly_generated(){
        IWeatherData weatherData = new WeatherData(0, 5, 0);
        String message = weatherThreshold.generateThresholdMessage(weatherData);
        assertTrue(message.contains("Rain"));
        assertTrue(message.contains("No"));
        assertTrue(message.contains("threshold exceeded"));
    }

    @Test
    void message_no_exceed_correctly_generated_for_min_bound(){
        IWeatherData weatherData = new WeatherData(0, 0, 0);
        String message = weatherThreshold.generateThresholdMessage(weatherData);
        assertTrue(message.contains("Rain"));
        assertTrue(message.contains("No"));
        assertTrue(message.contains("threshold exceeded"));
    }

    @Test
    void message_no_exceed_correctly_generated_for_max_bound(){
        IWeatherData weatherData = new WeatherData(0, 10, 0);
        String message = weatherThreshold.generateThresholdMessage(weatherData);
        assertTrue(message.contains("Rain"));
        assertTrue(message.contains("No"));
        assertTrue(message.contains("threshold exceeded"));
    }


    @Test
    void data_message_correctly_generated_when_no_exceed(){
        IWeatherData weatherData = new WeatherData(0, 5, 0);
        String message = weatherThreshold.generateThresholdDataMessage(weatherData);
        assertTrue(message.contains("ERROR"));
    }

    @Test
    void data_message_correctly_generated_when_max_exceeded(){
        IWeatherData weatherData = new WeatherData(0, 11, 0);
        String message = weatherThreshold.generateThresholdDataMessage(weatherData);
        assertTrue(message.contains("" + 10));
        assertTrue(message.contains(">"));
        assertTrue(message.contains("" + 11));
    }

    @Test
    void data_message_correctly_generated_when_min_exceeded(){
        IWeatherData weatherData = new WeatherData(0, -1, 0);
        String message = weatherThreshold.generateThresholdDataMessage(weatherData);
        System.out.println(message);
        assertTrue(message.contains("" + -1));
        assertTrue(message.contains("<"));
        assertTrue(message.contains("" + 0));
    }

    @Test
    void data_message_correctly_generated_at_max_bound(){
        IWeatherData weatherData = new WeatherData(0, 10, 0);
        String message = weatherThreshold.generateThresholdDataMessage(weatherData);
        assertTrue(message.contains("ERROR"));
    }

    @Test
    void data_message_correctly_generated_at_min_bound(){
        IWeatherData weatherData = new WeatherData(0, 0, 0);
        String message = weatherThreshold.generateThresholdDataMessage(weatherData);
        assertTrue(message.contains("ERROR"));
    }

}
