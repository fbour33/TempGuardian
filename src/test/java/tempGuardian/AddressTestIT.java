package tempGuardian;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AddressTestIT {

    @Test
    void get_thresholds_correctly(){
        IWeatherThreshold weatherThreshold = new TempThreshold(0, 10);
        Address address = new Address("location", weatherThreshold);
        ArrayList<IWeatherThreshold> thresholdList = address.getThresholds();
        assertEquals(1, thresholdList.size());
        assertTrue(thresholdList.contains(weatherThreshold));
    }
}
