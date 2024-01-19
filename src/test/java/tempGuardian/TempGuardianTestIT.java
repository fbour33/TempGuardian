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

    private ArrayList<String> configTest(String inputType, String notifications) throws IOException, ApiCommunicationError, InterruptedException {
        String outputFilePath = "data/testIT/" + inputType + "/output.csv";
        IConfigurationSystem configurationSystem = new ConfigurationSystem("data/testIT/" + inputType + "/input.csv");
        INotificationSystem notificationSystem = new NotificationSystem(outputFilePath);

        switch (notifications){
            case "disable_user":
                for (IUser user: configurationSystem.getAllUsers()){
                    user.disableNotifications();
                }
                break;
            case "disable_address":
                for (IUser user: configurationSystem.getAllUsers()){
                    for (IAddress address: configurationSystem.getUserAddresses(user)){
                        user.disableNotifications(address);
                    }
                }
                break;
            default:
                break;
        }

        new TempGuardian(configurationSystem, weatherAgent, positionAgent, notificationSystem).executeSystem();
        return getAlertsInFile(outputFilePath);
    }

    private ArrayList<String> getAlertsInFile(String filePath) throws IOException {
        ArrayList<String> stringAlert = new ArrayList<>();
        boolean isFirstLineProceed = false;
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            String currentLine;
            while((currentLine = reader.readLine()) != null){
                if (isFirstLineProceed){
                    stringAlert.add(currentLine);
                }else{
                    isFirstLineProceed = true;
                }
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
        ArrayList<String> alerts = configTest("temp/min_alert", "enable_all");
        assertEquals(1, alerts.size());
        assertTrue(isAlertSent(alerts, "VelocityDreamer", "40 rue Pierre De Coubertin 31400 Toulouse", "Temperature", false));
    }
    @Test
    void send_alert_on_temperature_maximum_exceeded() throws IOException, ApiCommunicationError, InterruptedException {
        ArrayList<String> alerts = configTest("temp/max_alert", "enable_all");
        assertEquals(1, alerts.size());
        assertTrue(isAlertSent(alerts, "VelocityDreamer", "40 rue Pierre De Coubertin 31400 Toulouse", "Temperature", true));
    }

    @Test
    void send_alert_on_rainfall_minimum_exceeded() throws IOException, ApiCommunicationError, InterruptedException {
        ArrayList<String> alerts = configTest("rain/min_alert", "enable_all");
        assertEquals(1, alerts.size());
        assertTrue(isAlertSent(alerts, "VelocityDreamer", "40 rue Pierre De Coubertin 31400 Toulouse", "Rain", false));
    }
    @Test
    void send_alert_on_rainfall_maximum_exceeded() throws IOException, ApiCommunicationError, InterruptedException {
        ArrayList<String> alerts = configTest("rain/max_alert", "enable_all");
        assertEquals(1, alerts.size());
        assertTrue(isAlertSent(alerts, "VelocityDreamer", "40 rue Pierre De Coubertin 31400 Toulouse", "Rain", true));
    }

    @Test
    void send_alert_on_wind_minimum_exceeded() throws IOException, ApiCommunicationError, InterruptedException {
        ArrayList<String> alerts = configTest("wind/min_alert", "enable_all");
        assertEquals(1, alerts.size());
        assertTrue(isAlertSent(alerts, "VelocityDreamer", "40 rue Pierre De Coubertin 31400 Toulouse", "Wind", false));
    }
    @Test
    void send_alert_on_wind_maximum_exceeded() throws IOException, ApiCommunicationError, InterruptedException {
        ArrayList<String> alerts = configTest("wind/max_alert", "enable_all");
        assertEquals(1, alerts.size());
        assertTrue(isAlertSent(alerts, "VelocityDreamer", "40 rue Pierre De Coubertin 31400 Toulouse", "Wind", true));
    }

    @Test
    void dont_send_alert_on_threshold_not_exceeded() throws ApiCommunicationError, InterruptedException, IOException {
        ArrayList<String> alerts = configTest("no_alert", "enable_all");
        assertEquals(0, alerts.size());
    }

    @Test
    void dont_send_alert_when_user_notifications_disabled() throws ApiCommunicationError, InterruptedException, IOException {
        ArrayList<String> all_alerts = configTest("all_alert", "disable_user");
        ArrayList<String> no_alerts = configTest("no_alert", "disable_user");
        assertEquals(0, all_alerts.size());
        assertEquals(0, no_alerts.size());
    }

    @Test
    void dont_send_alert_when_address_notifications_disabled() throws ApiCommunicationError, InterruptedException, IOException {
        ArrayList<String> alerts = configTest("all_alert", "disable_address");
        assertEquals(0, alerts.size());
    }

    @Test
    void send_alert_on_every_user_threshold() throws ApiCommunicationError, InterruptedException, IOException {
        ArrayList<String> alerts = configTest("2_user_temp_max_alert", "enable_all");
        assertTrue(isAlertSent(alerts, "VelocityDreamer", "40 rue Pierre De Coubertin 31400 Toulouse", "Temperature", true));
        assertTrue(isAlertSent(alerts, "BinaryBlaze", "90 place Stanislas 54100 Nancy", "Temperature", true));
    }

    @Test
    void send_alert_on_every_user_address() throws ApiCommunicationError, InterruptedException, IOException {
        ArrayList<String> alerts = configTest("2_address_temp_max_alert", "enable_all");
        assertTrue(isAlertSent(alerts, "VelocityDreamer", "40 rue Pierre De Coubertin 31400 Toulouse", "Temperature", true));
        assertTrue(isAlertSent(alerts, "VelocityDreamer", "90 place Stanislas 54100 Nancy", "Temperature", true));
    }
}
