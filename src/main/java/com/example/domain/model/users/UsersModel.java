package domain.model.users;

import constant.role.Role;

public class UsersModel {

    private final int id;
    private final String username;
    private final String password;
    private final Role role;

    // Constructor
    public UsersModel(int id, String username, String password, Role role ) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "UsersModel{" +
               "id=" + id +
               ", username='" + username + '\'' +
               ", role=" + role +
               '}';
    }
}
