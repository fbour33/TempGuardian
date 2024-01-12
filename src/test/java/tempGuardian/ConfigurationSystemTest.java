package tempGuardian;

import org.junit.jupiter.api.Test;
import tempGuardian.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ConfigurationSystemTest {

    @Test
    void init_configurationSystem_with_existing_file(){
        assertDoesNotThrow(() -> {
            new ConfigurationSystem("data/test/empty_user.csv");
        });
    }

    @Test
    void init_configurationSystem_with_non_existing_file(){
        assertThrows(FileNotFoundException.class, () -> {
            new ConfigurationSystem("NonExistingFile.csv");
        });
    }

    @Test
    void init_configurationSystem_with_empty_data() throws FileNotFoundException {
        ConfigurationSystem configurationSystem = new ConfigurationSystem("data/test/empty_user.csv");
        assertTrue(configurationSystem.getAllUsers().isEmpty());
    }

    @Test
    void get_one_user_address() throws FileNotFoundException {
        ConfigurationSystem configurationSystem = new ConfigurationSystem("data/test/one_user_with_one_address.csv");
        IUser user = mock(User.class);
        when(user.getName()).thenReturn("StellarJourney92");
        ArrayList<IAddress> userAddress = configurationSystem.getUserAddresses(user);
        assertEquals(userAddress.size(), 1);
        assertEquals(userAddress.get(0).getLocation(), "61 Chemin Challet 59800 Lille");
    }

    @Test
     void adding_one_user_with_one_address() throws FileNotFoundException {
        ConfigurationSystem configurationSystem = new ConfigurationSystem("data/test/one_user_with_one_address.csv");
        ArrayList<IUser> userList = configurationSystem.getAllUsers();
        assertEquals(userList.size(), 1);
        IUser user = userList.get(0);
        assertEquals(user.getName(), "StellarJourney92");
        assertEquals(user.getAddresses().size(), 1);
        assertEquals(user.getAddresses().get(0).getLocation(), "61 Chemin Challet 59800 Lille");
        assertEquals(user.getAddresses().get(0).getThresholds().get(0).getMinThreshold(), -2);
        assertEquals(user.getAddresses().get(0).getThresholds().get(0).getMaxThreshold(), 8);
    }

    boolean isInAddressList(ArrayList<IAddress> addresses, String location){
        for(IAddress address: addresses)
            if(address.getLocation().equals(location))
                return true;
        return false;
    }

    ArrayList<IWeatherThreshold> getThresholdsByLocation(ArrayList<IAddress> addresses, String location){
        for(IAddress address: addresses)
            if(address.getLocation().equals(location))
                return address.getThresholds();
        return null;
    }

    @Test
    void adding_one_user_with_multiple_addresses() throws FileNotFoundException {
        ConfigurationSystem configurationSystem = new ConfigurationSystem("data/test/one_user_with_multiple_addresses.csv");
        ArrayList<IUser> userList = configurationSystem.getAllUsers();
        assertEquals(userList.size(), 1);

        IUser user = userList.get(0);
        assertEquals(user.getName(), "StellarJourney92");
        assertEquals(user.getAddresses().size(), 2);
        assertTrue(isInAddressList(user.getAddresses(), "61 Chemin Challet 59800 Lille"));
        ArrayList<IWeatherThreshold> thresholds = getThresholdsByLocation(user.getAddresses(), "61 Chemin Challet 59800 Lille");
        assertEquals(thresholds.get(0).getMinThreshold(), 5);
        assertEquals(thresholds.get(0).getMaxThreshold(), 20);

        assertTrue(isInAddressList(user.getAddresses(), "49 rue Reine Elisabeth 06500 Menton"));
        thresholds = getThresholdsByLocation(user.getAddresses(), "49 rue Reine Elisabeth 06500 Menton");
        assertEquals(thresholds.get(0).getMinThreshold(), 3.2);
        assertEquals(thresholds.get(0).getMaxThreshold(), 11.5);
    }

    @Test
    void adding_two_user_with_multiple_address() throws FileNotFoundException {
        ConfigurationSystem configurationSystem = new ConfigurationSystem("data/test/two_users_with_multiple_addresses.csv");
        ArrayList<IUser> userList = configurationSystem.getAllUsers();
        assertEquals(userList.size(), 2);

        IUser user = userList.get(1);
        assertEquals(user.getName(), "StellarJourney92");
        assertEquals(user.getAddresses().size(), 1);
        assertTrue(isInAddressList(user.getAddresses(), "61 Chemin Challet 59800 Lille"));
        ArrayList<IWeatherThreshold> thresholds = getThresholdsByLocation(user.getAddresses(), "61 Chemin Challet 59800 Lille");
        assertEquals(thresholds.get(0).getMinThreshold(), 5);
        assertEquals(thresholds.get(0).getMaxThreshold(), 20);

        user = userList.get(0);
        assertEquals(user.getName(), "QuantumExplorer");
        assertEquals(user.getAddresses().size(), 1);
        assertTrue(isInAddressList(user.getAddresses(), "65 rue Charles Corbeau 45400 Fleury-les-aubrais"));
        assertEquals(user.getAddresses().get(0).getThresholds().size(), 2);
        thresholds = getThresholdsByLocation(user.getAddresses(), "65 rue Charles Corbeau 45400 Fleury-les-aubrais");
        assertEquals(thresholds.get(0).getMinThreshold(), 10);
        assertEquals(thresholds.get(0).getMaxThreshold(), 25);
        assertEquals(thresholds.get(1).getMinThreshold(), 1);
        assertEquals(thresholds.get(1).getMaxThreshold(), 6);
    }



}
