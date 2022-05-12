import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class DateRetrieverImpl extends UnicastRemoteObject implements DateRetriever {
    String[] WEEK_DAYS = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    public DateRetrieverImpl() throws RemoteException {
        super(); // Call UnicastRemoteObject constructor
    }

    public String getDate() throws RemoteException {
        Calendar c = Calendar.getInstance();
        return WEEK_DAYS[c.get(Calendar.DAY_OF_WEEK)];
    }

    public String getCompleteDate() throws RemoteException {
        Calendar c = Calendar.getInstance();
        return "Today is: " + WEEK_DAYS[c.get(Calendar.DAY_OF_WEEK)];
    }

    public String getDateWithArgs(ArrayList<?> arguments) throws RemoteException {
        return "Hi " + arguments.get(0) + ", I am DateRetriever";
    }

    public static void main(String[] args) {
        // Set the directory where the java.policy is located
        // The second argument is the path to the java.policy
        System.setProperty("java.security.policy", "./java.policy");

        // Create security administrator
        System.setSecurityManager(new SecurityManager());

        // Name or IP of the host where RMI resides
        String hostName = "155.210.154.201";

        try {
            // Create remote object
            DateRetrieverImpl obj = new DateRetrieverImpl();

            // Register remote object
            Naming.rebind("//" + "155.210.154.202" + "/757024DateRetriever", obj);
            System.out.println("[+] Remote object successfully registered");

            // Register server and services
            Broker broker = (Broker) Naming.lookup("//" + hostName + "/757024Broker");
            System.out.println("[+] Connection with broker established");
            broker.registerServer("/757024DateRetriever", "155.210.154.202");
            System.out.println("[+] Server registered on broker");
            broker.registerService("/757024DateRetriever", "getDate", null, "String");
            Map<String, String> params = new HashMap<>();
            params.put("name", "String"); params.put("age", "int");
            broker.registerService("/757024DateRetriever", "getDateWithArgs", params, "String");
            System.out.println("[+] Services registered on broker");

            // Terminate getDate and register getCompleteDate
            System.out.println("Press enter to terminate getDate() and register getCompleteDate()");
            System.console().readLine();
            broker.terminateService("/757024DateRetriever", "getDate");
            broker.registerService("/757024DateRetriever", "getCompleteDate", null, "String");
            System.out.println("[+] New service registered on broker");
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
