package pl.coderslab.entity;

public class User {
    private int id;
    private String userName;
    private String email;
    private String password;

    public User() {
        this.userName = "";
        this.email = "";
        this.password = "";
    }

    public User(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public int setId(int id) {
        return this.id = id;
    }

    public String setUserName(String userName) {
        return this.userName = userName;
    }

    public String setEmail(String email) {
        return this.email = email;
    }

    public String setPassword(String password) {
        return this.password = password;
    }

    public int getId() {
        return this.id;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

}
