package tempGuardian;

import java.util.ArrayList;

public interface IUser {

    String getName();
    void disableNotifications();
    void disableNotifications(IAddress... addresses);
    boolean areNotificationsDisabled();
    boolean areNotificationsDisabled(IAddress address);
    ArrayList<IAddress> getAddresses();
    void addAddress(IAddress address);
}
