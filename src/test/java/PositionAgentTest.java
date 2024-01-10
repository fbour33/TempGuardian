import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tempGuardian.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class PositionAgentTest {

    IAddress address;

    String addressLocation = "40 rue Pierre De Coubertin 31400 Toulouse";
    double latitude = 43.6584857;
    double longitude = 1.4929625;

    double default_latitude = 44.806433;
    double default_longitude = -0.605182;
    @BeforeEach
    void setup(){
        address = mock(IAddress.class);
    }

    @Test
    void get_correct_position_when_found() throws InterruptedException {
        when(address.getLocation()).thenReturn(addressLocation);
        IPositionAgent positionAgent = new PositionAgent();
        Position position = positionAgent.getPositionFromAddress(address);
        assertEquals(latitude, position.getLatitude());
        assertEquals(longitude, position.getLongitude());
        Thread.sleep(1000);
    }

    @Test
    void get_default_position_when_not_found() throws InterruptedException {
        when(address.getLocation()).thenReturn("");
        IPositionAgent positionAgent = new PositionAgent();
        Position position = positionAgent.getPositionFromAddress(address);
        assertEquals(default_latitude, position.getLatitude());
        assertEquals(default_longitude, position.getLongitude());
        Thread.sleep(1000);
    }
}
