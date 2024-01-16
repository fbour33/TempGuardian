package tempGuardian;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class TempGuardianTestIT {
    IWeatherAgent weatherAgent;
    IPositionAgent positionAgent;

    @BeforeEach
    void setup() throws ApiCommunicationError {
        weatherAgent = new WeatherAgent();
        positionAgent = new PositionAgent();
    }

    private ArrayList<String> configTest(String inputType) throws IOException, ApiCommunicationError, InterruptedException {
        String outputFilePath = "data/testIT/" + inputType + "/output.csv";
        IConfigurationSystem configurationSystem = new ConfigurationSystem("data/testIT/" + inputType + "/input.csv");
        INotificationSystem notificationSystem = new NotificationSystem(outputFilePath);
        new TempGuardian(configurationSystem, weatherAgent, positionAgent, notificationSystem).executeSystem();
        return getAlertsInFile(outputFilePath);
    }

    private ArrayList<String> getAlertsInFile(String filePath) throws IOException {
        ArrayList<String> stringAlert = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            String currentLine;
            while((currentLine = reader.readLine()) != null){
                stringAlert.add(currentLine);
            }
            return stringAlert;
        }
    }
    private boolean isAlertSent(ArrayList<String> alerts, String username, String address, String WeatherDataType, boolean isMaxExceeded){
        String exceedMessage = ((isMaxExceeded) ? "Maximum" : "Minimum") + " threshold exceeded";
        for (String alert: alerts){
            if (alert.contains(username) && alert.contains(address) && alert.contains(WeatherDataType) && alert.contains(exceedMessage)) return true;
        }
        return false;
    }

    @Test
    void send_alert_on_temperature_minimum_exceeded() throws IOException, ApiCommunicationError, InterruptedException {
        ArrayList<String> alerts = configTest("temp/min_alert");
        assertEquals(1, alerts.size());
        assertTrue(isAlertSent(alerts, "VelocityDreamer", "40 rue Pierre De Coubertin 31400 Toulouse", "Temperature", false));
    }

    @Test
    void dont_send_alert_on_threshold_not_exceeded() throws ApiCommunicationError, InterruptedException, IOException {
        ArrayList<String> alerts = configTest("no_alert");

//        verify(notificationSystem, times(0)).sendAlert(anyString(), anyString(), anyString(), anyString());
    }

    @Test
    void dont_send_alert_when_all_notifications_disabled() throws ApiCommunicationError, InterruptedException, IOException {
        ArrayList<String> alerts = configTest("all_alert");
//        user1.disableNotifications();
//        verify(notificationSystem, times(0)).sendAlert(anyString(), anyString(), anyString(), anyString());
    }

    @Test
    void dont_send_alert_when_specific_notifications_disabled() throws ApiCommunicationError, InterruptedException, IOException {
        ArrayList<String> alerts = configTest("all_alert");
//        user1.disableNotifications(address1);
//        verify(notificationSystem, times(0)).sendAlert(anyString(), anyString(), anyString(), anyString());
    }

    @Test
    void send_alert_on_every_user_threshold() throws ApiCommunicationError, InterruptedException, IOException {
        ArrayList<String> alerts = configTest("2_user_temp_max_alert");
//        verify(notificationSystem, times(2)).sendAlert(anyString(), anyString(), anyString(), anyString());
    }

    @Test
    void send_alert_on_every_user_address() throws ApiCommunicationError, InterruptedException, IOException {
        ArrayList<String> alerts = configTest("2_address_temp_max_alert");
//        verify(notificationSystem, times(2)).sendAlert(anyString(), anyString(), anyString(), anyString());
    }
}
