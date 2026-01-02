package com.example.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    @Query(value = "select e from Employee e inner join Position p on p.positionId = e.position.positionId where (e.idEmployee like %:nameSearch% or e.fullName like %:nameSearch%) and (p.positionName like %:typeSearch%)")
    Page<Employee> searchEmployee(@Param("nameSearch") String nameSearch,
            @Param("typeSearch") String typeSearch, Pageable pageable);
}
