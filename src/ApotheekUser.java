import java.util.List;

public class ApotheekUser extends User {
    public ApotheekUser(int userId, String userName, List<String> roles) {
        super(userId, userName, roles);
    }
    public boolean canEditMedicijnen() {
        return true;
    }
}
