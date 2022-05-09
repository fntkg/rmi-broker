import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface TimeRetriever extends Remote {
    /**
     * @return Actual time
     * @throws RemoteException
     */
    String getTime() throws RemoteException;

    String getTimeWithArgs(ArrayList<?> arguments) throws RemoteException;
}
