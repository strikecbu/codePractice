import java.util.function.UnaryOperator;

public class DecoratorPattern {
    public static void main(String[] args) {
        // Product itself
        Burger burger = Burger.produce(b ->
            b.addBeef()
                .addCheese()
                .addBeef()
                .addCheese()
        );
        System.out.println("You got " + burger);

        System.out.println("===================");
        // Product by decorator
        Burger burger1 = new BurgerShop(b -> b.addCheese().addBeef()).use(new Burger());
        System.out.println("You got " + burger1);
    }

}

class Burger {
    private String type = "";

    public Burger() {
    }

    public Burger(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format("%s burger", this.type.trim());
    }

    public Burger addBeef() {
        System.out.println("Add Beef to burger");
        this.type += "Beef ";
        return new Burger(this.type);
    }

    public Burger addCheese() {
        System.out.println("Add Cheese to burger");
        this.type += "Cheese ";
        return new Burger(this.type);
    }

    public static Burger produce(UnaryOperator<Burger> fn) {
        Burger burger = new Burger();
        Burger produceBurger = fn.apply(burger);
        System.out.println("Final burger is " + produceBurger.type.trim() + " burger");
        return produceBurger;
    }
}

class BurgerShop {
    private final UnaryOperator<Burger> fn;

    public BurgerShop(UnaryOperator<Burger> fn) {
        this.fn = fn;
    }
    public Burger use(Burger burger) {
        return fn.apply(burger);
    }
}
