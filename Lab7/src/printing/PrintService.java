package printing;

public class PrintService {
    private final Printer printer;

    public PrintService(Printer printer) {
        this.printer = printer;
    }

    public void printReport(String text) {
        printer.printDocument(text);
    }
}
