package com.example.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.example.dto.AccountEmployee;
import com.example.entity.*;
import com.example.service.EmployeeService;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/positions")
    public ResponseEntity<List<Position>> getAllPosition() {
        return ResponseEntity.ok(employeeService.getAllPosition());
    }

    @GetMapping("/accounts")
    public ResponseEntity<List<Account>> getAllAccount() {
        return ResponseEntity.ok(employeeService.getAllAccount());
    }

    @GetMapping
    public ResponseEntity<Page<Employee>> getAllEmployee(Pageable pageable) {
        return ResponseEntity.ok(employeeService.getAllEmployee(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountEmployee> getEmployeeDetail(@PathVariable String id) {
        return ResponseEntity.ok(employeeService.getEmployeeDetail(id));
    }

    @PostMapping
    public ResponseEntity<?> createEmployee(@Valid @RequestBody AccountEmployee dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getFieldErrors());
        }
        employeeService.createEmployee(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable String id, @Valid @RequestBody AccountEmployee dto,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getFieldErrors());
        }
        employeeService.updateEmployee(dto, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable String id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Employee>> searchEmployee(
            @RequestParam String nameSearch,
            @RequestParam String typeSearch,
            Pageable pageable) {
        return ResponseEntity.ok(employeeService.searchEmployee(nameSearch, typeSearch, pageable));
    }
}
