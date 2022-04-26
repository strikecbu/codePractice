import java.util.Random;

class Lambda {
    public static void main(String[] args) {
        NoArgNoReturn say = () -> System.out.println("Hello");
        Person person = new Person();
        person.sayHi(say);

        HaveArgNoReturn andySay = (name) -> System.out.println(String.format("%s says Hello~", name));
        person.sayHiWithName(andySay);

        NoArgHaveReturn sayNum = () -> new Random().nextInt();
        int randomNum = person.sayOneNumer(sayNum);
        System.out.println(String.format("RandomNum: %s", randomNum));

        HaveArgHaveReturn sayRangNum = (a, b) -> new Random().nextInt(a, b);
        int rangeNum = person.sayNumberFrom0To10(sayRangNum);
        System.out.println(String.format("Random number from 0 to 10: %s", rangeNum));
    }
}

class Person {
    void sayHi(NoArgNoReturn say) {
        say.sayHello();
    }

    void sayHiWithName(HaveArgNoReturn say) {
        say.sayHello("Andy");
    }

    int sayOneNumer(NoArgHaveReturn say) {
        return say.sayNumber();
    }

    int sayNumberFrom0To10(HaveArgHaveReturn say) {
        return say.sayNumberWithRange(0, 10);
    }
}

@FunctionalInterface
interface NoArgNoReturn {
    void sayHello();
}

@FunctionalInterface
interface HaveArgNoReturn {
    void sayHello(String name);
}

@FunctionalInterface
interface NoArgHaveReturn {
    int sayNumber();
}

@FunctionalInterface
interface HaveArgHaveReturn {
    int sayNumberWithRange(int from, int to);
}