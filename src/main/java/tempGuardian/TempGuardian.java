package tempGuardian;

import java.io.FileNotFoundException;

public class TempGuardian {
    private final IConfigurationSystem configurationSystem;
    private final IWeatherAgent weatherAgent;
    private final IPositionAgent positionAgent;
    private final INotificationSystem notificationSystem;

    public TempGuardian(IConfigurationSystem configurationSystem, IWeatherAgent weatherAgent, IPositionAgent positionAgent, INotificationSystem notificationSystem) {
        this.configurationSystem = configurationSystem;
        this.weatherAgent = weatherAgent;
        this.positionAgent = positionAgent;
        this.notificationSystem = notificationSystem;
    }

    public void executeSystem() throws InterruptedException, ApiCommunicationError {
        for (IUser user : configurationSystem.getAllUsers()) {
            if (user.areNotificationsDisabled()) return;
            for (IAddress address : configurationSystem.getUserAddresses(user)) {
                if (user.areNotificationsDisabled(address)) return;
                Position position = positionAgent.getPositionFromAddress(address);
                for (IWeatherThreshold threshold : address.getThresholds()) {
                    IWeatherData weatherData = weatherAgent.getWeatherData(position);
                    if (threshold.isThresholdExceeded(weatherData)) {
                        notificationSystem.sendAlert(
                                user.getName(),
                                threshold.generateThresholdMessage(weatherData),
                                address.getLocation(),
                                threshold.generateThresholdDataMessage(weatherData)
                        );
                    }
                }
            }
        }
    }
    public static void main(String[] args) throws FileNotFoundException, ApiCommunicationError, InterruptedException {
        TempGuardian tempGuardian = new TempGuardian(
                new ConfigurationSystem("data/input.csv"),
                new WeatherAgent(),
                new PositionAgent(),
                new NotificationSystem("data/output.csv"));
        tempGuardian.executeSystem();
    }
}
