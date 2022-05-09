import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface DateRetriever extends Remote {
    /**
     * @return Actual day of the week
     */
    String getDate() throws RemoteException;

    /**
     * @return Actual day of the week but with more text
     */
    String getCompleteDate() throws RemoteException;

    String getDateWithArgs(ArrayList<?> arguments) throws RemoteException;
}
