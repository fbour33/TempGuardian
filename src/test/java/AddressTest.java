import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tempGuardian.Address;
import tempGuardian.IAddress;
import tempGuardian.IWeatherThreshold;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AddressTest {

    IWeatherThreshold weatherThreshold;

    @BeforeEach
    void setUp() {
        weatherThreshold = mock(IWeatherThreshold.class);
    }

    @Test
    void get_location_correctly() {
        Address address = new Address("location");

        assertEquals("location", address.getLocation());
    }

    @Test
    void set_location_correctly(){
        Address address = new Address("location");

        address.setLocation("new_location");

        assertEquals("new_location", address.getLocation());
    }

    @Test
    void get_thresholds_correctly(){
        Address address = new Address("location", weatherThreshold);

        ArrayList<IWeatherThreshold> thresholdList = address.getThresholds();
        assertEquals(1, thresholdList.size());
        assertTrue(thresholdList.contains(weatherThreshold));
    }

    @Test
    void same_addresses_are_equals(){
        Address address1 = new Address("location");
        assertEquals(address1, address1);

        Address address2 = new Address("location");
        assertEquals(address1, address2);

        Address address3 = new Address("location", weatherThreshold);
        assertEquals(address1, address3);
    }

    @Test
    void different_addresses_are_different(){
        Address address1 = new Address("location1");
        Address address2 = new Address("location2");

        assertNotEquals(address1, address2);

        Address address3 = new Address("location3", weatherThreshold);
        assertNotEquals(address1, address3);

        IAddress fakeAddress = mock(IAddress.class);
        when(fakeAddress.getLocation()).thenReturn("location1");
        assertNotEquals(address1, fakeAddress);
    }

    @Test
    void toString_correctly_print(){
        Address address1 = new Address("newName");
        String stringAddress = address1.toString();
        assertTrue(stringAddress.contains("Address"));
        assertTrue(stringAddress.contains("location"));
        assertTrue(stringAddress.contains("thresholds"));
        assertTrue(stringAddress.contains("newName"));
    }
}
