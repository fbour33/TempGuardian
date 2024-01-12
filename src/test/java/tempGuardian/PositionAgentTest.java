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
    void too_fast_calls_throw_error() {
        when(address.getLocation()).thenReturn(addressLocation);
        IPositionAgent positionAgent = new PositionAgent();
        assertThrows(ApiCommunicationError.class, () -> {
            positionAgent.getPositionFromAddress(address);
            positionAgent.getPositionFromAddress(address);
        });
    }

}
