import java.util.List;
class User {
    public String userName;
    public int userId;
    public List<String> roles; // Dit is de nieuwe lijst van rollen/namen voor elke User

    public User(int userId, String userName, List<String> roles) {
        this.userId = userId;
        this.userName = userName;
        this.roles = roles;
    }
    public String getUserName() {
        return userName;
    }

    public int getUserId() {
        return userId;
    }

    public List<String> getRoles() {
        return roles;
    }

    public boolean canAccessPatientLength() {
        return true;
    }

    public boolean canAccessPatientWeight() {
        return true;
    }
}