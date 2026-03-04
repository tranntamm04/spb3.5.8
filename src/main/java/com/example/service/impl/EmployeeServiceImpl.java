package com.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.dto.AccountEmployee;
import com.example.entity.*;
import com.example.repository.*;
import com.example.service.EmployeeService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final AccountRepository accountRepository;
    private final PositionRepository positionRepository;
    private final RoleRepository roleRepository;
    private final AccountRoleRepository accountRoleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<Position> getAllPosition() {
        return positionRepository.findAll();
    }

    @Override
    public List<Account> getAllAccount() {
        return accountRepository.findAll();
    }

    @Override
    public Page<Employee> getAllEmployee(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    @Override
    public AccountEmployee getEmployeeDetail(String id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));
        return new AccountEmployee(
                employee.getIdEmployee(),
                employee.getFullName(),
                employee.getDateOfBirth(),
                employee.getEmail(),
                employee.getAddress(),
                employee.getPhone(),
                employee.getRegisterDate(),
                employee.getPosition().getPositionId(),
                employee.getAccount().getUserName(),
                employee.getAccount().getPassword()
        );
    }

    @Override
    public void createEmployee(AccountEmployee dto) {
        if (accountRepository.existsByUserName(dto.getUserName())) {
            throw new RuntimeException("Username already exists");
        }
        Account account = new Account();
        account.setUserName(dto.getUserName());
        account.setPassword(passwordEncoder.encode(dto.getPassword()));
        accountRepository.save(account);
        accountRepository.save(account);
        Role role = roleRepository.findById(1).orElseThrow(() -> new RuntimeException("Role not found"));

        AccountRoleKey key = new AccountRoleKey(account.getUserName(), 1);
        accountRoleRepository.save(new AccountRole(key, account, role));

        Position position = positionRepository.findById(dto.getPositionId()).orElseThrow(() -> new RuntimeException("Position not found"));

        Employee employee = new Employee(
                dto.getIdEmployee(),
                dto.getFullName(),
                dto.getDateOfBirth(),
                dto.getEmail(),
                dto.getAddress(),
                dto.getPhone(),
                dto.getRegisterDate(),
                account,
                position
        );
        employeeRepository.save(employee);
    }

    @Override
    public void updateEmployee(AccountEmployee dto, String id) {

        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));

        Account account = accountRepository.findByUserName(dto.getUserName());
        Position position = positionRepository.findById(dto.getPositionId()).orElseThrow(() -> new RuntimeException("Position not found"));

        employee.setFullName(dto.getFullName());
        employee.setDateOfBirth(dto.getDateOfBirth());
        employee.setEmail(dto.getEmail());
        employee.setAddress(dto.getAddress());
        employee.setPhone(dto.getPhone());
        employee.setPosition(position);
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            account.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(String id) {
        if (!employeeRepository.existsById(id)) {
            throw new RuntimeException("Employee not found");
        }
        employeeRepository.deleteById(id);
    }

    @Override
    public Page<Employee> searchEmployee(String nameSearch, String typeSearch, Pageable pageable) {
        return employeeRepository.searchEmployee(nameSearch, typeSearch, pageable);
    }
}
