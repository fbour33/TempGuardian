import org.junit.jupiter.api.Test;
import tempGuardian.IWeatherData;
import tempGuardian.WeatherData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WeatherDataTest {

    double temp = 0;
    double wind = 1;
    double rain = 2;
    @Test
    void get_temperature_correctly(){
        IWeatherData weatherData = new WeatherData(temp, rain, wind);
        assertEquals(temp, weatherData.getTemperature());
    }

    @Test
    void get_wind_correctly(){
        IWeatherData weatherData = new WeatherData(temp, rain, wind);
        assertEquals(wind, weatherData.getWind());
    }

    @Test
    void get_rain_correctly(){
        IWeatherData weatherData = new WeatherData(temp, rain, wind);
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
