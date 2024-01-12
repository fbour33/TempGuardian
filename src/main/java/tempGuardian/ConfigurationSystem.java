package tempGuardian;

import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class ConfigurationSystem implements IConfigurationSystem {

    final HashMap<String, IUser> userSet = new HashMap<>();

    public ConfigurationSystem(String filePath) throws FileNotFoundException {
        List<InputAddress> beans = new CsvToBeanBuilder<InputAddress>(new FileReader(filePath))
                .withType(InputAddress.class)
                .build().parse();
        beans.forEach(this::addAddress);
    }

    private void addAddress(InputAddress inputAddress) {
        IUser user = userSet.get(inputAddress.getUsername());
        ArrayList<IWeatherThreshold> thresholdList = new ArrayList<>();
        if (inputAddress.isTempDefined())
            thresholdList.add(new TempThreshold(inputAddress.getMinTemp(), inputAddress.getMaxTemp()));
        if (inputAddress.isWindDefined())
            thresholdList.add(new WindThreshold(inputAddress.getMinWind(), inputAddress.getMaxWind()));
        if (inputAddress.isRainFallDefined())
            thresholdList.add(new RainThreshold(inputAddress.getMinRainfall(), inputAddress.getMaxRainfall()));
        IAddress newAddress = new Address(inputAddress.getAddress(), thresholdList.toArray(new IWeatherThreshold[0]));
        if (user != null) {
            user.addAddress(newAddress);
        } else {
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
        return userSet.get(user.getName()).getAddresses();
    }

}
