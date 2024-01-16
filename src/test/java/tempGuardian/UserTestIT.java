package tempGuardian;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class UserTestIT {
    IAddress address1;
    final String username = "test_username";
    final String address_location = "test_location";
    @BeforeEach
    void setup(){
        address1 = new Address(address_location);
    }

    @Test
    void add_address_correctly(){
        User user = new User(username);
        user.addAddress(address1);
        ArrayList<IAddress> addressList = user.getAddresses();
        assertEquals(1, addressList.size());
        assertEquals(address_location, addressList.get(0).getLocation());
    }

    @Test
    void disable_specific_notifications_correctly(){
        User user = new User(username);
        user.addAddress(address1);
        user.addAddress(new Address("test_location_2"));
        ArrayList<IAddress> addressList = user.getAddresses();
        user.disableNotifications(address1);
        for (IAddress address: addressList){
            if (address.getLocation().equals(address_location)){
                assertTrue(user.areNotificationsDisabled(address));
            }else{
                assertFalse(user.areNotificationsDisabled(address));
            }
        }
    }
}
