import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Calendar;

public class TimeRetrieverImpl extends UnicastRemoteObject implements TimeRetriever {
    public TimeRetrieverImpl() throws RemoteException {
        super(); // Call UnicastRemoteObject constructor
    }

    public String getTime() throws RemoteException {
        Calendar c = Calendar.getInstance();
        return String.valueOf(c.get(Calendar.HOUR_OF_DAY)) + ':' + c.get(Calendar.MINUTE);
    }

    public static void main(String[] args) {

        // Name or IP of the host where RMI resides
        String hostName = "127.0.0.1";

        try {
            // Create remote object
            TimeRetrieverImpl obj = new TimeRetrieverImpl();

            // Register remote object
            Naming.rebind("//" + hostName + "/757024TimeRetriever", obj);
            System.out.println("Remote object successfully registered");

            // Register services
            Broker broker = (Broker) Naming.lookup("//" + hostName + "/757024Broker");
            broker.registerServer("/757024TimeRetriever", "127.0.0.1");
            broker.registerService("/757024TimeRetriever", "getHour", null, "String");
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
