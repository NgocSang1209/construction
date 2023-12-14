package com.project.webxaydung.Services;
import com.project.webxaydung.Dto.EmployeeDTO;
import com.project.webxaydung.Models.Employee;
import com.project.webxaydung.Repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private  final EmployeeRepository employeeRepository;
    @Transactional
    public Employee createEmployee(EmployeeDTO employeeDTO) {
        Employee newEmployee = Employee
                .builder()
                .name(employeeDTO.getName())
                .position(employeeDTO.getPosition())
                .name(employeeDTO.getName())
                .phone(employeeDTO.getPhone())
                .build();
        return employeeRepository.save(newEmployee);
    }

    public Employee getEmployeeById(int id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }


    @Transactional
    public Employee updateEmployee(int employeeId,
                                   EmployeeDTO employeeDTO) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new RuntimeException("Không tìm thấy nhân viên với ID: " + employeeId);
        }
        // Lấy công việc từ cơ sở dữ liệu
       Employee existingEmployee = employeeRepository.getOne(employeeId);
        if(employeeDTO.getName()!= null) {
            existingEmployee.setName(employeeDTO.getName());
        }
        if(employeeDTO.getPosition() != null) {
            existingEmployee.setPosition(employeeDTO.getPosition());
        }
        if(employeeDTO.getPhone()!= null) {
            existingEmployee.setPhone(employeeDTO.getPhone());
        }
        if(employeeDTO.getEmail() != null) {
            existingEmployee.setEmail(employeeDTO.getEmail());
        }
        employeeRepository.save(existingEmployee);
        return existingEmployee;
    }

    @Transactional
    public void deleteEmployee(int id) {
        //xóa xong
        employeeRepository.deleteById(id);
    }
}
