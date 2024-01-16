package tempGuardian;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tempGuardian.IWeatherData;
import tempGuardian.IWeatherThreshold;
import tempGuardian.WindThreshold;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WindThresholdTest {

    IWeatherData weatherData;

    @BeforeEach
    void setup(){
        weatherData = mock(IWeatherData.class);
    }

    @Test
    void get_max_correctly(){
        IWeatherThreshold weatherThreshold = new WindThreshold(0, 10);
        assertEquals(10, weatherThreshold.getMaxThreshold());
    }

    @Test
    void get_min_correctly(){
        IWeatherThreshold weatherThreshold = new WindThreshold(0, 10);
        assertEquals(0, weatherThreshold.getMinThreshold());
    }

    @Test
    void isThreshold_correctly_min_exceeded(){
        IWeatherThreshold weatherThreshold = new WindThreshold(0, 10);
        when(weatherData.getWind()).thenReturn(-1d);
        assertTrue(weatherThreshold.isThresholdExceeded(weatherData));
    }

    @Test
    void isThreshold_correctly_max_exceeded(){
        IWeatherThreshold weatherThreshold = new WindThreshold(0, 10);
        when(weatherData.getWind()).thenReturn(11d);
        assertTrue(weatherThreshold.isThresholdExceeded(weatherData));
    }

    @Test
    void isThreshold_correctly_not_exceeded(){
        IWeatherThreshold weatherThreshold = new WindThreshold(0, 10);
        when(weatherData.getWind()).thenReturn(5d);
        assertFalse(weatherThreshold.isThresholdExceeded(weatherData));
    }

    @Test
    void isThreshold_correctly_not_exceeded_at_min_bound(){
        IWeatherThreshold weatherThreshold = new WindThreshold(0, 10);
        when(weatherData.getWind()).thenReturn(0d);
        assertFalse(weatherThreshold.isThresholdExceeded(weatherData));
    }

    @Test
    void isThreshold_correctly_not_exceeded_at_max_bound(){
        IWeatherThreshold weatherThreshold = new WindThreshold(0, 10);
        when(weatherData.getWind()).thenReturn(10d);
        assertFalse(weatherThreshold.isThresholdExceeded(weatherData));
    }

    @Test
    void message_correctly_generated_max_exceeded(){
        IWeatherThreshold weatherThreshold = new WindThreshold(0, 10);
        when(weatherData.getWind()).thenReturn(11d);
        String message = weatherThreshold.generateThresholdMessage(weatherData);
        assertTrue(message.contains("Wind"));
        assertTrue(message.contains("Maximum"));
        assertTrue(message.contains("threshold exceeded"));
    }

    @Test
    void message_correctly_generated_min_exceeded(){
        IWeatherThreshold weatherThreshold = new WindThreshold(0, 10);
        when(weatherData.getWind()).thenReturn(-1d);
        String message = weatherThreshold.generateThresholdMessage(weatherData);
        assertTrue(message.contains("Wind"));
        assertTrue(message.contains("Minimum"));
        assertTrue(message.contains("threshold exceeded"));
    }


    @Test
    void message_correctly_generated_no_exceeded(){
        IWeatherThreshold weatherThreshold = new WindThreshold(0, 10);
        when(weatherData.getWind()).thenReturn(5d);
        String message = weatherThreshold.generateThresholdMessage(weatherData);
        assertTrue(message.contains("Wind"));
        assertTrue(message.contains("No"));
        assertTrue(message.contains("threshold exceeded"));
    }

    @Test
    void message_correctly_generated_no_exceed_for_min_bound(){
        IWeatherThreshold weatherThreshold = new WindThreshold(0, 10);
        when(weatherData.getWind()).thenReturn(0d);
        String message = weatherThreshold.generateThresholdMessage(weatherData);
        assertTrue(message.contains("Wind"));
        assertTrue(message.contains("No"));
        assertTrue(message.contains("threshold exceeded"));
    }

    @Test
    void message_correctly_generated_no_exceed_for_max_bound(){
        IWeatherThreshold weatherThreshold = new WindThreshold(0, 10);
        when(weatherData.getWind()).thenReturn(10d);
        String message = weatherThreshold.generateThresholdMessage(weatherData);
        assertTrue(message.contains("Wind"));
        assertTrue(message.contains("No"));
        assertTrue(message.contains("threshold exceeded"));
    }

    @Test
    void data_message_correctly_generated_when_no_exceed(){
        IWeatherThreshold weatherThreshold = new TempThreshold(0, 10);
        when(weatherData.getWind()).thenReturn(0d);
        String message = weatherThreshold.generateThresholdDataMessage(weatherData);
        assertTrue(message.contains("ERROR"));
    }

    @Test
    void data_message_correctly_generated_when_max_exceeded(){
        IWeatherThreshold weatherThreshold = new WindThreshold(0, 10);
        when(weatherData.getWind()).thenReturn(11d);
        String message = weatherThreshold.generateThresholdDataMessage(weatherData);
        assertTrue(message.contains("" + 10));
        assertTrue(message.contains(">"));
        assertTrue(message.contains("" + 11));
    }

    @Test
    void data_message_correctly_generated_when_min_exceeded(){
        IWeatherThreshold weatherThreshold = new WindThreshold(0, 10);
        when(weatherData.getWind()).thenReturn(-1d);
        String message = weatherThreshold.generateThresholdDataMessage(weatherData);
        assertTrue(message.contains("" + -1));
        assertTrue(message.contains("<"));
        assertTrue(message.contains("" + 0));
    }

    @Test
    void data_message_correctly_generated_at_max_bound(){
        IWeatherThreshold weatherThreshold = new WindThreshold(0, 10);
        when(weatherData.getWind()).thenReturn(10d);
        String message = weatherThreshold.generateThresholdDataMessage(weatherData);
        assertTrue(message.contains("ERROR"));
    }

    @Test
    void data_message_correctly_generated_at_min_bound(){
        IWeatherThreshold weatherThreshold = new WindThreshold(0, 10);
        when(weatherData.getWind()).thenReturn(0d);
        String message = weatherThreshold.generateThresholdDataMessage(weatherData);
        assertTrue(message.contains("ERROR"));
    }

    @Test
    void data_message_correctly_generated(){
        IWeatherThreshold weatherThreshold = new WindThreshold(0, 10);
        when(weatherData.getWind()).thenReturn(11d);
        String message = weatherThreshold.generateThresholdDataMessage(weatherData);
        assertTrue(message.contains("" + 10));
        assertTrue(message.contains(">"));
        assertTrue(message.contains("" + 11));
        when(weatherData.getWind()).thenReturn(-1d);
        message = weatherThreshold.generateThresholdDataMessage(weatherData);
        System.out.println(message);
        assertTrue(message.contains("" + -1));
        assertTrue(message.contains("<"));
        assertTrue(message.contains("" + 0));
        when(weatherData.getWind()).thenReturn(5d);
        message = weatherThreshold.generateThresholdDataMessage(weatherData);
        assertTrue(message.contains("ERROR"));
    }

    @Test
    void toString_correctly_print(){
        IWeatherThreshold weatherThreshold = new WindThreshold(0, 10);
        String weatherThresholdString = weatherThreshold.toString();
        assertTrue(weatherThresholdString.contains("WindThreshold"));
        assertTrue(weatherThresholdString.contains("minThreshold"));
        assertTrue(weatherThresholdString.contains("maxThreshold"));
        assertTrue(weatherThresholdString.contains("" + 0));
        assertTrue(weatherThresholdString.contains("" + 10));
    }
}
