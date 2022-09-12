package com.fadgiras.exos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fadgiras.exos.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{

}
