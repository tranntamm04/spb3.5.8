package com.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class AccountRoleKey implements Serializable {

    @Column(name = "user_name")
    private String userName;

    @Column(name = "role_id")
    private int roleId;

    public AccountRoleKey() {
    }

    public AccountRoleKey(String userName, int roleId) {
        this.userName = userName;
        this.roleId = roleId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        AccountRoleKey that = (AccountRoleKey) o;
        return roleId == that.roleId && Objects.equals(userName, that.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, roleId);
    }
}
