package com.example.mockito.employee;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Employee {
    @JsonProperty("firstname")
    private final String name;
    @JsonProperty("lastname")
    private final String surname;
    @JsonProperty("departmentId")
    private final int department;

    private final double salary;

    public Employee(String name, String surname, int department, double salary) {
        this.name = name;
        this.surname = surname;
        this.salary = salary;
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getDepartment() {
        return department;
    }

    public double getSalary() {
        return salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee employee)) return false;
        return getDepartment() == employee.getDepartment() && Double.compare(employee.getSalary(), getSalary()) == 0 && Objects.equals(getName(), employee.getName()) && Objects.equals(getSurname(), employee.getSurname());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getSurname(), getDepartment(), getSalary());
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", department=" + department +
                ", salary=" + salary +
                '}';
    }
}
