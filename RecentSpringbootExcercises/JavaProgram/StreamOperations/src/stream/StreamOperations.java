package stream;

import model.Employee;

import javax.xml.transform.stream.StreamSource;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class StreamOperations {
    public void getStreamOperations(List<Employee> employeeList) {
        //How many male and female employees are there in the organization?
        Map<String, Long> collect = employeeList.stream().collect(Collectors.groupingBy(emp -> emp.getGender(), Collectors.counting()));
        extracted(collect);
        //Print the name of all departments in the organization?
        List<String> collect1 = employeeList.stream().map(Employee::getDepartment).distinct().collect(Collectors.toList());
        collect1.forEach(System.out::println);
        //What is the average age of male and female employees?
        Map<String, Double> collect2 = employeeList.stream().collect(Collectors.groupingBy(Employee::getGender, Collectors.averagingDouble(Employee::getAge)));
        collect2.forEach((key, value) -> System.out.println(key + "=" + value));
        //Get the details of highest paid employee in the organization?
        Optional<Employee> max = employeeList.stream().max(Comparator.comparing(Employee::getSalary));
        System.out.println(max.get());
        //Get the names of all employees who have joined after 2015?
        employeeList.stream().filter(emp -> emp.getYearOfJoining() >= 2015).map(Employee::getName).forEach(System.out::println);
        //Count the number of employees in each department?
        Map<String, Long> collect3 = employeeList.stream().collect(Collectors.groupingBy(emp -> emp.getDepartment(), Collectors.counting()));
        extracted(collect3);

        //What is the average salary of each department?
        Map<String, Double> collect4 = employeeList.stream().collect(Collectors.groupingBy(Employee::getDepartment, Collectors.averagingDouble(Employee::getSalary)));
        collect4.entrySet().stream().forEach(entry-> {
            String var1 = entry.getKey();
            Double var2 = entry.getValue();
            System.out.println(var1 + ":" + var2 );
        });

        //Get the details of youngest male employee in the product development department?
        Optional<Employee> employee = employeeList.stream().filter(emp -> {
            return emp.getGender().equalsIgnoreCase("male") && emp.getDepartment().equalsIgnoreCase("Product Development");
        }).min(Comparator.comparing(Employee::getAge));
        if(employee.isPresent())
        System.out.println(employee.get());

    }

    private static void extracted(Map<String, Long> collect) {
        collect.forEach((key, value) -> System.out.println(key + "=" + value));
    }
}
