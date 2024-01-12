package tempGuardian;

public class ApiCommunicationError extends Exception{
    public ApiCommunicationError(String apiName, String message){
        super("[" + apiName + "] Error: " + message);
    }
}
