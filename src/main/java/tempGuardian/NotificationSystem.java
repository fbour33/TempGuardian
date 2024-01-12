package tempGuardian;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class NotificationSystem implements INotificationSystem {

    String outputFilePath;
    public NotificationSystem(String outputFilePath) {
        this.outputFilePath = outputFilePath;
        try (CSVWriter writer = new CSVWriter(new FileWriter(outputFilePath))){
            writer.writeNext(new String[]{"username", "address", "message", "data"});
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void sendAlert(String username, String address, String message, String data) {
        String[] writeData = {username, address, message, data};

        try (CSVWriter writer = new CSVWriter(new FileWriter(outputFilePath, true))){
            System.out.println("Alert: " + Arrays.toString(writeData));
            writer.writeNext(writeData);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
