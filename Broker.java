import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface Broker extends Remote {

    /**
     * Register a new server on the broker registry
     * @param serverName Name of the server to register
     * @param remoteHost Address of the server to register
     * @throws RemoteException Communication-related exceptions
     */
    void registerServer(String serverName, String remoteHost) throws RemoteException;

    /**
     * Execute a service
     * @param serviceName Name of the service to execute
     * @param serviceParameters List of arguments for the service
     * @return The result of executing the service
     * @throws RemoteException Communication-related exceptions
     */
    Object executeService(String serviceName, List<?> serviceParameters) throws RemoteException;

    /**
     * Register a new service on the broker registry
     * @param serverName Name of the server who registers the service
     * @param serviceName Name of the service to register
     * @param serviceParameters Needed arguments of the service
     * @param returnType returnType of the service
     * @throws RemoteException Communication-related exceptions
     */
    void registerService(String serverName, String serviceName, Map<String, String> serviceParameters, String returnType) throws RemoteException;

    /**
     * Terminate a service from the registry
     * @param serverName Name of the server who owns the service
     * @param serviceName Name of the service
     * @throws RemoteException Communication-related exceptions
     */
    void terminateService(String serverName, String serviceName) throws RemoteException;

    List<Service> listServices() throws RemoteException;
}
