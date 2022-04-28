public class CommandPattern {
    public static void main(String[] args) {
        AC ac1 = new AC();
        AC ac2 = new AC();

        Remote remote = new Remote(ac1::openAc);
        remote.runCommand();

    }
}

@FunctionalInterface
interface Command {
    void execute();
}

class Remote {

    private final Command command;

    Remote(Command command) {
        this.command = command;
    }

    public void runCommand() {
        this.command.execute();
    }
}

class AC {
    public void openAc() {
        System.out.println("Open AC");
    }
    public void closeAc() {
        System.out.println("Close AC");
    }
}
