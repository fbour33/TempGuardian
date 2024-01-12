import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tempGuardian.IWeatherData;
import tempGuardian.IWeatherThreshold;
import tempGuardian.RainThreshold;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RainThresholdTest {

    IWeatherData weatherData;

    @BeforeEach
    void setup(){
        weatherData = mock(IWeatherData.class);
    }

    @Test
    void get_max_correctly(){
        IWeatherThreshold weatherThreshold = new RainThreshold(0, 10);
        assertEquals(10, weatherThreshold.getMaxThreshold());
    }

    @Test
    void get_min_correctly(){
        IWeatherThreshold weatherThreshold = new RainThreshold(0, 10);
        assertEquals(0, weatherThreshold.getMinThreshold());
    }

    @Test
    void isThreshold_correctly_min_exceeded(){
        IWeatherThreshold weatherThreshold = new RainThreshold(0, 10);
        when(weatherData.getRain()).thenReturn(-1d);
        assertTrue(weatherThreshold.isThresholdExceeded(weatherData));
    }

    @Test
    void isThreshold_correctly_max_exceeded(){
        IWeatherThreshold weatherThreshold = new RainThreshold(0, 10);
        when(weatherData.getRain()).thenReturn(11d);
        assertTrue(weatherThreshold.isThresholdExceeded(weatherData));
    }

    @Test
    void isThreshold_correctly_not_exceeded(){
        IWeatherThreshold weatherThreshold = new RainThreshold(0, 10);
        when(weatherData.getRain()).thenReturn(5d);
        assertFalse(weatherThreshold.isThresholdExceeded(weatherData));
    }

    @Test
    void message_correctly_generated(){
        IWeatherThreshold weatherThreshold = new RainThreshold(0, 10);
        when(weatherData.getRain()).thenReturn(11d);
        String message = weatherThreshold.generateThresholdMessage(weatherData);
        assertTrue(message.contains("Rain"));
        assertTrue(message.contains("Maximum"));
        assertTrue(message.contains("threshold exceeded"));
        when(weatherData.getRain()).thenReturn(-1d);
        message = weatherThreshold.generateThresholdMessage(weatherData);
        assertTrue(message.contains("Rain"));
        assertTrue(message.contains("Minimum"));
        assertTrue(message.contains("threshold exceeded"));
        when(weatherData.getRain()).thenReturn(5d);
        message = weatherThreshold.generateThresholdMessage(weatherData);
        assertTrue(message.contains("Rain"));
        assertTrue(message.contains("No"));
        assertTrue(message.contains("threshold exceeded"));
    }

    @Test
    void data_message_correctly_generated(){
        IWeatherThreshold weatherThreshold = new RainThreshold(0, 10);
        when(weatherData.getRain()).thenReturn(11d);
        String message = weatherThreshold.generateThresholdDataMessage(weatherData);
        assertTrue(message.contains("" + 10));
        assertTrue(message.contains(">"));
        assertTrue(message.contains("" + 11));
        when(weatherData.getRain()).thenReturn(-1d);
        message = weatherThreshold.generateThresholdDataMessage(weatherData);
        System.out.println(message);
        assertTrue(message.contains("" + -1));
        assertTrue(message.contains("<"));
        assertTrue(message.contains("" + 0));
        when(weatherData.getRain()).thenReturn(5d);
        message = weatherThreshold.generateThresholdDataMessage(weatherData);
        assertTrue(message.contains("ERROR"));
    }

    @Test
    void toString_correctly_print(){
        IWeatherThreshold weatherThreshold = new RainThreshold(0, 10);
        String weatherThresholdString = weatherThreshold.toString();
        assertTrue(weatherThresholdString.contains("RainThreshold"));
        assertTrue(weatherThresholdString.contains("minThreshold"));
        assertTrue(weatherThresholdString.contains("maxThreshold"));
        assertTrue(weatherThresholdString.contains("" + 0));
        assertTrue(weatherThresholdString.contains("" + 10));
    }
}
