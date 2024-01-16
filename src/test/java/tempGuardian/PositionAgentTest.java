package tempGuardian;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class PositionAgentTest {

    IAddress address;

    final String addressLocation = "40 rue Pierre De Coubertin 31400 Toulouse";

    @BeforeEach
    void setup(){
        address = mock(IAddress.class);
    }

    @Test
    void get_position_from_address_correctly() throws ApiCommunicationError, InterruptedException {
        IPositionAgent positionAgent = new PositionAgent();
        when(address.getLocation()).thenReturn(addressLocation);
        Position position = positionAgent.getPositionFromAddress(address);
        assertNotNull(position);
    }

}
