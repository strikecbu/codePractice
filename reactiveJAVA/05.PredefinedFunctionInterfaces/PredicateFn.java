import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

class PredicateFn {
    public static void main(String[] args) {
        Predicate<String> checkEmpty = s -> !s.isEmpty();

        List<String> words = List.of("Hello", "", "Andy", "");

        List<String> filterdWords = filter(words, checkEmpty);
        System.out.println(filterdWords);
    }

    private static <T> List<T> filter(List<T> words, Predicate<T> checkEmpty) {
        List<T> result = new ArrayList<>();
        for (T t : words) {
           if(checkEmpty.test(t)) {
               result.add(t);
           } 
        }
        return result;
    }

}
