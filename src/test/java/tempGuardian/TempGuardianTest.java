package tempGuardian;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;
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
    void setup() throws ApiCommunicationError, InterruptedException {
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
        when(thresholdExceeded.generateThresholdMessage(any(IWeatherData.class))).thenReturn("");
        when(thresholdExceeded.generateThresholdDataMessage(any(IWeatherData.class))).thenReturn("");

        thresholdNotExceeded = mock(IWeatherThreshold.class);
        when(thresholdNotExceeded.isThresholdExceeded(any(IWeatherData.class))).thenReturn(false);
        when(thresholdNotExceeded.generateThresholdMessage(any(IWeatherData.class))).thenReturn("");
        when(thresholdNotExceeded.generateThresholdDataMessage(any(IWeatherData.class))).thenReturn("");

        tempGuardian = new TempGuardian(configurationSystem, weatherAgent, positionAgent, notificationSystem);
    }

    @Test
    void send_alert_on_threshold_exceeded() throws ApiCommunicationError, InterruptedException {
        userList.add(user1);
        addressesList.add(address1);
        address1Thresholds.add(thresholdExceeded);
        tempGuardian.executeSystem();

        verify(notificationSystem, times(1)).sendAlert(anyString(), anyString(), anyString(), anyString());
        verify(notificationSystem, times(1)).sendAlert(eq(username), anyString(), eq(location), anyString());
    }

    @Test
    void dont_send_alert_on_threshold_not_exceeded() throws ApiCommunicationError, InterruptedException {
        userList.add(user1);
        addressesList.add(address1);
        address1Thresholds.add(thresholdNotExceeded);
        tempGuardian.executeSystem();

        verify(notificationSystem, times(0)).sendAlert(anyString(), anyString(), anyString(), anyString());
    }

    @Test
    void dont_send_alert_when_all_notifications_disabled() throws ApiCommunicationError, InterruptedException {
        userList.add(user1);
        addressesList.add(address1);
        address1Thresholds.add(thresholdNotExceeded);
        tempGuardian.executeSystem();
        user1.disableNotifications();
        verify(notificationSystem, times(0)).sendAlert(anyString(), anyString(), anyString(), anyString());
    }

    @Test
    void dont_send_alert_when_specific_notifications_disabled() throws ApiCommunicationError, InterruptedException {
        userList.add(user1);
        addressesList.add(address1);
        address1Thresholds.add(thresholdNotExceeded);
        tempGuardian.executeSystem();
        user1.disableNotifications(address1);
        verify(notificationSystem, times(0)).sendAlert(anyString(), anyString(), anyString(), anyString());
    }

    @Test
    void send_alert_on_every_user_threshold() throws ApiCommunicationError, InterruptedException {
        userList.add(user1);
        addressesList.add(address1);
        address1Thresholds.add(thresholdExceeded);
        address1Thresholds.add(thresholdExceeded);
        tempGuardian.executeSystem();
        verify(notificationSystem, times(2)).sendAlert(anyString(), anyString(), anyString(), anyString());
    }

    @Test
    void send_alert_on_every_user_address() throws ApiCommunicationError, InterruptedException {
        userList.add(user1);
        addressesList.add(address1);
        addressesList.add(address1);
        address1Thresholds.add(thresholdExceeded);
        tempGuardian.executeSystem();
        verify(notificationSystem, times(2)).sendAlert(anyString(), anyString(), anyString(), anyString());
    }

    @Test
    void throw_ApiNotificationSystem_on_position_agent_error() throws ApiCommunicationError, InterruptedException {
        when(positionAgent.getPositionFromAddress(any(IAddress.class))).thenThrow(ApiCommunicationError.class);
        userList.add(user1);
        addressesList.add(address1);
        address1Thresholds.add(thresholdExceeded);
        assertThrows(ApiCommunicationError.class, () -> tempGuardian.executeSystem());
    }

    @Test
    void throw_ApiNotificationSystem_on_weather_agent_error() throws ApiCommunicationError {
        when(weatherAgent.getWeatherData(any(Position.class))).thenThrow(ApiCommunicationError.class);
        userList.add(user1);
        addressesList.add(address1);
        address1Thresholds.add(thresholdExceeded);
        assertThrows(ApiCommunicationError.class, () -> tempGuardian.executeSystem());
    }
}
