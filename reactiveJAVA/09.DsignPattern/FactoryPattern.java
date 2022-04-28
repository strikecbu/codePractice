import java.util.function.Predicate;
import java.util.function.Supplier;

public class FactoryPattern {
    public static void main(String[] args) {
        CarFactory factory = new CarFactory(125);
        Car car1 = factory.product();
        System.out.println("Car you got: " + car1);
        CarFactory factory2 = new CarFactory(99);
        Car car2 = factory2.product();
        System.out.println("Car you got: " + car2);
        CarFactory carFactory = new CarFactoryBuilder(integer -> integer > 130).build(125);
        Car car3 = carFactory.product();
        System.out.println("Car you got: " + car3);
    }
}

class Car {
    int horsePower;
    String brand;

    @Override
    public String toString() {
        return "Car{" +
                "horsePower=" + horsePower +
                ", brand='" + brand + '\'' +
                '}';
    }
}

class ToyotaCar extends Car {
    public ToyotaCar() {
        super();
        horsePower = 120;
        brand = "TOYOTA";
    }
}
class BmwCar extends Car {
    public BmwCar() {
        super();
        horsePower = 200;
        brand = "BMW";
    }
}

class CarFactory {
    private final Supplier<Car> supplier;

    public CarFactory(Supplier<Car> supplier) {
        this.supplier = supplier;
    }
    public CarFactory(Integer requireHorsePower) {
        if (requireHorsePower > 120) {
            this.supplier = BmwCar::new;
        } else {
            this.supplier = ToyotaCar::new;
        }
    }

    public Car product() {
        return this.supplier.get();
    }
}

class CarFactoryBuilder {
    private final Predicate<Integer> predicate;

    public CarFactoryBuilder(Predicate<Integer> predicate) {
        this.predicate = predicate;
    }

    public CarFactory build(Integer requireHorsePower) {
        if (this.predicate.test(requireHorsePower)) {
            return new CarFactory(BmwCar::new);
        } else {
            return new CarFactory(ToyotaCar::new);
        }
    }
}

