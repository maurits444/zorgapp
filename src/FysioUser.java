import java.util.List;

public class FysioUser extends User {
    public FysioUser(int userId, String userName, List<String> roles) {
        super(userId, userName, roles);
    }
    public boolean canEditMedicijnen() {
        return false; // De fysiotherapeut kan geen medicijnen bewerken
    }
}
