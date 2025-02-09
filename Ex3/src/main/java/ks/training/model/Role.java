package ks.training.model;

import java.util.List;

public class Role {
    private int id;
    private int roleName;
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Role(int id, int roleName, List<User> users) {
        this.id = id;
        this.roleName = roleName;
        this.users = users;
    }

    public Role() {
    }

    public Role(int id, int roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoleName() {
        return roleName;
    }

    public void setRoleName(int roleName) {
        this.roleName = roleName;
    }
}
