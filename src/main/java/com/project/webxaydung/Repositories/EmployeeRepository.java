package com.project.webxaydung.Repositories;

import com.project.webxaydung.Models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

}
