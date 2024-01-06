package tempGuardian;

import java.util.ArrayList;

public interface IConfigurationSystem {

    ArrayList<IUser> getAllUsers();
    ArrayList<IAddress> getUserAddresses(IUser user);
    void executeSystem(IPositionAgent positionAgent, IWeatherAgent weatherAgent, String outputPath) throws InterruptedException;
}
