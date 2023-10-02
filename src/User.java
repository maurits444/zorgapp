class User {
    private String userName;
    private int userId;

    public User(int userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

   public String getUserName() {
        return userName;
    }

    public int getUserId() {
        return userId;
    }
}
