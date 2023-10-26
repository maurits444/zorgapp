import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TandartsUser extends User {
    private Map<String, Double> tandartsTarieven;

    public TandartsUser(int id, String name, List<String> roles) {
        super(id, name, roles);
        tandartsTarieven = new HashMap<>();
        // Voeg de tarieven toe aan de lijst
        tandartsTarieven.put("Routine controle", 20.00);
        tandartsTarieven.put("Extractie", 30.00);
        tandartsTarieven.put("Fluoridebehandeling", 30.00);
        tandartsTarieven.put("Wortelkanaalbehandeling", 55.00);
        tandartsTarieven.put("Implantaat", 55.00);
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

    // Methode om tarief op te halen op basis van de dienstnaam
    public double getTandartsTarief(String dienst) {
        if (tandartsTarieven.containsKey(dienst)) {
            return tandartsTarieven.get(dienst);
        } else {
            // Return een standaardwaarde of geef een foutmelding terug
            return 0.0; // Of een andere waarde die je passend vindt
        }
    }
}
