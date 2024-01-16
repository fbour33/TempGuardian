package tempGuardian;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tempGuardian.IWeatherData;
import tempGuardian.IWeatherThreshold;
import tempGuardian.TempThreshold;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TempThresholdTest {

    IWeatherData weatherData;

    @BeforeEach
    void setup(){
        weatherData = mock(IWeatherData.class);
    }

    @Test
    void get_max_correctly(){
        IWeatherThreshold weatherThreshold = new TempThreshold(0, 10);
        assertEquals(10, weatherThreshold.getMaxThreshold());
    }

    @Test
    void get_min_correctly(){
        IWeatherThreshold weatherThreshold = new TempThreshold(0, 10);
        assertEquals(0, weatherThreshold.getMinThreshold());
    }

    @Test
    void isThreshold_correctly_min_exceeded(){
        IWeatherThreshold weatherThreshold = new TempThreshold(0, 10);
        when(weatherData.getTemperature()).thenReturn(-1d);
        assertTrue(weatherThreshold.isThresholdExceeded(weatherData));
    }

    @Test
    void isThreshold_correctly_max_exceeded(){
        IWeatherThreshold weatherThreshold = new TempThreshold(0, 10);
        when(weatherData.getTemperature()).thenReturn(11d);
        assertTrue(weatherThreshold.isThresholdExceeded(weatherData));
    }

    @Test
    void isThreshold_correctly_not_exceeded(){
        IWeatherThreshold weatherThreshold = new TempThreshold(0, 10);
        when(weatherData.getTemperature()).thenReturn(5d);
        assertFalse(weatherThreshold.isThresholdExceeded(weatherData));
    }

    @Test
    void isThreshold_correctly_min_not_exceeded_at_bounds(){
        IWeatherThreshold weatherThreshold = new TempThreshold(0, 10);
        when(weatherData.getTemperature()).thenReturn(0d);
        assertFalse(weatherThreshold.isThresholdExceeded(weatherData));
    }

    @Test
    void isThreshold_correctly_max_not_exceeded_at_bounds(){
        IWeatherThreshold weatherThreshold = new TempThreshold(0, 10);
        when(weatherData.getTemperature()).thenReturn(10d);
        assertFalse(weatherThreshold.isThresholdExceeded(weatherData));
    }

    @Test
    void message_exceeded_min_correctly_generated(){
        IWeatherThreshold weatherThreshold = new TempThreshold(0, 10);
        when(weatherData.getTemperature()).thenReturn(11d);
        String message = weatherThreshold.generateThresholdMessage(weatherData);
        assertTrue(message.contains("Temperature"));
        assertTrue(message.contains("Maximum"));
        assertTrue(message.contains("threshold exceeded"));
    }

    @Test
    void message_exceeded_max_correctly_generated(){
        IWeatherThreshold weatherThreshold = new TempThreshold(0, 10);
        when(weatherData.getTemperature()).thenReturn(-1d);
        String message = weatherThreshold.generateThresholdMessage(weatherData);
        assertTrue(message.contains("Temperature"));
        assertTrue(message.contains("Minimum"));
        assertTrue(message.contains("threshold exceeded"));
    }

    @Test
    void message_no_exceeded_correctly_generated(){
        IWeatherThreshold weatherThreshold = new TempThreshold(0, 10);
        when(weatherData.getTemperature()).thenReturn(5d);
        String message = weatherThreshold.generateThresholdMessage(weatherData);
        assertTrue(message.contains("Temperature"));
        assertTrue(message.contains("No"));
        assertTrue(message.contains("threshold exceeded"));
    }

    @Test
    void message_no_exceed_correctly_generated_for_min_bound(){
        IWeatherThreshold weatherThreshold = new TempThreshold(0, 10);
        when(weatherData.getTemperature()).thenReturn(0d);
        String message = weatherThreshold.generateThresholdMessage(weatherData);
        assertTrue(message.contains("Temperature"));
        assertTrue(message.contains("No"));
        assertTrue(message.contains("threshold exceeded"));
    }

    @Test
    void message_no_exceed_correctly_generated_for_max_bound(){
        IWeatherThreshold weatherThreshold = new TempThreshold(0, 10);
        when(weatherData.getTemperature()).thenReturn(10d);
        String message = weatherThreshold.generateThresholdMessage(weatherData);
        assertTrue(message.contains("Temperature"));
        assertTrue(message.contains("No"));
        assertTrue(message.contains("threshold exceeded"));
    }


    @Test
    void data_message_correctly_generated_when_no_exceed(){
        IWeatherThreshold weatherThreshold = new TempThreshold(0, 10);
        when(weatherData.getTemperature()).thenReturn(5d);
        String message = weatherThreshold.generateThresholdDataMessage(weatherData);
        assertTrue(message.contains("ERROR"));
    }

    @Test
    void data_message_correctly_generated_when_max_exceeded(){
        IWeatherThreshold weatherThreshold = new TempThreshold(0, 10);
        when(weatherData.getTemperature()).thenReturn(11d);
        String message = weatherThreshold.generateThresholdDataMessage(weatherData);
        assertTrue(message.contains("" + 10));
        assertTrue(message.contains(">"));
        assertTrue(message.contains("" + 11));
    }

    @Test
    void data_message_correctly_generated_when_min_exceeded(){
        IWeatherThreshold weatherThreshold = new TempThreshold(0, 10);
        when(weatherData.getTemperature()).thenReturn(-1d);
        String message = weatherThreshold.generateThresholdDataMessage(weatherData);
        System.out.println(message);
        assertTrue(message.contains("" + -1));
        assertTrue(message.contains("<"));
        assertTrue(message.contains("" + 0));
    }

    @Test
    void data_message_correctly_generated_at_max_bound(){
        IWeatherThreshold weatherThreshold = new TempThreshold(0, 10);
        when(weatherData.getTemperature()).thenReturn(10d);
        String message = weatherThreshold.generateThresholdDataMessage(weatherData);
        assertTrue(message.contains("ERROR"));
    }

    @Test
    void data_message_correctly_generated_at_min_bound(){
        IWeatherThreshold weatherThreshold = new TempThreshold(0, 10);
        when(weatherData.getTemperature()).thenReturn(0d);
        String message = weatherThreshold.generateThresholdDataMessage(weatherData);
        assertTrue(message.contains("ERROR"));
    }

    @Test
    void toString_correctly_print(){
        IWeatherThreshold weatherThreshold = new TempThreshold(0, 10);
        String weatherThresholdString = weatherThreshold.toString();
        assertTrue(weatherThresholdString.contains("TempThreshold"));
        assertTrue(weatherThresholdString.contains("minThreshold"));
        assertTrue(weatherThresholdString.contains("maxThreshold"));
        assertTrue(weatherThresholdString.contains("" + 0));
        assertTrue(weatherThresholdString.contains("" + 10));
    }
}
