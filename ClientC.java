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
            String hostname = "155.210.154.201";
            Broker server = (Broker) Naming.lookup("//" + hostname + "/757024Broker");
            System.out.println("[+] Connection with broker established");

            // Step 2: Remotely invoke server object methods
            // GET LIST OF SERVICES
            while (true) {
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
                if (Integer.parseInt(selectedService) == 0) {
                    break;
                }
                Service service = servicesList.get(Integer.parseInt(selectedService)-1);
                System.out.println("\n[+] SELECTED SERVICE: " + service.getServiceName());
                List<Object> arguments = new ArrayList<>();

                Map<String, String> params = service.getServiceParameters();
                if (params != null) {
                    System.out.println("[+] THIS SERVICE NEED ARGS:");
                    System.out.println(params);
                    System.out.println("\nWrite necessary args, one per line:");
                    System.out.println(params.size());
                    for (int j = 0; j <= params.size(); j++) {
                        arguments.add(System.console().readLine());
                        j++;
                    }
                }

                // EXECUTE SELECTED SERVICE
                System.out.println("[+] ANSWER FROM SERVER:");
                System.out.println(server.executeService(service.getServiceName(), arguments)); // What if it can not be printed?
                System.out.println("------------------------------\n");
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
