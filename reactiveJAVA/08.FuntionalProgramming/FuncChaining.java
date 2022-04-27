public class FuncChaining {
    public static void main(String[] args) {
        FunctionChain<String> fn1 = name -> System.out.println(String.format("Hello %s", name));;
        FunctionChain<String> fn2 = name -> System.out.println(String.format("你好 %s", name));;
        FunctionChain<String> fnExtra = name -> System.out.println(String.format("Hola %s", name));;
        
        FunctionChain<String> fn3 = fn1.acceptThen(fn2).acceptThen(fnExtra);
        fn3.accept("Andy");
    }
}

@FunctionalInterface
interface FunctionChain<T> {
    void accept(T t);

    default FunctionChain<T> acceptThen(FunctionChain<T> fn2) {
        return (t) -> {
            this.accept(t);
            fn2.accept(t);
        };
    };
}
