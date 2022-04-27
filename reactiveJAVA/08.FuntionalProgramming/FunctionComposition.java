
public class FunctionComposition {
    public static void main(String[] args) {
        ComposeFunction<Square, Integer> fn1 = s -> s.getArea();
        ComposeFunction<Integer, String> fn2 = d -> {
            return String.format("The side-length is %s", Math.sqrt(d));
        };
        ComposeFunction<Square, String> fn3 = fn2.compose(fn1);

        Square s = new Square(100);
        String comment = fn3.apply(s);
        System.out.println(comment);
    }
}

class Square {
    private Integer area;

    public Square(Integer area) {
        this.area = area;
    }

    public Integer getArea() {
        return this.area;
    }
}

interface ComposeFunction<T, R> {
    R apply(T t);

    default <U> ComposeFunction<U, R> compose(ComposeFunction<U, T> fn1) {
        return (U u) -> {
            return apply(fn1.apply(u));
        };
    };

}

