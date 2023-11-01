import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class FysioUser extends User {

    private Map<String, Double> consultRates;

    public FysioUser(int id, String name, List<String> roles) {
        super(id, name, roles);
        consultRates = new HashMap<>();
        consultRates.put("Standaard behandeling", 17.50);
        consultRates.put("Tapen en bandageren", 25.00);
        consultRates.put("Mobilisatie", 25.00);
        consultRates.put("Massage", 25.00);
        consultRates.put("Manuele therapie", 50.00);
        consultRates.put("Dry Needling", 50.00);
        consultRates.put("Gebruik van oefenbad", 5.00);
    }

    public Double getConsultRate(String serviceName) {
        return consultRates.get(serviceName);
    }


    public boolean canEditMedicine() {
        return false;
    }
}
