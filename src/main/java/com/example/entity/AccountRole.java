package com.example.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
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

    public AccountRole(AccountRoleKey id, Account account, Role role) {
        this.id = id;
        this.account = account;
        this.role = role;
    }


}
