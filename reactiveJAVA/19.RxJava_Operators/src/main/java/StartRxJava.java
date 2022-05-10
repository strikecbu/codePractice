import io.reactivex.rxjava3.core.Observable;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class StartRxJava {
    public static void main(String[] args) throws InterruptedException {
        Observable<Employee> just$ = Observable.just(
                new Employee("Andy", 18, 540888),
                new Employee("Cathy", 39, 39398),
                new Employee("Fat", 41, 29800),
                new Employee("Tim", 38, 22000),
                new Employee("Joker", 21, 25447),
                new Employee("Roger", 18, 26400),
                new Employee("King", 45, 458863)
        );

        just$
                .filter(employee -> employee.getSalary() > 30000)
                .sorted((e1, e2) -> Long.compare(e2.getSalary(), e1.getSalary()))
                .map(Employee::getName)
//                .toList()
                .subscribe(System.out::println);

    }

}

class Employee {
    private final String name;
    private final int age;
    private final long salary;

    Employee(String name, int age, long salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public long getSalary() {
        return salary;
    }
}
