package com.example.homework2_8.controller;

import com.example.homework2_8.model.Employee;
import com.example.homework2_8.service.DepartmentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {
    private final com.example.homework2_8.service.DepartmentService.DepartmentService departmentService;

    public DepartmentController(DepartmentService.DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/max-salsary")
    public Employee findEmployeeWithMaxSalaryFromDepartment(@RequestParam("departmentId") int department) {
        return departmentService.findEmployeeWithMaxSalaryFromDepartment(department);
    }

    @GetMapping("/min-salsary")
    public Employee findEmployeeWithMinSalaryFromDepartment(@RequestParam("departmentId") int department) {
        return departmentService.findEmployeeWithMinSalaryFromDepartment(department);
    }

    @GetMapping("/all")
    public Collection<Employee> findEmployeesFromDepartment(@RequestParam("departmentId") int department) {
        return departmentService.findEmployeesFromDepartment(department);
    }

    @GetMapping("/all")
    public Map<Integer, List<Employee>> findEmployees() {
        return departmentService.findEmployees();
    }
}
