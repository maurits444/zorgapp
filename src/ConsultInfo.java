public class ConsultInfo {
    private String serviceName;
    private double rate;

    public ConsultInfo(String serviceName, double rate) {
        this.serviceName = serviceName;
        this.rate = rate;
    }

    // Getters
    public String getServiceName() {
        return serviceName;
    }

    public double getRate() {
        return rate;
    }
}
