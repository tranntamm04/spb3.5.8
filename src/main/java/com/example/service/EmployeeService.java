package com.example.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.dto.AccountEmployee;
import com.example.entity.Employee;

public interface EmployeeService {
    Page<Employee> getAllEmployee(Pageable pageable);

    Employee findById(String id);

    void deleteEmployee(String id);

    void update(AccountEmployee accountEmployees, String id);

    void saveEmployee(Employee employee);

    Page<Employee> searchEmployee(String nameSearch, String typeSearch, Pageable pageable);
}
