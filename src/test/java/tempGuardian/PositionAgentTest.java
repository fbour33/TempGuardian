package tempGuardian;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class PositionAgentTest {

    IAddress address;

    final String addressLocation = "40 rue Pierre De Coubertin 31400 Toulouse";
    final String fakeLocation = "61 Chemin Challet 59800 Lille";

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

    @Test
    void get_default_position_on_fake_address() throws ApiCommunicationError, InterruptedException {
        IPositionAgent positionAgent = new PositionAgent();
        when(address.getLocation()).thenReturn(fakeLocation);
        Position position = positionAgent.getPositionFromAddress(address);
        assertNotNull(position);
    }

}
