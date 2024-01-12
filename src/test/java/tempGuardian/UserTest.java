package tempGuardian;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tempGuardian.IAddress;
import tempGuardian.User;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserTest {

    IAddress address1;
    IAddress address2;
    final String username = "test_username";
    final String address_location = "test_location";
    @BeforeEach
    void setup(){
        address1 = mock(IAddress.class);
        address2 = mock(IAddress.class);
    }

    @Test
    void add_address_correctly(){
        User user = new User(username);
        user.addAddress(address1);
        when(address1.getLocation()).thenReturn(address_location);
        ArrayList<IAddress> addressList = user.getAddresses();
        assertEquals(1, addressList.size());
        assertEquals(address_location, addressList.get(0).getLocation());
    }

    @Test
    void get_name_correctly(){
        User user = new User(username);
        assertEquals(username, user.getName());
    }

    @Test
    void disable_all_notifications_correctly(){
        User user = new User(username);
        user.addAddress(address1);
        user.addAddress(address2);
        assertFalse(user.areNotificationsDisabled());
        user.disableNotifications();
        assertTrue(user.areNotificationsDisabled());
    }

    @Test
    void disable_specific_notifications_correctly(){
        User user = new User(username);
        user.addAddress(address1);
        when(address1.getLocation()).thenReturn(address_location);
        user.addAddress(address2);
        when(address2.getLocation()).thenReturn("null");
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

    @Test
    void are_notifications_disabled_for_not_user_address(){
        User user = new User(username);
        assertFalse(user.areNotificationsDisabled(address1));
    }

    @Test
    void toString_correctly(){
        User user = new User(username);
        user.addAddress(address1);
        when(address1.toString()).thenReturn(address_location);
        String message = user.toString();
        assertTrue(message.contains("User"));
        assertTrue(message.contains("username"));
        assertTrue(message.contains(username));
        assertTrue(message.contains("false"));
        assertTrue(message.contains(address_location));
    }
}
