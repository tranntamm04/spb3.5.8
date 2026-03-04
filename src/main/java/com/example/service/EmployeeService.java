package com.example.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.dto.AccountEmployee;
import com.example.entity.*;

import java.util.List;

public interface EmployeeService {

    List<Position> getAllPosition();
    List<Account> getAllAccount();

    Page<Employee> getAllEmployee(Pageable pageable);
    AccountEmployee getEmployeeDetail(String id);

    void createEmployee(AccountEmployee dto);
    void updateEmployee(AccountEmployee dto, String id);
    void deleteEmployee(String id);

    Page<Employee> searchEmployee(String nameSearch, String typeSearch, Pageable pageable);
}
