import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;

public class OwnCollector {
    public static void main(String[] args) {
        List<Integer> numbers = List.of(1, 41, 87, 2, 25, 74, 85, 3, 5, 8, 19, 22, 100);

        Collector<Integer, ArrayList<Integer>, ArrayList<Integer>> toList = Collector.of(
                ArrayList::new,
                ArrayList::add,
                (list1, list2) -> {
                    list1.addAll(list2);
                    return list1;
                },
                Collector.Characteristics.IDENTITY_FINISH
        );

        ArrayList<Integer> evens = numbers.stream().filter(e -> e % 2 == 0).collect(toList);
        System.out.println(evens);

        Collector<Integer, ArrayList<Integer>, ArrayList<Integer>> toSortedList = Collector.of(
                ArrayList::new,
                ArrayList::add,
                (list1, list2) -> {
                    list1.addAll(list2);
                    return list1;
                },
                (list) -> {
                    Collections.sort(list);
                    return list;
                },
                Collector.Characteristics.UNORDERED
        );

        ArrayList<Integer> sortedNumbers = numbers.stream().collect(toSortedList);
        System.out.println(sortedNumbers);

        List<Employee> employees = List.of(new Employee("Andy", 35), new Employee("Mark", 25),
                new Employee("Gary", 46), new Employee("William", 0));

        Collector<Employee, Department, Department> collector = Collector.of(
                Department::new,
                (department, o) -> {
                    if (o.getAge() > 30) {
                        department.addOld(o);
                    } else {
                        department.addYoung(o);
                    }
                },
                (department, department2) -> {
                    department.getYounger().addAll(department2.getYounger());
                    department.getOlder().addAll(department2.getOlder());
                    return department;
                },
                Collector.Characteristics.IDENTITY_FINISH
        );

        Department department = employees.stream().collect(collector);
        System.out.println(department);

    }
}

class Employee {
    private String name;
    private int age;

    public Employee(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

class Department {
    private final List<Employee> orderEmployees = new ArrayList<>();
    private final List<Employee> youngerEmployees = new ArrayList<>();

    public void addOld(Employee employee) {
        orderEmployees.add(employee);
    }

    public void addYoung(Employee employee) {
        youngerEmployees.add(employee);
    }

    public List<Employee> getOlder() {
        return this.orderEmployees;
    }

    public List<Employee> getYounger() {
        return this.youngerEmployees;
    }

    @Override
    public String toString() {
        return "Department{" +
                "orderEmployees=" + orderEmployees +
                ", youngerEmployees=" + youngerEmployees +
                '}';
    }
}
