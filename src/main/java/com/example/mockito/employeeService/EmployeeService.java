package com.example.mockito.employeeService;

import com.example.mockito.employee.Employee;
import com.example.mockito.exception.EmployeeAlreadyAddedException;
import com.example.mockito.exception.EmployeeNotFoundException;
import com.example.mockito.exception.EmployeeStorageIsFullException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    private static final int LIMIT = 10;

    private final List<Employee> employees = new ArrayList<>();

    private final ValidatorService validatorService;

    public EmployeeService(ValidatorService validatorService) {
        this.validatorService = validatorService;
    }

    public Employee addEmployee(String name,
                                String surname,
                                int department,
                                double salary) {
        Employee employee = new Employee(
                validatorService.validateName(name),
                validatorService.validateSurname(surname),
                department,
                salary
        );
        if (employees.contains(employee)) {
            throw new EmployeeAlreadyAddedException();
        }
        if (employees.size() < LIMIT) {
            employees.add(employee);
            return employee;
        } else {
            throw new EmployeeStorageIsFullException();
        }
    }

    public Employee findEmployee(String name,
                                 String surname) {
        return employees.stream()
                .filter(emp -> emp.getName().equals(name) && emp.getSurname().equals(surname))
                .findFirst()
                .orElseThrow(EmployeeNotFoundException::new);
    }


    public Employee removeEmployee(String name,
                                   String surname) {
        Employee employee = employees.stream()
                .filter(emp -> emp.getName().equals(name) && emp.getSurname().equals(surname))
                .findFirst()
                .orElseThrow(EmployeeNotFoundException::new);
        employees.remove(employee);
        return employee;
    }

    public List<Employee> getAll() {
        return new ArrayList<>(employees);
    }

}
