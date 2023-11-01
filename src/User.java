import java.util.List;
class User {
    public String userName;
    public int userId;
    public List<String> roles;

    public User(int userId, String userName, List<String> roles) {
        this.userId = userId;
        this.userName = userName;
        this.roles = roles;
    }

    // Getters
    public String getUserName() {
        return userName;
    }

    public int getUserId() {
        return userId;
    }

    public List<String> getRoles() {
        return roles;
    }
    public ConsultInfo getConsultInfo(String serviceName) {
        return null;
    }

    // Access
    public boolean canAccessPatientLength() {
        return true;
    }

    public boolean canAccessPatientWeight() {
        return true;
    }
    public boolean canEditMedicine() {
        return false;
    }
}