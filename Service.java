import java.util.List;

public class Service {
    private final String serviceName;
    private final List<Object> serviceParameters;
    private final String returnType;
    private final String serverName;

    public Service(String serviceName, List<Object> serviceParameters, String returnType, String serverName) {
        this.serviceName = serviceName;
        this.serviceParameters = serviceParameters;
        this.returnType = returnType;
        this.serverName = serverName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public List<Object> getServiceParameters() {
        return serviceParameters;
    }

    public String getReturnType() {
        return returnType;
    }
    public String getServerName() {return serverName;}
}
