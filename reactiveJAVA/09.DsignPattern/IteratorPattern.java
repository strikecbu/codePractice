import java.util.function.Consumer;

public class IteratorPattern {
    public static void main(String[] args) {
        MyList myList = new MyList(new Object[]{1, 2, 3, 4, 55});
        myList.forEach(System.out::println);
    }
}

class MyList {
    private final Object[] arrays;

    public MyList(Object[] arrays) {
        this.arrays = arrays;
    }

    public void forEach(Consumer<Object> fn) {
        for (Object array : this.arrays) {
            fn.accept(array);
        }
    }
}
