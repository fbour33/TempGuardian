package tempGuardian;

public interface IPositionAgent {

    Position getPositionFromAddress(IAddress address) throws ApiCommunicationError;

}
