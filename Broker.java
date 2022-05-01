import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Broker extends Remote {

    void registerServer(String serverName, String remoteHost) throws RemoteException;

    Object executeService(String serviceName, List<Object> serviceParameters) throws RemoteException;

    void registerService(String serverName, String serviceName, List<Object> serviceParameters, String returnType) throws RemoteException;

    void terminateService(String serverName, String serviceName) throws RemoteException;

    List<Service> servicesList() throws RemoteException;
}
