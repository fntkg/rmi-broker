import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TimeRetriever extends Remote {
    /**
     * @return Actual time
     * @throws RemoteException
     */
    String getTime() throws RemoteException;
}
