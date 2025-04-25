/*
 *  find user name, password and role
 */
public class User {
    private  String username, password, role; // role = "admin", "teacher", "student"

    // setters
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // getters
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
