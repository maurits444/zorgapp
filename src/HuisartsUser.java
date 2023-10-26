import java.util.List;

public class HuisartsUser extends User {
    public HuisartsUser(int userId, String userName, List<String> roles) {
        super(userId, userName, roles);
    }
    public boolean canEditMedicijnen() {
        return false;
    }
}
