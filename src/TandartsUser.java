import java.util.List;

public class TandartsUser extends User {
    public TandartsUser(int id, String name, List<String> roles) {
        super(id, name, roles);
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