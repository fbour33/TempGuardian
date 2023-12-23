package tempGuardian;

import java.util.ArrayList;
import java.util.HashMap;

public interface IConfigurationSystem {

    public HashMap<String, ArrayList<IAddress>> getAllUsers();
    public ArrayList<IAddress> getUserAddresses(IUser user);

}
