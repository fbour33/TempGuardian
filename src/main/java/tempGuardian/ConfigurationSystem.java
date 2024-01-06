package tempGuardian;

import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class ConfigurationSystem implements IConfigurationSystem {

    HashMap<String, IUser> userSet = new HashMap<>();

    public ConfigurationSystem(String filePath) {
        try {
            List<InputAddress> beans = new CsvToBeanBuilder<InputAddress>(new FileReader(filePath))
                    .withType(InputAddress.class)
                    .build().parse();
            beans.forEach(this::addAddress);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
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

    @Override
    public void executeSystem(IPositionAgent positionAgent, IWeatherAgent weatherAgent, String outputPath) throws InterruptedException {
        INotificationSystem notificationSystem = new NotificationSystem(outputPath);
        for (IUser user : getAllUsers()) {
            for (IAddress address : getUserAddresses(user)) {
                for (IWeatherThreshold threshold : address.getThresholds()) {
                    Position position = positionAgent.getPositionFromAddress(address);
                    IWeatherData weatherData = weatherAgent.getWeatherData(position);
                    if (threshold.isThresholdExceeded(weatherData)) {
                        notificationSystem.sendAlert(
                                user.getName(),
                                threshold.generateThresholdMessage(weatherData),
                                address.getLocation(),
                                threshold.generateThresholdDataMessage(weatherData)
                        );
                    }
                    Thread.sleep(1000);
                }
            }
        }
    }


    // Use to test code
    public static void main(String[] args) throws InterruptedException {
        IConfigurationSystem configurationSystem = new ConfigurationSystem("data/input.csv");
        ArrayList<IUser> userArrayList = configurationSystem.getAllUsers();
        int count = 1;
        for (IUser user : userArrayList) {
            System.out.println("[" + count + "]" + user);
            count++;
        }

        IPositionAgent positionAgent = new PositionAgent();
        IWeatherAgent weatherAgent = new WeatherAgent();
        configurationSystem.executeSystem(positionAgent, weatherAgent, "data/test_main.csv");
    }
}
