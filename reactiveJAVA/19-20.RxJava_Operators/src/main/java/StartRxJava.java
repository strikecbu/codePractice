import io.reactivex.rxjava3.core.Observable;

import java.sql.Array;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class StartRxJava {
    public static void main(String[] args) throws InterruptedException {
        Observable<Employee> just$ = Observable.just(
                new Employee("Andy", 18, 540888, 5.0),
                new Employee("Cathy", 39, 39398, 3.0),
                new Employee("Fat", 41, 29800, 4.5),
                new Employee("Tim", 38, 22000, 3.0),
                new Employee("Joker", 21, 25447, 5.0),
                new Employee("Roger", 18, 26400, 4.5),
                new Employee("King", 45, 458863, 5.0)
        );

        just$
                .filter(employee -> employee.getSalary() > 30000)
                .sorted((e1, e2) -> Long.compare(e2.getSalary(), e1.getSalary()))
                .map(Employee::getName)
//                .toList()
                .subscribe(System.out::println);

        System.out.println("================");
        // flatMap
        List<String> strings = List.of("Andy", "Hack", "IS", "FUN");

        Observable
                .fromIterable(strings)
//                .concatMap((s -> Observable.fromArray(s.split(""))))
                .flatMap(s -> {
                    return Observable.fromArray(s.split(""));
                })
                .subscribe(System.out::println);

        System.out.println("========= amb =========");

        Observable<String> ambSource1 = Observable.interval(1, TimeUnit.SECONDS).map(n -> "source1: " + n).take(10);
        Observable<String> ambSource2 =
                Observable.interval(1, TimeUnit.MICROSECONDS).map(n -> "source2: " + n).take(10);

        Observable.amb(List.of(ambSource1, ambSource2)).subscribe(System.out::println);
        Thread.sleep(300);

        System.out.println("========= group =========");

        just$.groupBy(Employee::getRate)
                .flatMapSingle(e -> e.toMultimap(key -> key.getRate(), emp -> emp.getName()))
                .subscribe(obj -> {
                    System.out.println(obj);
                });


    }

}

class Employee {
    private final String name;
    private final int age;
    private final long salary;

    private final double rate;

    Employee(String name, int age, long salary, double rate) {
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.rate = rate;
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

    public double getRate() {
        return rate;
    }
}
