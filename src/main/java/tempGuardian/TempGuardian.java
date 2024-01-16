package tempGuardian;

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
                    if (threshold.isThresholdExceeded(weatherData) && !user.areNotificationsDisabled(address)) {
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
}
