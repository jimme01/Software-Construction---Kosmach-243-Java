package printing;

public class DocumentScanner implements ScannerDevice {
    @Override
    public void scanDocument() {
        System.out.println("Scanning physical document...");
    }
}
