package com.example.mockito.employeeService;

import com.example.mockito.employee.Employee;

import com.example.mockito.exception.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import  static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;


class EmployeeServiceTest {

    private final EmployeeService employeeService = new EmployeeService(new ValidatorService());

    @ParameterizedTest
    @MethodSource("params")
    public void addEmployeeNegativeTest(String name,
                     String surname,
                     int department,
                     double salary) {
        Employee expected = new Employee(name, surname, department, salary);
        assertThat(employeeService.addEmployee(name, surname, department, salary)).isEqualTo(expected);

        assertThatExceptionOfType(EmployeeAlreadyAddedException.class)
                .isThrownBy(() -> employeeService.addEmployee(name, surname, department, salary));
    }

    @Test
    public void addEmployeeNegativeTest2() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Ивана", "Иванова", 1, 45000));
        employees.add(new Employee("Иванв", "Ивановв", 1, 45000));
        employees.add(new Employee("Иваны", "Ивановы", 1, 45000));
        employees.add(new Employee("Иванф", "Ивановф", 1, 45000));
        employees.add(new Employee("Иванй", "Ивановй", 2, 45000));
        employees.add(new Employee("Иванц", "Ивановц", 2, 45000));
        employees.add(new Employee("Ивану", "Иванову", 2, 45000));
        employees.add(new Employee("Иванк", "Ивановк", 2, 45000));
        employees.add(new Employee("Иване", "Иванове", 2, 45000));
        employees.add(new Employee("Иванн", "Ивановн", 2, 45000));
        employees.forEach(employee ->
                assertThat(employeeService.addEmployee(employee.getName(), employee.getSurname(),
                        employee.getDepartment(), employee.getSalary())).isEqualTo(employee));

        assertThatExceptionOfType(EmployeeStorageIsFullException.class)
                .isThrownBy(() -> employeeService.addEmployee("Иван", "Иванов", 1, 45000));
    }

    @Test
    public void addNegativeTest3 () {
        assertThatExceptionOfType(IncorrectNameException.class)
                .isThrownBy(() -> employeeService.addEmployee("!Иван", "Иванов", 2, 50000));

        assertThatExceptionOfType(IncorrectSurnameException.class)
                .isThrownBy(() -> employeeService.addEmployee("Иван", "!!Иванов", 2, 50000));

        assertThatExceptionOfType(IncorrectNameException.class)
                .isThrownBy(() -> employeeService.addEmployee(null, "Иванов", 2, 50000));

    }

    @ParameterizedTest
    @MethodSource("params")
    void removeEmployeeNegativeTest(String name,
                                    String surname,
                                    int department,
                                    double salary) {
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> employeeService.removeEmployee("Tom", "Thomson"));

        Employee expected = new Employee(name, surname, department, salary);
        assertThat(employeeService.addEmployee(name, surname, department, salary)).isEqualTo(expected);
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> employeeService.removeEmployee("Tom", "Thomas"));
    }

    @ParameterizedTest
    @MethodSource("params")
    void removeEmployeePositiveTest(String name,
                                    String surname,
                                    int department,
                                    double salary) {
        Employee expected = new Employee(name, surname, department, salary);
        assertThat(employeeService.addEmployee(name, surname, department, salary)).isEqualTo(expected);

        assertThat(employeeService.removeEmployee(name, surname)).isEqualTo(expected);
        assertThat(employeeService.getAll()).isEmpty();
    }
    @ParameterizedTest
    @MethodSource("params")
    void findEmployeeNegativeTest(String name,
                                  String surname,
                                  int department,
                                  double salary) {
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> employeeService.findEmployee("Ivan", "Ivanov"));

        Employee expected = new Employee(name, surname, department, salary);
        assertThat(employeeService.addEmployee(name, surname, department, salary)).isEqualTo(expected);
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> employeeService.findEmployee("Ivan", "Ivanov"));
    }

    @ParameterizedTest
    @MethodSource("params")
    void findEmployeePositiveTest(String name,
                                  String surname,
                                  int department,
                                  double salary) {
        Employee expected = new Employee(name, surname, department, salary);
        assertThat(employeeService.addEmployee(name, surname, department, salary)).isEqualTo(expected);

        assertThat(employeeService.findEmployee(name, surname)).isEqualTo(expected);
        assertThat(employeeService.getAll()).hasSize(1);
    }

    public static Stream<Arguments> params (){
        return Stream.of(
                Arguments.of("Иванов", "Иван", 1, 100),
                Arguments.of("Александров", "Александр", 2, 101),
                Arguments.of("Игнатов", "Игнат", 3, 102)
        );
    }
}