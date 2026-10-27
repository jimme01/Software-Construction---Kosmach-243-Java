package printing;

public class OfficeMfp implements Printer, DuplexPrinter, ScannerDevice, FaxSender, Stapler {
    @Override
    public void printDocument(String doc) {
        System.out.println("Printing: " + doc);
    }

    @Override
    public void duplexPrint(String doc) {
        System.out.println("Duplex printing: " + doc);
    }

    @Override
    public void scanDocument() {
        System.out.println("Scanning physical document...");
    }

    @Override
    public void sendFax(String number, String doc) {
        System.out.println("Sending fax to " + number + ": " + doc);
    }

    @Override
    public void staplePages(int count) {
        System.out.println("Stapled " + count + " pages.");
    }
}
