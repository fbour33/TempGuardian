package tempGuardian;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class PositionAgentTestIT {

    final String addressLocation = "40 rue Pierre De Coubertin 31400 Toulouse";

    @Test
    void too_fast_calls_throw_error() {
        IAddress address = new Address(addressLocation);
        IPositionAgent positionAgent = new PositionAgent();
        assertThrows(ApiCommunicationError.class, () -> {
            positionAgent.getPositionFromAddress(address);
            positionAgent.getPositionFromAddress(address);
            Thread.sleep(1000);
        });
    }

}
