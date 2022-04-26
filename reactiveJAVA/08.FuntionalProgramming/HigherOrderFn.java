
class HigherOrderFn {
    public static void main(String[] args) {
        
       FactoryFn<String> factory = createFactory(Math::random, rand -> String.format("The number generate is %s", rand));

       String product = factory.create();
       System.out.println(product);


    }
    static <T, R> FactoryFn<R> createFactory(Producer<T> producer, Configuar<T, R> configuar) {
        T t = producer.produce();
        R r = configuar.config(t);
        return () -> r;
    }
}



@FunctionalInterface
interface FactoryFn<T> {
    T create();
}

@FunctionalInterface
interface Producer<T> {
    T produce();
}

@FunctionalInterface
interface Configuar<T, R> {
    R config(T t);
}