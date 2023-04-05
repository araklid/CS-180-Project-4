package src.PJ4;

/**
 * Homework X
 *
 * @author Weston Walker, L08
 * @version Jan 1, 2000
 **/
public class User {
    private String username;
    private String password;
    public User(String username, String password) {
        this.password = password;
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
