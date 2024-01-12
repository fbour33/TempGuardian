package tempGuardian;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tempGuardian.Position;
import static org.junit.jupiter.api.Assertions.*;

public class PositionTest {

    Position position;
    final double latitude = 1;
    final double longitude = 2;

    @BeforeEach
    void setup(){
        position = new Position(latitude, longitude);
    }

    @Test
    void get_latitude_correctly(){
        assertEquals(latitude, position.getLatitude());
    }

    @Test
    void get_longitude_correctly(){
        assertEquals(longitude, position.getLongitude());
    }

    @Test
    void toString_correctly_print(){
        String positionString = position.toString();
        assertTrue(positionString.contains("lat"));
        assertTrue(positionString.contains("lon"));
        assertTrue(positionString.contains("" + latitude));
        assertTrue(positionString.contains("" + longitude));
    }
}
