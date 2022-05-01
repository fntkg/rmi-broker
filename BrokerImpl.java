import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BrokerImpl extends UnicastRemoteObject implements Broker {
    private Map<String, String> serversMap = new HashMap<String, String>();
    private Map<String, List<Method>> servicesMap = new HashMap<String, List<Method>>();

    public BrokerImpl() throws RemoteException {
        super(); // Call UnicastRemoteObject constructor
    }

    /**
     * @param serverName Name of the server to register
     * @param remoteHost     Address of the server to register
     * @throws RemoteException
     */
    @Override
    public void registerServer(String serverName, String remoteHost) throws RemoteException {
        serversMap.put(serverName, remoteHost);
    }

    /**
     * @param serverName       Name of the service to execute
     * @param methodParameters Parameters of the service to execute
     * @return
     * @throws RemoteException
     */
    @Override
    public String executeMethod(String serverName, String[] methodParameters) throws RemoteException {
        // TODO: Implement this method
        return null;
    }

    /**
     * @param serverName       Name of the server
     * @param methodName       Name of the service
     * @param methodParameters Parameters of the service
     * @param returnType       Return type
     */
    @Override
    public void registerMethod(String serverName, String methodName, String[] methodParameters, String returnType) {
        // Get list of services from serverName
        List<Method> services = this.servicesMap.get(serverName);
        // Add new service
        Method service = new Method(methodName, methodParameters, returnType);
        services.add(service);
        // Add updated list to servicesMap
        servicesMap.put(methodName, services);
    }

    /**
     * @param serverName Name of the server
     * @param methodName Name of the service
     */
    @Override
    public void terminateMethod(String serverName, String methodName) {
        // Get list of services from serverName
        List<Method> services = this.servicesMap.get(serverName);
        // Delete service
        services.remove(findByName(services, methodName));
        // Add updated list to servicesMap
        servicesMap.put(methodName, services);
    }

    /**
     * @return List of available methods
     */
    @Override
    public List<Method> methodsList() {
        List<Method> methodsList = null;
        // Concat all lists with methods
        // Iterate over the hashmap getting the lists
        for (List<Method> value : servicesMap.values()) {
            Stream.concat(methodsList.stream(), value.stream()).collect(Collectors.toList());
        }
        return methodsList;
    }

    public static void main(String[] args) {

        // Name or IP of the host where RMI resides
        String hostName = "127.0.0.1";

        try {
            // Create remote object
            BrokerImpl obj = new BrokerImpl();

            // Register remote object
            Naming.rebind("//" + hostName + "/757024Broker", obj);
            System.out.println("Remote object successfully registered");
        }
        catch(Exception ex) {
            System.out.println(ex);
        }
    }

    // AUXILIARY METHODS
    private static Method findByName(List<Method> methodsList, String methodName){
        return methodsList.stream().filter(method -> methodName.equals(method.getMethodName())).findFirst().orElse(null);
    }
}
