package tempGuardian;

import java.util.ArrayList;

public interface IConfigurationSystem {

    public void setUpUsers();
    public ArrayList<IUser> getAllUsers();
    public IUser getUser(IUser user);

}
