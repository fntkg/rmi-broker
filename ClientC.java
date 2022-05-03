import java.rmi.Naming;
import java.util.List;

public class ClientC {
    public static void main(String[] args){
        // Set the directory where the java.policy is located
        System.setProperty("java.security.policy", "./java.policy");

        if (System.getSecurityManager() == null) {
            // Create security administrator
            System.setSecurityManager(new SecurityManager());
        }

        try {
            // Step 1: Get a reference to the server object created earlier
            // Name of the server host or its IP. This is where the remote object will be looked for.
            String hostname = "127.0.0.1";
            Broker server = (Broker) Naming.lookup("//" + hostname + "/757024Broker");
            System.out.println("[+] Connection with broker established");

            // Step 2: Remotely invoke server object methods
            boolean continueExecution = true;
            // GET LIST OF SERVICES
            while (continueExecution) {
                List<Service> servicesList = server.listServices();
                int i = 0;
                System.out.println("[+] Services list");
                System.out.println("-----------------");
                for (Service service : servicesList){
                    System.out.println(i + ". " + service.getServiceName());
                    i++;
                }
                System.out.println("99. Exit");
                System.out.println("-------------");
                // SELECT A SERVICE
                System.out.println("[+] Select a service: ");
                String selectedService = System.console().readLine();
                Service service = servicesList.get(Integer.parseInt(selectedService));
                System.out.println("[+] Service selected: " + service.getServiceName());

                // EXECUTE SELECTED SERVICE
                if (Integer.parseInt(selectedService) == 99) {
                    continueExecution = false;
                }
                else {
                    System.out.println("[+] Answer from server:");
                    System.out.println(server.executeService(service.getServiceName(), null));
                    System.out.println("\n");
                }
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
