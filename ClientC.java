import java.rmi.Naming;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
                int i = 1;
                System.out.println("[+] AVAILABLE SERVICES");
                for (Service service : servicesList){
                    System.out.println(i + ". " + service.getServiceName());
                    i++;
                }
                System.out.println("0. Exit");
                System.out.println("-------------");
                // SELECT A SERVICE
                System.out.println("[+] SELECT A SERVICE OR 0 TO EXIT: ");
                String selectedService = System.console().readLine();
                Service service = servicesList.get(Integer.parseInt(selectedService)-1);
                System.out.println("\n[+] SELECTED SERVICE: " + service.getServiceName());

                // TODO Check args of the service
                Map<String, String> params = service.getServiceParameters();
                if (!params.isEmpty()) {
                    System.out.println("[+] THIS SERVICE NEED ARGS:");
                    System.out.println(params);
                    for (int j = 0; i < params.size(); i++) {

                    }
                }

                // TODO Ask the user for the args

                // EXECUTE SELECTED SERVICE
                if (Integer.parseInt(selectedService) == 99) {
                    continueExecution = false;
                }
                else {
                    System.out.println("[+] Answer from server:");
                    System.out.println(server.executeService(service.getServiceName(), new ArrayList<>())); // TODO Y si no se puede imprimir?
                    System.out.println("\n");
                }
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
