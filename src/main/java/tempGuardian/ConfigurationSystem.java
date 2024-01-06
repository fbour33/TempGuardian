package tempGuardian;

import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class ConfigurationSystem implements IConfigurationSystem {

    HashMap<String, ArrayList<IAddress>> userList = new HashMap<>();
    HashMap<String, IUser> userSet = new HashMap<>();

    public ConfigurationSystem(String filePath) {
        try {
            List<InputAddress> beans = new CsvToBeanBuilder<InputAddress>(new FileReader(filePath))
                    .withType(InputAddress.class)
                    .build().parse();
            beans.forEach(this::addAddress);
        }catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    private void addAddress(InputAddress inputAddress){
        IUser user = userSet.get(inputAddress.getUsername());
        IAddress newAddress = new Address(
                inputAddress.getAddress(),
                new WeatherThreshold(WeatherDataName.TEMP, inputAddress.getValue(WeatherDataName.TEMP, true), inputAddress.getValue(WeatherDataName.TEMP, false)),
                new WeatherThreshold(WeatherDataName.WIND, inputAddress.getValue(WeatherDataName.WIND, true), inputAddress.getValue(WeatherDataName.WIND, false)),
                new WeatherThreshold(WeatherDataName.RAIN, inputAddress.getValue(WeatherDataName.RAIN, true), inputAddress.getValue(WeatherDataName.RAIN, false))
        );
        if (user != null){
            user.addAddress(newAddress);
        }else{
            IUser newUser = new User(inputAddress.getUsername());
            newUser.addAddress(newAddress);
            userSet.put(inputAddress.getUsername(), newUser);
        }
    }


    @Override
    public ArrayList<IUser> getAllUsers() {
        return new ArrayList<>(userSet.values());
    }

    @Override
    public ArrayList<IAddress> getUserAddresses(IUser user) {
        return userList.get(user.getName());
    }

// Use to test code
/*    public static void main(String[] args) {
        IConfigurationSystem configurationSystem = new ConfigurationSystem("data/input.csv");
        ArrayList<IUser> userArrayList = configurationSystem.getAllUsers();
        int count = 1;
        for (IUser user: userArrayList){
            System.out.println("[" + count + "]" + user);
            count++;
        }
    }*/
}
