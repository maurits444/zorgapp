import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class TandartsUser extends User {
    private final Map<String, Double> consultRates;

    public TandartsUser(int id, String name, List<String> roles) {
        super(id, name, roles);
        consultRates = new HashMap<>();
        consultRates.put("Routine controle", 20.00);
        consultRates.put("Extractie", 30.00);
        consultRates.put("Fluoridebehandeling", 30.00);
        consultRates.put("Wortelkanaalbehandeling", 55.00);
        consultRates.put("Implantaat", 55.00);
    }

    // Getters
    public Double getConsultRate(String serviceName) {
        return consultRates.get(serviceName);
    }

    // Access
    public boolean canAccessPatientLength() {
        return false;
    }
}
