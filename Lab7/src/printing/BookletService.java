package printing;

public class BookletService {
    private final DuplexPrinter printer;
    private final Stapler stapler;

    public BookletService(DuplexPrinter printer, Stapler stapler) {
        this.printer = printer;
        this.stapler = stapler;
    }

    public void makeBooklet(String doc, int pages) {
        printer.duplexPrint(doc);
        stapler.staplePages(pages);
    }
}
