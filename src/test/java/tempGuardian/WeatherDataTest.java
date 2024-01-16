package tempGuardian;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tempGuardian.IWeatherData;
import tempGuardian.WeatherData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WeatherDataTest {

    final double temp = 1;
    final double wind = 2;
    final double rain = 3;

    IWeatherData weatherData;

    @BeforeEach
    public void setup(){
        weatherData = new WeatherData(temp, rain, wind);
    }
    @Test
    void get_temperature_correctly(){
        assertEquals(temp, weatherData.getTemperature());
    }

    @Test
    void get_wind_correctly(){
        assertEquals(wind, weatherData.getWind());
    }

    @Test
    void get_rain_correctly(){
        assertEquals(rain, weatherData.getRain());
    }

    @Test
    void toString_correctly_print(){
        IWeatherData weatherData = new WeatherData(temp, rain, wind);
        String weatherDataString = weatherData.toString();
        assertTrue(weatherDataString.contains("temperature_2m"));
        assertTrue(weatherDataString.contains("rain"));
        assertTrue(weatherDataString.contains("wind_speed_10m"));
        assertTrue(weatherDataString.contains("" + temp));
        assertTrue(weatherDataString.contains("" + wind));
        assertTrue(weatherDataString.contains("" + rain));
    }
}
