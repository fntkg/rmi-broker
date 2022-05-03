import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Service implements Serializable {
    private final String serviceName;
    private final Map<String, String> serviceParameters;
    // number.getClass().getSimpleName()
    private final String returnType;
    private final String serverName;

    public Service(String serviceName, Map<String, String> serviceParameters, String returnType, String serverName) {
        this.serviceName = serviceName;
        this.serviceParameters = serviceParameters;
        this.returnType = returnType;
        this.serverName = serverName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public Map<String, String> getServiceParameters() {
        return serviceParameters;
    }

    public String getReturnType() {
        return returnType;
    }
    public String getServerName() {return serverName;}
}
