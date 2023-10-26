import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class TandartsUser extends User {
    private Map<String, Double> consultRates; // Een map om de tarieven op te slaan

    public TandartsUser(int id, String name, List<String> roles) {
        super(id, name, roles);
        consultRates = new HashMap<>();
        // Voeg de bestaande tarieven toe aan de map
        consultRates.put("Routine controle", 20.00);
        consultRates.put("Extractie", 30.00);
        consultRates.put("Fluoridebehandeling", 30.00);
        consultRates.put("Wortelkanaalbehandeling", 55.00);
        consultRates.put("Implantaat", 55.00);
    }

    // Methode om consulttarief op te halen
    public Double getConsultRate(String serviceName) {
        return consultRates.get(serviceName);
    }

    public boolean canAccessPatientLength() {
        return false;
    }

    public boolean canAccessPatientWeight() {
        return false;
    }

    public boolean canEditMedicijnen() {
        return false;
    }
}
