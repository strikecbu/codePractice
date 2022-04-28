import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class StrategyPattern {
    public static void main(String[] args) {
        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(new Employee("001", "Andy", 18));
        employees.add(new Employee("002", "Cathy", 35));
        employees.add(new Employee("003", "Roger", 32));
        employees.add(new Employee("004", "William", 0));
        employees.add(new Employee("005", "Androw", 28));

        List<Employee> filteredByName = filter(employees, employee -> employee.getName().contains("An"));
        System.out.println("Name has 'An' : " + filteredByName);
        List<Employee> youngEmployees = filter(employees, employee -> employee.getAge() < 30);
        System.out.println("Age under 30 : " + youngEmployees);

    }

    private static <T> List<T> filter(List<T> list, Predicate<T> fn) {
        List<T> filteredList = new ArrayList<>();
        for (T t : list) {
            if (fn.test(t)) {
                filteredList.add(t);
            }
        }
        return filteredList;
    }
}


@FunctionalInterface
interface EmployeeFilter {
    List<Employee> filter(List<Employee> list, Predicate<Employee> fn);
}

class Employee {
    private String id;
    private String name;
    private int age;

    public Employee() {
    }

    public Employee(String id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
