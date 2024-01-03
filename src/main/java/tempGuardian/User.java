package tempGuardian;

import java.util.ArrayList;

public class User implements IUser {

    public String name;
    public ArrayList<IAddress> addressesList;

    @Override
    public String getName() {
        return name;
    }




}
