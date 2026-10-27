package printing;

public class ArchiveService {
    private final ScannerDevice scanner;

    public ArchiveService(ScannerDevice scanner) {
        this.scanner = scanner;
    }

    public void digitize() {
        scanner.scanDocument();
    }
}
