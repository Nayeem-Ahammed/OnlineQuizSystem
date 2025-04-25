/*
 *  find user name, password and role
 */
public class User {
    String username, password, role; // role = "admin", "teacher", "student"

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // getters & sutter
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getRole() {
        return role;
    }
    
}
