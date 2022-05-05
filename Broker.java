import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface Broker extends Remote {

    void registerServer(String serverName, String remoteHost) throws RemoteException;

    Object executeService(String serviceName, List<?> serviceParameters) throws RemoteException;

    void registerService(String serverName, String serviceName, Map<String, String> serviceParameters, String returnType) throws RemoteException;

    void terminateService(String serverName, String serviceName) throws RemoteException;

    List<Service> listServices() throws RemoteException;
}
