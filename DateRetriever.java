import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DateRetriever extends Remote {
    /**
     * @return Actual day of the week
     * @throws RemoteException
     */
    String getDate() throws RemoteException;
}
