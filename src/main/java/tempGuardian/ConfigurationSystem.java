package tempGuardian;

import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class ConfigurationSystem implements IConfigurationSystem {

    HashMap<String, ArrayList<IAddress>> userList = new HashMap<>();

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
        ArrayList<IAddress> userData = userList.get(inputAddress.getUsername());
        //TODO create new Address from input
        // IAddress newAddress = new Address();...
        if (userData != null){
            //TODO add newAddress to userData
        }else{
            ArrayList<IAddress> newUserData = new ArrayList<>();
            //TODO add newAddress to newUserData
            userList.put(inputAddress.getUsername(), newUserData);
        }
        System.out.println(inputAddress);
    }

    @Override
    public HashMap<String, ArrayList<IAddress>> getAllUsers() {
        return userList;
    }

    @Override
    public ArrayList<IAddress> getUserAddresses(IUser user) {
        return userList.get(user.getName());
    }

/* Use to test code
    public static void main(String[] args) {
        IConfigurationSystem configurationSystem = new ConfigurationSystem("data/input.csv");
        int count = 1;
        for (Map.Entry<String, ArrayList<IAddress>> entry: configurationSystem.getAllUsers().entrySet()){
            System.out.println("[" + count + "]" + entry.getKey() + entry.getValue());
            count++;
        }
    }*/
}
