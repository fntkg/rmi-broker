import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class TimeRetrieverImpl extends UnicastRemoteObject implements TimeRetriever {
    public TimeRetrieverImpl() throws RemoteException {
        super(); // Call UnicastRemoteObject constructor
    }

    public String getTime() throws RemoteException {
        Calendar c = Calendar.getInstance();
        return String.valueOf(c.get(Calendar.HOUR_OF_DAY)) + ':' + c.get(Calendar.MINUTE);
    }

    public String getTimeWithArgs(ArrayList<?> arguments) throws RemoteException {
        return "Hi " + arguments.get(0) + ", I am TimeRetriever.";
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
            TimeRetrieverImpl obj = new TimeRetrieverImpl();

            // Register remote object
            Naming.rebind("//" + hostName + "/757024TimeRetriever", obj);
            System.out.println("[+] Remote object successfully registered");

            // Register services
            Broker broker = (Broker) Naming.lookup("//" + hostName + "/757024Broker");
            System.out.println("[+] Connection with broker established");
            broker.registerServer("/757024TimeRetriever", "127.0.0.1");
            System.out.println("[+] Server registered on broker");
            Thread.sleep(2000);

            broker.registerService("/757024TimeRetriever", "getTime", null, "String");
            Map<String, String> parameters = new HashMap<>();
            parameters.put("name", "String"); parameters.put("age", "int"); parameters.put("weight", "int");
            broker.registerService("/757024TimeRetriever", "getTimeWithArgs", parameters, "String");
            System.out.println("[+] Services registered on broker");
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
