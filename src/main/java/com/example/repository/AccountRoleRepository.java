package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.entity.Account;
import com.example.entity.AccountRole;
import com.example.entity.AccountRoleKey;

import java.util.List;

@Repository
public interface AccountRoleRepository extends JpaRepository<AccountRole, AccountRoleKey> {
    List<AccountRole> findAllByAccount(Account account);
}
