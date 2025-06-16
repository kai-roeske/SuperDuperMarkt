package controller.print;

public class ConsoleWriter implements OutputWriter {

    @Override
    public void write(String message) {
        System.out.println(message);
    }
}
