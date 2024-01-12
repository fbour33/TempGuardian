package tempGuardian;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tempGuardian.*;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

public class TempGuardianTest {

    IConfigurationSystem configurationSystem;
    INotificationSystem notificationSystem;
    IWeatherAgent weatherAgent;
    IPositionAgent positionAgent;

    IAddress address1;
    String location = "location test";
    IUser user1;
    String username = "username_test";
    IWeatherThreshold thresholdExceeded;
    IWeatherThreshold thresholdNotExceeded;
    Position position;
    IWeatherData weatherData;

    TempGuardian tempGuardian;
    ArrayList<IUser> userList = new ArrayList<>();
    ArrayList<IAddress> addressesList = new ArrayList<>();
    ArrayList<IWeatherThreshold> address1Thresholds = new ArrayList<>();

    @BeforeEach
    void setup() throws ApiCommunicationError {
        configurationSystem = mock(IConfigurationSystem.class);
        when(configurationSystem.getAllUsers()).thenReturn(userList);
        when(configurationSystem.getUserAddresses(any(IUser.class))).thenReturn(addressesList);

        notificationSystem = mock(INotificationSystem.class);

        weatherAgent = mock(IWeatherAgent.class);
        weatherData = new WeatherData(0, 0, 0);
        when(weatherAgent.getWeatherData(any(Position.class))).thenReturn(weatherData);

        positionAgent = mock(IPositionAgent.class);
        position = new Position(1, 1);
        when(positionAgent.getPositionFromAddress(any(IAddress.class))).thenReturn(position);


        address1 = mock(IAddress.class);
        when(address1.getThresholds()).thenReturn(address1Thresholds);
        when(address1.getLocation()).thenReturn(location);

        user1 = mock(IUser.class);
        when(user1.getName()).thenReturn(username);

        thresholdExceeded = mock(IWeatherThreshold.class);
        when(thresholdExceeded.isThresholdExceeded(any(IWeatherData.class))).thenReturn(true);

        thresholdNotExceeded = mock(IWeatherThreshold.class);
        when(thresholdExceeded.isThresholdExceeded(any(IWeatherData.class))).thenReturn(false);


        tempGuardian = new TempGuardian(configurationSystem, weatherAgent, positionAgent, notificationSystem);
    }

    @Test
    void send_alert_on_threshold_exceeded() throws ApiCommunicationError, InterruptedException {
        userList.add(user1);
        addressesList.add(address1);
        address1Thresholds.add(thresholdExceeded);
        //tempGuardian.executeSystem();

        //verify(notificationSystem, times(1)).sendAlert(anyString(), anyString(), anyString(), anyString());
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
