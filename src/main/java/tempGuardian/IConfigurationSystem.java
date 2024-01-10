package tempGuardian;

import java.util.ArrayList;

public interface IConfigurationSystem {

    ArrayList<IUser> getAllUsers();
    ArrayList<IAddress> getUserAddresses(IUser user);
}
