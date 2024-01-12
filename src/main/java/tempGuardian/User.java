package tempGuardian;

import java.util.ArrayList;
import java.util.HashMap;

public class User implements IUser {
    final String username;
    boolean areNotificationsDisabled = false;
    final HashMap<IAddress, Boolean> addressSet;

    public User(String username){
        this.username = username;
        addressSet = new HashMap<>();
    }

    @Override
    public void addAddress(IAddress address){
        addressSet.put(address, false);
    }

    @Override
    public String getName() {
        return username;
    }

    @Override
    public void disableNotifications() {
        areNotificationsDisabled = true;
    }

    @Override
    public void disableNotifications(IAddress... addresses) {
        for (IAddress toDisableAddress : addresses) {
            addressSet.replace(toDisableAddress, true);
        }
    }

    @Override
    public boolean areNotificationsDisabled() {
        return areNotificationsDisabled;
    }

    @Override
    public boolean areNotificationsDisabled(IAddress address) {
        Boolean addressNotifications = addressSet.get(address);
        if (addressNotifications != null) return addressNotifications || areNotificationsDisabled;
        return false;
    }

    @Override
    public ArrayList<IAddress> getAddresses() {
        return new ArrayList<>(addressSet.keySet());
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", areNotificationsDisabled=" + areNotificationsDisabled +
                ", addressSet=" + addressSet +
                '}';
    }
}
