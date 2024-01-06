package tempGuardian;

import java.util.ArrayList;
import java.util.HashMap;

public interface IConfigurationSystem {

    ArrayList<IUser> getAllUsers();
    ArrayList<IAddress> getUserAddresses(IUser user);

}
