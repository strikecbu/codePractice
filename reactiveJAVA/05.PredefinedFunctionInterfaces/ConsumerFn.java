import java.util.function.Consumer;

public class ConsumerFn {
    public static void main(String[] args) {
        Consumer<String> printName = s -> System.out.println(s);
        printName.accept("Andy, How are you?");
    }
}
