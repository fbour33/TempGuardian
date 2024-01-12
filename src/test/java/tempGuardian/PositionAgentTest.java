package tempGuardian;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tempGuardian.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class PositionAgentTest {

    IAddress address;

    final String addressLocation = "40 rue Pierre De Coubertin 31400 Toulouse";
    final double latitude = 43.6584857;
    final double longitude = 1.4929625;

    final double default_latitude = 44.806433;
    final double default_longitude = -0.605182;
    @BeforeEach
    void setup(){
        address = mock(IAddress.class);
    }

    @Test
    void get_correct_position_when_found() throws InterruptedException, ApiCommunicationError {
        when(address.getLocation()).thenReturn(addressLocation);
        IPositionAgent positionAgent = new PositionAgent();
        Position position = positionAgent.getPositionFromAddress(address);
        assertEquals(latitude, position.getLatitude());
        assertEquals(longitude, position.getLongitude());
        Thread.sleep(1000);
    }

    @Test
    void get_default_position_when_not_found() throws InterruptedException, ApiCommunicationError {
        when(address.getLocation()).thenReturn("");
        IPositionAgent positionAgent = new PositionAgent();
        Position position = positionAgent.getPositionFromAddress(address);
        assertEquals(default_latitude, position.getLatitude());
        assertEquals(default_longitude, position.getLongitude());
        Thread.sleep(1000);
    }

    @Test
    void too_fast_calls_throw_error() {
        when(address.getLocation()).thenReturn(addressLocation);
        IPositionAgent positionAgent = new PositionAgent();
        assertThrows(ApiCommunicationError.class, () -> {
            positionAgent.getPositionFromAddress(address);
            positionAgent.getPositionFromAddress(address);
        });
    }

}
