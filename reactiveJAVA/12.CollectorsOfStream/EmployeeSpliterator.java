import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Spliterator;
import java.util.function.Consumer;

public class EmployeeSpliterator implements Spliterator<Employee> {

    private int id;
    private String name;
    private char gender;
    private Date dob;
    private String city;
    private String designation;
    private Date joiningDate;
    private double salary;
    private final Spliterator<String> baseSpliterator;

    public EmployeeSpliterator(Spliterator<String> baseSpliterator) {
        this.baseSpliterator = baseSpliterator;
    }

    @Override
    public boolean tryAdvance(Consumer<? super Employee> action) {
        if (this.baseSpliterator.tryAdvance(id -> this.id = Integer.parseInt(id)) &&
                this.baseSpliterator.tryAdvance(name -> this.name = name) &&
                this.baseSpliterator.tryAdvance(gender -> this.gender = gender.charAt(0)) &&
                this.baseSpliterator.tryAdvance(dob -> this.dob = Date.valueOf(dob)) &&
                this.baseSpliterator.tryAdvance(city -> this.city = city) &&
                this.baseSpliterator.tryAdvance(designation -> this.designation = designation) &&
                this.baseSpliterator.tryAdvance(joiningDate -> this.joiningDate = Date.valueOf(joiningDate)) &&
                this.baseSpliterator.tryAdvance(salary -> this.salary = Double.parseDouble(salary))
        ) {
            action.accept(new Employee(this.id, this.name, this.gender, this.dob, this.city, this.designation,
                    this.joiningDate, this.salary));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Spliterator<Employee> trySplit() {
        return null;
    }

    @Override
    public long estimateSize() {
        return this.baseSpliterator.estimateSize() / 8;
    }

    @Override
    public int characteristics() {
        return this.baseSpliterator.characteristics();
    }
}
