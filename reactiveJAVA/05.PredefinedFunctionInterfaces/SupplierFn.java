import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;

public class SupplierFn {
    public static void main(String[] args) {
        Supplier<Integer> takeNumer = () -> new Random().nextInt(100);
        System.out.println(takeNumer.get());
    }
    
}
