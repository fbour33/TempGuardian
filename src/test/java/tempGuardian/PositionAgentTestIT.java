package tempGuardian;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PositionAgentTestIT {

    final String addressLocation = "40 rue Pierre De Coubertin 31400 Toulouse";

    @Test
    void get_position_from_address_correctly() throws ApiCommunicationError, InterruptedException {
        IPositionAgent positionAgent = new PositionAgent();
        IAddress address = new Address(addressLocation);
        Position position = positionAgent.getPositionFromAddress(address);
        assertNotNull(position);
    }
}
