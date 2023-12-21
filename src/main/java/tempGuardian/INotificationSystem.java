package tempGuardian;

public interface INotificationSystem {

    public void sendAlert(String username, String message, String address, String data);
}
