package tempGuardian;

public interface INotificationSystem {
    void sendAlert(String username, String message, String address, String data);
}