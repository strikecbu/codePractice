import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class UnaryOperatorFn {
    public static void main(String[] args) {
        UnaryOperator<Double> addTax = d -> d * 1.1;
        List<Double> prices = List.of(10.0, 9.99, 29.9);
        
        List<Double> priceWithTax = map(prices, addTax);
        System.out.println(priceWithTax);
    }

    private static <T> List<T> map(List<T> prices, UnaryOperator<T> addTax) {
        List<T> result = new ArrayList<>();
        for (T t : prices) {
            result.add(addTax.apply(t));
        }
        return result;
    }
    
}
