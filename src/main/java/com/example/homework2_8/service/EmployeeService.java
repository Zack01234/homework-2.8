package com.example.homework2_8.service;

import com.example.homework2_8.exception.EmployeeAlreadyAddedException;
import com.example.homework2_8.exception.EmployeeNotFoundException;
import com.example.homework2_8.exception.EmployeeStorageFullException;
import com.example.homework2_8.model.Employee;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class EmployeeService {
    private static final int LIMIT = 10;
    private final Map<String, Employee> employees = new HashMap<>();

    private String getKey(String name, String surname) {
        return name + "|" + surname;
    }

    public Employee add(String name, String surname, double salary, int department) {
        Employee employee = new Employee(name, surname, salary, department);
        String key = getKey(name, surname);
        if (employees.containsKey(key)) {
            throw new EmployeeAlreadyAddedException();
        }
        if (employees.size() < LIMIT) {
            employees.put(key, employee);
            return employee;
        }
        throw new EmployeeStorageFullException();
    }

    public Employee remove(String name, String surname) {
        String key = getKey(name, surname);
        if (!employees.containsKey(key)) {
            throw new EmployeeNotFoundException();
        }
        return employees.remove(key);
    }

    public Employee find(String name, String surname) {
        String key = getKey(name, surname);
        if (!employees.containsKey(key)) {
            throw new EmployeeNotFoundException();
        }
        return employees.get(key);
    }

    public List<Employee> getAll() {
        return new ArrayList<>(employees.values());
    }

    public void printEmployeesByDepartment() {
        Map<Integer, List<Employee>> map = employees.values().stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, TreeMap::new, Collectors.toList()));
        map.forEach((departmentId, employees) -> System.out.println("Сотрудник из отдела " + departmentId + " : " + employees));
    }

    public void printEmployeesWithSalaryLessThan(double bound) {
        System.out.println("Сотрудник с ЗП меньшей, чем " + bound + " : ");
        employees.values().stream()
                .filter(employee -> employee.getSalary() < bound)
                .forEach(employee -> System.out.printf("Сотрудник: %s %s, ЗП: %.2f%n",
                        employee.getSurname(),
                        employee.getName(),
                        employee.getSalary()
                ));
    }

    public void printEmployeesWithSalaryMoreThan(double bound) {
        System.out.println("Сотрудник с ЗП большей, чем " + bound + " : ");
        employees.values().stream()
                .filter(employee -> employee.getSalary() >= bound)
                .forEach(employee -> System.out.printf("Сотрудник: %s %s, ЗП: %.2f%n",
                        employee.getSurname(),
                        employee.getName(),
                        employee.getSalary()
                ));
    }

    public void indexSalaries(double index) {
        employees.values().stream()
                .forEach(employee -> employee.setSalary(employee.getSalary() + employee.getSalary() * index / 100));
    }

    public void indexSalariesForDepartment(double index, int department) {
        employees.values().stream()
                .filter(employee -> employee.getDepartment() == department)
                .forEach(employee -> employee.setSalary(employee.getSalary() + employee.getSalary() * index / 100));
    }

    public double averageSalaryForDepartment(int department) {
        return employees.values().stream()
                .filter(employee -> employee.getDepartment() == department)
                .mapToDouble(Employee::getSalary)
                .average()
                .orElse(0);
    }

    public Employee findEmployeeWithMinSalaryFromDepartment(int department) {
        return employees.values().stream()
                .filter(employee -> employee.getDepartment() == department)
                .min(Comparator.comparing(Employee::getSalary))
                .orElse(null);
    }

    public Employee findEmployeeWithMinSalary(int department) {
        return employees.values().stream()
                .min(Comparator.comparing(Employee::getSalary))
                .orElse(null);
    }

    public double totalSalariesForDepartment(int department) {
        return employees.values().stream()
                .filter(employee -> employee.getDepartment() == department)
                .mapToDouble(Employee::getSalary)
                .sum();
    }

    public double totalSalaries(int department) {
        return employees.values().stream()
                .mapToDouble(Employee::getSalary)
                .sum();
    }

    public void printFullNameEmployees() {
        employees.values().stream()
                .forEach(employee -> System.out.println(employee.getSurname() + " " + employee.getName()));
    }
}
