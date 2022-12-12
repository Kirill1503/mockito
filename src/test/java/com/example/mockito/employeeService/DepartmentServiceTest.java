package com.example.mockito.employeeService;

import com.example.mockito.employee.Employee;
import com.example.mockito.exception.EmployeeNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private DepartmentService departmentService;

    @BeforeEach
    public void beforeEach() {
        List<Employee> employees = List.of(
                new Employee("Ivan", "Ivanov", 1, 10000),
                new Employee("Petr", "Petrov", 1, 20000),
                new Employee("Sergey", "Sergeev", 2, 15000),
                new Employee("Gregory", "Leps", 2, 20000),
                new Employee("Lev", "Yashin", 2, 30000),
                new Employee("Alex", "Alexandrov", 1, 30000)
        );
        when(employeeService.getAll()).thenReturn(employees);
    }

    @ParameterizedTest
    @MethodSource("employeeWithMaxSalary")
    void testFindEmployeeWithMaxSalaryFromDepartment(int departmentId, Employee expected) {
        assertThat(departmentService.findEmployeeWithMaxSalaryFromDepartment(departmentId)).isEqualTo(expected);
    }

    @Test
    void testFindEmployeeWithMaxSalaryFromDepartmentNegative() {
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> departmentService.findEmployeeWithMaxSalaryFromDepartment(3));
    }
    @ParameterizedTest
    @MethodSource("employeeWithMinSalary")
    void testFindEmployeeWithMinSalaryFromDepartment(int departmentId, Employee expected) {
        assertThat(departmentService.findEmployeeWithMinSalaryFromDepartment(departmentId)).isEqualTo(expected);
    }

    @Test
    void testFindEmployeeWithMinSalaryFromDepartmentNegative() {
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> departmentService.findEmployeeWithMinSalaryFromDepartment(3));
    }
    @ParameterizedTest
    @MethodSource("employeeFromDepartment")
    void testFindEmployeeFromDepartment(int departmentId, List<Employee> expected) {
        assertThat(departmentService.findEmployeeFromDepartment(departmentId)).containsExactlyElementsOf(expected);
    }

    @Test
    void testFindEmployees() {
        assertThat(departmentService.findEmployees()).containsAllEntriesOf(
                Map.of(
                        1, List.of(new Employee("Ivan", "Ivanov", 1, 10000),
                                new Employee("Petr", "Petrov", 1, 20000),
                                new Employee("Alex", "Alexandrov", 1, 30000)),
                        2, List.of(new Employee("Sergey", "Sergeev", 2, 15000),
                                new Employee("Gregory", "Leps", 2, 20000),
                                new Employee("Lev", "Yashin", 2, 30000))
                )
        );
    }

    public static Stream<Arguments> employeeWithMaxSalary() {
        return Stream.of(
                Arguments.of(1, new Employee("Alex", "Alexandrov", 1, 30000)),
                Arguments.of(2, new Employee("Lev", "Yashin", 2, 30000))
        );
    }
    public static Stream<Arguments> employeeWithMinSalary() {
        return Stream.of(
                Arguments.of(1, new Employee("Ivan", "Ivanov", 1, 10000)),
                Arguments.of(2, new Employee("Sergey", "Sergeev", 2, 15000))
        );
    }
    public static Stream<Arguments> employeeFromDepartment() {
        return Stream.of(
                Arguments.of(1, List.of(new Employee("Ivan", "Ivanov", 1, 10000),
                        new Employee("Petr", "Petrov", 1, 20000),
                        new Employee("Alex", "Alexandrov", 1, 30000))),
                Arguments.of(2, List.of(new Employee("Sergey", "Sergeev", 2, 15000),
                        new Employee("Gregory", "Leps", 2, 20000),
                        new Employee("Lev", "Yashin", 2, 30000))),
                Arguments.of(3, Collections.EMPTY_LIST)
        );
    }
}