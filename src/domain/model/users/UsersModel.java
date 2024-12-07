package domain.model.users;

import java.util.Objects;

import constant.role.Role;

public class UsersModel {

    private final String id;
    private final String username;
    private final String password;
    private final Role role;

    // Constructor
    public UsersModel(String id, String username, String password, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getters
    public String getId() {
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
               "id='" + id + '\'' +
               ", username='" + username + '\'' +
               ", password='" + password + '\'' +
               ", role=" + role +
               '}';
    }

    @Override
    public final int hashCode() {
        return Objects.hash(id, username, password, role);
    }

    @Override
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;  // Same object reference
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;  // Different class or null object
        }
        UsersModel that = (UsersModel) obj;
        return Objects.equals(id, that.id) &&
               Objects.equals(username, that.username) &&
               Objects.equals(password, that.password) &&
               role == that.role;  // Assuming Role is an enum or a comparable class
    }
}
