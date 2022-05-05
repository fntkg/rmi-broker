import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

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
        System.out.println("[+] New server registered: " + serverName);
    }

    @Override
    public Object executeService(String serviceName, List<?> serviceParameters) throws RemoteException {
        Service service = findByName(this.servicesList, serviceName);
        String serverName = service.getServerName();
        String serverAddress = serversMap.get(serverName);
        System.out.println("[-] Executing " + serviceName + " from " + serverName);
        // Execute service
        try { // https://stackoverflow.com/questions/160970/how-do-i-invoke-a-java-method-when-given-the-method-name-as-a-string
            Object server = Naming.lookup("//" + serverAddress + serverName); // Get the remote object
            java.lang.reflect.Method method;
            method = server.getClass().getMethod(serviceName);
            // Transform serviceParameters to an array. Using a list because I want comfort
            Object[] arguments = serviceParameters.toArray();
            return method.invoke(server, arguments);
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public void registerService(String serverName, String serviceName, Map<String, String> serviceParameters, String returnType) throws RemoteException {
        // Create new service
        Service service = new Service(serviceName, serviceParameters, returnType, serverName);
        // Add new service to list of services
        this.servicesList.add(service);
        System.out.println("[+] Server " + serverName + " registered a new service: " + serviceName);
    }

    @Override
    public void terminateService(String serverName, String serviceName) throws RemoteException {
        // Delete service
        this.servicesList.remove(findByName(this.servicesList, serviceName));
        System.out.println("[+] Server " + serverName + " terminated a service: " + serviceName);
    }

    @Override
    public List<Service> listServices() throws RemoteException {
        System.out.println("[+] Returning services list");
        return this.servicesList;
    }

    // AUXILIARY METHODS
    private static Service findByName(List<Service> methodsList, String methodName){
        return methodsList.stream().filter(service -> methodName.equals(service.getServiceName())).findFirst().orElse(null);
    }

    public static void main(String[] args) {
        // Set the directory where the java.policy is located
        // The second argument is the path to the java.policy
        System.setProperty("java.security.policy", "./java.policy");

        // Create security administrator
        System.setSecurityManager(new SecurityManager());

        // Name or IP of the host where RMI resides
        String hostName = "127.0.0.1";

        try {
            // Create remote object
            BrokerImpl obj = new BrokerImpl();

            // Register remote object
            Naming.rebind("//" + hostName + "/757024Broker", obj);
            System.out.println("[+] Remote object successfully registered");
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
