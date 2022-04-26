import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class FunctionFn {
    public static void main(String[] args) {
        Function<String, Integer> transLength = (s) -> s.length();

        List<String> list = List.of("Andy", "Happy", "Family");
        List<Integer> numList = map(list, transLength);
        System.out.println(numList);
    }

    static <T, R> List<R> map(List<T> list, Function<T, R> fn) {
        List<R> result = new ArrayList<>();
        for (T t : list) {
            result.add(fn.apply(t));
        }
        return result;
    }

}
