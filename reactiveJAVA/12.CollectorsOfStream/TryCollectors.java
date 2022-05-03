import java.io.IOException;
import java.lang.invoke.VarHandle;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class TryCollectors {
    public static void main(String[] args) {
        Path path = Paths.get("./EmployeeData.txt");
        try (Stream<String> lines = Files.lines(path)) {
            Stream<String> dataStream = lines.flatMap(line -> Arrays.stream(line.split(",")));
            Spliterator<String> baseSpliterator = dataStream.spliterator();
            EmployeeSpliterator employeeSpliterator = new EmployeeSpliterator(baseSpliterator);
            Stream<Employee> employeeStream = StreamSupport.stream(employeeSpliterator, false);
            List<Employee> employeeList = employeeStream.toList();
            employeeList.forEach(System.out::println);
            System.out.println("============ End of List ============");

            Set<String> designationSet =
                    employeeList.stream().map(Employee::getDesignation).collect(Collectors.toSet());
            System.out.println(designationSet);
            System.out.println("============ End of Set ============");

            TreeSet<Employee> sortedEmployees = employeeList.stream().collect(Collectors.toCollection(TreeSet::new));
            System.out.println(sortedEmployees);
            System.out.println("============ End of TreeSet ============");

            Map<Integer, String> getNameById = employeeList.stream().collect(Collectors.toMap(Employee::getId,
                    Employee::getName));
            System.out.println(getNameById);
            System.out.println("============ End of Map ============");

            Map<Boolean, List<Employee>> partitionedEmployees =
                    employeeList.stream().collect(Collectors.partitioningBy(e -> e.getGender() == 'M'));
            List<Employee> maleEmployees = partitionedEmployees.get(true);
            List<Employee> femaleEmployees = partitionedEmployees.get(false);
            System.out.println(maleEmployees);
            System.out.println(femaleEmployees);
            System.out.println("============ End of Partition ============");

            Map<String, List<Employee>> designationMap =
                    employeeList.stream().collect(Collectors.groupingBy(Employee::getDesignation));
            List<Employee> managerEmployees = designationMap.get("Manager");
            System.out.println(managerEmployees);
            System.out.println("============ End of GroupBy ============");

            String employeeNames = employeeList.stream().map(Employee::getName).collect(Collectors.joining(", "));
            System.out.println(employeeNames);
            System.out.println("============ End of Join ============");

            Map<String, Long> designationCount =
                    employeeList.stream().collect(Collectors.groupingBy(Employee::getDesignation,
                            Collectors.counting()));
            System.out.println(designationCount);

            Map<String, Double> designationSalary =
                    employeeList.stream().collect(Collectors.groupingBy(Employee::getDesignation,
                            Collectors.summingDouble(Employee::getSalary)
                    ));
            System.out.println(designationSalary);

            Map<String, Optional<Employee>> designationMaxSalaryEmployee =
                    employeeList.stream().collect(Collectors.groupingBy(Employee::getDesignation,
                            Collectors.maxBy(Comparator.comparing(Employee::getSalary))));
            System.out.println(designationMaxSalaryEmployee);

            Map<String, Optional<Double>> designationMaxSalary =
                    employeeList.stream().collect(Collectors.groupingBy(Employee::getDesignation,
                            Collectors.mapping(Employee::getSalary,
                                    Collectors.maxBy(Comparator.comparing(Function.identity())))));
            System.out.println(designationMaxSalary);


            Map<String, List<Double>> salaryByDesignation =
                    employeeList.stream().collect(Collectors.groupingBy(Employee::getDesignation,
                            Collectors.mapping(Employee::getSalary, Collectors.toList())));

            System.out.println(salaryByDesignation);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
