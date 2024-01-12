package tempGuardian;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class TempGuardian {
    private final IConfigurationSystem configurationSystem;
    private final IWeatherAgent weatherAgent;
    private final IPositionAgent positionAgent;
    private final INotificationSystem notificationSystem;

    public TempGuardian(IConfigurationSystem configurationSystem, IWeatherAgent weatherAgent, IPositionAgent positionAgent, INotificationSystem notificationSystem){
        this.configurationSystem = configurationSystem;
        this.weatherAgent = weatherAgent;
        this.positionAgent = positionAgent;
        this.notificationSystem = notificationSystem;
    }

    public void executeSystem() throws InterruptedException {
        for (IUser user : configurationSystem.getAllUsers()) {
            for (IAddress address : configurationSystem.getUserAddresses(user)) {
                Position position = positionAgent.getPositionFromAddress(address);
                for (IWeatherThreshold threshold : address.getThresholds()) {
                    IWeatherData weatherData = weatherAgent.getWeatherData(position);
                    if (threshold.isThresholdExceeded(weatherData)) {
                        notificationSystem.sendAlert(
                                user.getName(),
                                address.getLocation(),
                                threshold.generateThresholdMessage(weatherData),
                                threshold.generateThresholdDataMessage(weatherData)
                        );
                    }
                }
                Thread.sleep(1000);
            }
        }
    }


    // Use to test code
    public static void main(String[] args) throws InterruptedException {
        try {
            IConfigurationSystem configurationSystem = new ConfigurationSystem("data/input.csv");
            IPositionAgent positionAgent = new PositionAgent();
            IWeatherAgent weatherAgent = new WeatherAgent();
            INotificationSystem notificationSystem = new NotificationSystem("data/test_main.csv");
            TempGuardian tempGuardian = new TempGuardian(configurationSystem, weatherAgent, positionAgent, notificationSystem);
            ArrayList<IUser> userArrayList = configurationSystem.getAllUsers();
            int count = 1;
            for (IUser user : userArrayList) {
                System.out.println("[" + count + "]" + user);
                count++;
            }
            tempGuardian.executeSystem();
        }catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }
    }
}
