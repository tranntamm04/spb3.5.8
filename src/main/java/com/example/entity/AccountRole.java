package com.example.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "account_role")
public class AccountRole {

    @EmbeddedId
    private AccountRoleKey id;

    @ManyToOne
    @MapsId("userName")
    @JoinColumn(name = "user_name")
    private Account account;

    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    private Role role;

    public AccountRole() {
    }

    public AccountRole(AccountRoleKey id, Account account, Role role) {
        this.id = id;
        this.account = account;
        this.role = role;
    }

    public AccountRoleKey getId() {
        return id;
    }

    public void setId(AccountRoleKey id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
