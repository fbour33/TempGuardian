package tempGuardian;


import okhttp3.OkHttpClient;

public interface IPositionAgent {

    public Position getPositionFromAddress(IAddress address);

}
