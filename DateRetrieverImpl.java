import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Calendar;

public class DateRetrieverImpl extends UnicastRemoteObject implements DateRetriever {
    String[] WEEK_DAYS = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sundar"};

    public DateRetrieverImpl() throws RemoteException {
        super(); // Call UnicastRemoteObject constructor
    }

    public String getDate() throws RemoteException {
        Calendar c = Calendar.getInstance();
        return WEEK_DAYS[c.get(Calendar.DAY_OF_WEEK)];
    }

    public static void main(String[] args) {

        // Name or IP of the host where RMI resides
        String hostName = "127.0.0.1";

        try {
            // Create remote object
            TimeRetrieverImpl obj = new TimeRetrieverImpl();

            // Register remote object
            Naming.rebind("//" + hostName + "/757024DateRetriever", obj);
            System.out.println("Remote object successfully registered");

            // Register services
            Broker broker = (Broker) Naming.lookup("//" + hostName + "757024Broker");
            broker.registerMethod("ServerA", "getDate", new String[0], "String");
        }
        catch(Exception ex) {
            System.out.println(ex);
        }
    }
}
