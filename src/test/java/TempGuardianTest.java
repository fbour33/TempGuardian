import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tempGuardian.*;

import static org.mockito.Mockito.mock;

public class TempGuardianTest {

    IConfigurationSystem configurationSystem;
    INotificationSystem notificationSystem;
    IWeatherAgent weatherAgent;
    IPositionAgent positionAgent;

    IAddress address1;
    IUser user1;
    IWeatherThreshold threshold1;
    IWeatherThreshold threshold2;

    @BeforeEach
    void setup(){
        configurationSystem = mock(IConfigurationSystem.class);
        notificationSystem = mock(INotificationSystem.class);
        weatherAgent = mock(IWeatherAgent.class);
        positionAgent = mock(IPositionAgent.class);
        address1 = mock(IAddress.class);
        user1 = mock(IUser.class);
        threshold1 = mock(IWeatherThreshold.class);
        threshold2 = mock(IWeatherThreshold.class);
    }

    @Test
    void send_alert_on_threshold_exceeded(){

    }

    @Test
    void dont_send_alert_on_threshold_not_exceeded(){

    }

    @Test
    void dont_send_alert_when_all_notifications_disabled(){

    }

    @Test
    void dont_send_alert_when_specific_notifications_disabled(){

    }

    @Test
    void send_alert_on_every_user_threshold(){

    }

    @Test
    void send_alert_on_every_user_address(){

    }
}
