package tempGuardian;

import java.util.ArrayList;

public interface IUser {

    public String getName();
    public void disableNotification();
    public void disableNotifications(IAddress... addresses);
    public boolean areNotificationsDisabled();
    public boolean areNotificationsDisabled(IAddress address);
    public ArrayList<IAddress> getAddresses();



}
