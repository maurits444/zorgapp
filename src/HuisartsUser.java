import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class HuisartsUser extends User {

    private final Map<String, Double> consultRates;

    public HuisartsUser(int id, String name, List<String> roles) {
        super(id, name, roles);
        consultRates = new HashMap<>();
        consultRates.put("Consult", 21.50);
        consultRates.put("Huisbezoek", 43.00);
        consultRates.put("Gezondheidsonderzoek", 43.00);
    }

    // Getters
    public Double getConsultRate(String serviceName) {
        return consultRates.get(serviceName);
    }

    // Access
    public boolean canEditMedicine() {
        return true;
    }
}
