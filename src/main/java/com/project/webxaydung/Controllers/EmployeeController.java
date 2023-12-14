package com.project.webxaydung.Controllers;
import com.project.webxaydung.Dto.EmployeeDTO;
import com.project.webxaydung.Models.Employee;
import com.project.webxaydung.Services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/employee")
@RequiredArgsConstructor
@Validated
public class EmployeeController {
    private  final EmployeeService employeeService;

    @PostMapping("")
    public ResponseEntity<?> insertEmployee(@RequestBody EmployeeDTO employeeDTO, BindingResult result){
        if(result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        try{
            employeeService.createEmployee(employeeDTO);
            return ResponseEntity.ok("Insert Employee Successfully");
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("")// http://localhost:8080/api/v1/employee
    public ResponseEntity<List<Employee>> getAllEmployee(){
        List<Employee> employees=employeeService.getAllEmployees();
        return  ResponseEntity.ok(employees);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(
            @PathVariable("id") int id
    ) {
        employeeService.getEmployeeById(id);
        return ResponseEntity.ok("Employee with ID: " + employeeService.getEmployeeById(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(
            @PathVariable int id,
            @RequestBody EmployeeDTO employeeDTO) {
        try {
            Employee updatedEmployee = employeeService.updateEmployee(id, employeeDTO);
            return ResponseEntity.ok(updatedEmployee);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int id) {
        try {
            employeeService.deleteEmployee(id);
            return ResponseEntity.ok(String.format("New with id = %d deleted successfully", id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
