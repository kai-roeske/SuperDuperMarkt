package controller.print;

/**
 * Implementierung des  {@link OutputWriter}s, der auf die Konsole schreibt.
 */
public class ConsoleWriter implements OutputWriter {

    @Override
    public void write(String message) {
        System.out.println(message);
    }
}
