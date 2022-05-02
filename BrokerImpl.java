import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BrokerImpl extends UnicastRemoteObject implements Broker {
    private final Map<String, String> serversMap = new HashMap<>();
    private final List<Service> servicesList;

    public BrokerImpl() throws RemoteException {
        super(); // Call UnicastRemoteObject constructor
        this.servicesList = new ArrayList<>();
    }

    @Override
    public void registerServer(String serverName, String remoteHost) throws RemoteException {
        serversMap.put(serverName, remoteHost);
    }

    @Override
    public Object executeService(String serviceName, List<Object> serviceParameters) throws RemoteException {
        Service service = findByName(this.servicesList, serviceName);
        String serverName = service.getServerName();
        String serverAddress = serversMap.get(serverName);
        // Execute service
        try { // https://stackoverflow.com/questions/160970/how-do-i-invoke-a-java-method-when-given-the-method-name-as-a-string
            Object server = Naming.lookup("//" + serverAddress + serverName); // Get the remote object
            java.lang.reflect.Method method;
            method = server.getClass().getMethod(serviceName); // Find method
            return method.invoke(server); // TODO: How to call this method with args?
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public void registerService(String serverName, String serviceName, List<Object> serviceParameters, String returnType) throws RemoteException {
        // Create new service
        Service service = new Service(serviceName, serviceParameters, returnType, serverName);
        // Add new service to list of services
        this.servicesList.add(service);
    }

    @Override
    public void terminateService(String serverName, String serviceName) throws RemoteException {
        // Delete service
        this.servicesList.remove(findByName(this.servicesList, serviceName));
    }

    @Override
    public List<Service> listServices() throws RemoteException {
        return this.servicesList;
    }

    // AUXILIARY METHODS
    private static Service findByName(List<Service> methodsList, String methodName){
        return methodsList.stream().filter(service -> methodName.equals(service.getServiceName())).findFirst().orElse(null);
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
            ex.printStackTrace();
        }
    }
}
