import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class HuisartsUser extends User {

    private Map<String, Double> consultRates; // Een map om de tarieven op te slaan

    public HuisartsUser(int id, String name, List<String> roles) {
        super(id, name, roles);
        consultRates = new HashMap<>();
        // Voeg de bestaande tarieven toe aan de map
        consultRates.put("Consult", 21.50);
        consultRates.put("Huisbezoek", 43.00);
        consultRates.put("Gezondheidsonderzoek", 43.00);
    }

    public Double getConsultRate(String serviceName) {
        return consultRates.get(serviceName);
    }


    public boolean canEditMedicijnen() {
        return false;
    }
}
