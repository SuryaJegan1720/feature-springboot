package stream;

import model.Employee;

import javax.xml.transform.stream.StreamSource;
import java.util.*;
import java.util.function.Function;
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

    public void getPrograms() {
      Integer[] arr  = new Integer[]{71, 18, 42, 21, 67, 32, 95, 14, 56, 87};
      List<Integer> listOfIntegers =Arrays.asList(arr);

        //Given a list of integers, separate odd and even numbers True: 18,42 false: 71,21
        Map<Boolean, List<Integer>> collect = listOfIntegers.stream().sorted().collect(Collectors.partitioningBy(e -> e % 2 == 0));
        collect.forEach((key, value)-> {
            if(key)
                System.out.println(value);
            else
                System.out.println(value);
        });
        //How do you remove duplicate elements from a list using Java 8 streams?
        List<String> listOfStrings = Arrays.asList("Java", "Python", "C#", "Java", "Kotlin", "Python");
        List<String> collect1 = listOfStrings.stream().distinct().toList();

        Set<String> setOfStrings = new HashSet<>(listOfStrings);
        for (String s : setOfStrings) {
            System.out.println(s);
        }



        Map<String, Long> collect2 = listOfStrings.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
        collect2.forEach((key,value) -> {
            if(value > 1){
            System.out.println(key);
            }
        });

        //How do you find frequency of each character in a string using Java 8 streams
        String inputString = "Java Concept Of The Day";
        Map<Character, Long> frequencies = inputString.chars()
                .mapToObj(c -> (char) c)
                .filter(Character::isLetter)
                .map(Character::toLowerCase)
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ));

        frequencies.forEach((letter, count) ->
                System.out.println(letter + ": " + count)
        );
        //How do you sort the given list of decimals in reverse order

        List<Double> decimalList = Arrays.asList(12.45, 23.58, 17.13, 42.89, 33.78, 71.85, 56.98, 21.12);

        List<Double> list = decimalList.stream().sorted().toList();

       // list.forEach(e-> System.out.println(e + " "));

        decimalList.stream().sorted(Comparator.reverseOrder()).forEach(e-> System.out.println(e + " "));;
       // Given a list of strings, join the strings with ‘[‘ as prefix, ‘]’ as suffix and ‘,’ as delimiter?
        List<String> listOfStrings2 = Arrays.asList("Facebook", "Twitter", "YouTube", "WhatsApp", "LinkedIn");
        String collect3 = listOfStrings2.stream().collect(Collectors.joining(",", "[", "]"));
        System.out.println(collect3);
        // From the given list of integers, print the numbers which are multiples of 5?
        List<Integer> listOfIntegers2 = Arrays.asList(45, 12, 56, 15, 24, 75, 31, 89,90,100);

        listOfIntegers2.stream().filter(e-> e%5 == 0).forEach(System.out::println);

       // Given a list of integers, find maximum and minimum of those numbers?
        List<Integer> listOfIntegers3 = Arrays.asList(45, 12, 56, 15, 24, 75, 31, 89);
        List<Integer> list1 = listOfIntegers3.stream().sorted().toList();
        Integer i = list1.get(0);
        Integer i1 = list1.get(list1.size() - 1);
        System.out.println(i + " " + i1);

        Optional<Integer> max = listOfIntegers3.stream().min(Comparator.comparing(Integer::intValue));
        System.out.println(max.get());






    }
}

