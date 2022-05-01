import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Broker extends Remote {

    /**
     * @param serverName Name of the server to register
     * @param remoteHost Address of the server to register
     */
    void registerServer(String serverName, String remoteHost) throws RemoteException;

    /**
     * @param serverName Name of the service to execute
     * @param methodParameters Parameters of the service to execute
     * @return
     */
    String executeMethod(String serverName, String[] methodParameters) throws RemoteException;

    /**
     * @param serverName Name of the server
     * @param methodName Name of the service
     * @param methodParameters Parameters of the service
     * @param returnType Return type
     */
    void registerMethod(String serverName, String methodName, String[] methodParameters, String returnType);

    /**
     * @param serverName Name of the server
     * @param methodName Name of the service
     */
    void terminateMethod(String serverName, String methodName);


    /**
     * @return List of available methods
     */
    List<Method> methodsList();
}
