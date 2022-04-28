import java.util.function.Consumer;

public class BuilderPattern {
    public static void main(String[] args) {
        MobileBuilder builder = new MobileBuilder();
        Mobile mobile = builder.with(b -> {
            b.ram = 16;
            b.screen = "1920 x 1080";
            b.soc = "A15";
            b.storage = 128;
        }).build();

        System.out.println(mobile);

    }
}
class MobileBuilder {
    int ram;
    int storage;
    String soc;
    String screen;

    public MobileBuilder with(Consumer<MobileBuilder> consumer) {
        consumer.accept(this);
        return this;
    }

    public Mobile build() {
        return new Mobile(this);
    }
}

class Mobile {
    private final int ram;
    private final int storage;
    private final String soc;
    private final String screen;

    Mobile(int ram, int storage, String soc, String screen) {
        this.ram = ram;
        this.storage = storage;
        this.soc = soc;
        this.screen = screen;
    }

    Mobile(MobileBuilder mobileBuilder) {
        this.ram = mobileBuilder.ram;
        this.storage = mobileBuilder.storage;
        this.soc = mobileBuilder.soc;
        this.screen = mobileBuilder.screen;
    }

    @Override
    public String toString() {
        return "Mobile{" +
                "ram=" + ram +
                ", storage=" + storage +
                ", soc='" + soc + '\'' +
                ", screen='" + screen + '\'' +
                '}';
    }
}
