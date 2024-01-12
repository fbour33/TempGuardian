import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import tempGuardian.NotificationSystem;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class NotificationSystemTest {


    private static ArrayList<String[]> alerts = new ArrayList<>(2);
    static String alertStringVelocityDream;
    static String alertStringQuantumExplorer;

    @BeforeAll
    public static void setup(){
        alerts.add(new String[]{"VelocityDreamer", "40 rue Pierre De Coubertin 31400 Toulouse", "[Rain fall] Minimum threshold exceeded", "0.0 < 0.4mm"});
        alerts.add(new String[]{"QuantumExplorer", "65 rue Charles Corbeau 45400 Fleury-les-aubrais", "[Rain fall] Minimum threshold exceeded", "0.0 < 1.0mm"});
    }

    @Test
    public void create_output_file() throws IOException {

        String expectedValue = "\"username\",\"address\",\"message\",\"data\"";
        String filePath = "data/test/create_file.csv";

        NotificationSystem notificationSystem = new NotificationSystem(filePath);
        File file = new File(filePath);
        assertTrue(file.exists());

        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            String currentLine = reader.readLine();
            assertEquals(expectedValue, currentLine);
        }

        file.delete();
    }


    private static String formatAlert(String[] alert) {
        return String.format("\"%s\",\"%s\",\"%s\",\"%s\"", alert[0], alert[1], alert[2], alert[3]);
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
    @Test
    public void send_empty_alert() throws IOException {
        String filePath = "data/test/one_alert.csv";
        NotificationSystem notificationSystem = new NotificationSystem(filePath);
        String[] alert = alerts.get(0);
        notificationSystem.sendAlert(alert[0], alert[1], alert[2], alert[3]);

        ArrayList<String> stringAlerts = getAlertsInFile(filePath);
        assertTrue(stringAlerts.contains(formatAlert(alert)));
        File file = new File(filePath);
        file.delete();
    }

    @Test
    public void send_multiple_alert() throws IOException {
        String filePath = "data/test/multiple_alerts.csv";
        NotificationSystem notificationSystem = new NotificationSystem(filePath);
        for(String[] alert: alerts){
            notificationSystem.sendAlert(alert[0], alert[1], alert[2], alert[3]);
        }
        for(String[] alert: alerts) {
            ArrayList<String> stringAlerts = getAlertsInFile(filePath);
            assertTrue(stringAlerts.contains(formatAlert(alert)));
        }
        File file = new File(filePath);
        file.delete();
    }

}
