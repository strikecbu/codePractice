import java.util.function.Function;

public class FunctionCurry {

    public static void main(String[] args) {
        Function<Double, Function<Double, Double>> fn1 = a -> b -> a * b ;
        Function<Double, Double> taxFn = fn1.apply(1.1);
        Double priceWithTax1 = taxFn.apply(20.0);
        Double priceWithTax2 = taxFn.apply(99.99);

        System.out.println("priceWithTax1: $" + priceWithTax1);
        System.out.println("priceWithTax2: $" + priceWithTax2);
    }
}
