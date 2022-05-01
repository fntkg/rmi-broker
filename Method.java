public class Method {
    private String methodName;
    private String[] methodParameters;
    private String returnType;

    public Method(String methodName, String[] methodParameters, String returnType) {
        this.methodName = methodName;
        this.methodParameters = methodParameters;
        this.returnType = returnType;
    }

    public String getMethodName() {
        return methodName;
    }

    public String[] getMethodParameters() {
        return methodParameters;
    }

    public String getReturnType() {
        return returnType;
    }
}
