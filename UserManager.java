
import java.io.IOException;
import java.util.List;

/*
 * 
 */
public class UserManager {
    private FileDatabase database;
    private User currectUser;

    public UserManager() {
        this.database = new FileDatabase();
    }

    // Register a new user
    public boolean register(User user) {
        try {
            database.saveUser(user);
        } catch (IOException e) {
            System.out.println("Error saving user: " + e.getMessage());
            return false;
        }
        return true;
    }

    // Login a user
    public User login(String username, String password) {
        try {
            List <User> users = database.loadUsers();
            for(User user : users) {
                if(user.getUsername().equals(username) && user.getPassword().equals(password)) {
                    currectUser = user;
                    return user;
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
        return null;
    }

    // Get current user
    public User getCurrentUser() {
        return currectUser;
    }

    
}
