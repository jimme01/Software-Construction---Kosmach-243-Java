package printing;

import common.Check;
import common.OutputCapture;

public class PrintingDemo {
    public static void main(String[] args) {
        run();
    }

    public static void run() {
        System.out.println("=== Завдання 4. ISP: багатофункціональний пристрій ===");

        System.out.println("\nДо: клієнт залежить від «жирного» інтерфейсу");
        printing.legacy.OfficeClient legacyClient = new printing.legacy.OfficeClient();
        String error = Check.errorOf(() -> legacyClient.scanAndPrint(new printing.legacy.BasicLaserPrinter(), "Договір"));
        System.out.println("  " + error);
        Check.that("клієнт, якому треба сканувати, падає на принтері", error.startsWith("UnsupportedOperationException"));

        System.out.println("\nПісля: кожен клієнт залежить лише від потрібної ролі");
        BasicLaserPrinter laser = new BasicLaserPrinter();
        DocumentScanner scanner = new DocumentScanner();
        OfficeMfp mfp = new OfficeMfp();

        String print = OutputCapture.capture(() -> new PrintService(laser).printReport("Звіт за жовтень"));
        String scan = OutputCapture.capture(() -> new ArchiveService(scanner).digitize());
        String booklet = OutputCapture.capture(() -> new BookletService(mfp, mfp).makeBooklet("Брошура", 12));
        String fax = OutputCapture.capture(() -> new FaxService(mfp).sendContract("+380441234567", "Договір №7"));
        String mfpPrint = OutputCapture.capture(() -> new PrintService(mfp).printReport("Звіт з МФП"));

        System.out.print(print + scan + booklet + fax + mfpPrint);
        Check.that("PrintService працює з простим лазерним принтером", print.contains("Printing: Звіт за жовтень"));
        Check.that("ArchiveService працює зі сканером", scan.contains("Scanning physical document"));
        Check.that("BookletService отримує друк і степлер від МФП", booklet.contains("Duplex printing") && booklet.contains("Stapled 12 pages"));
        Check.that("FaxService працює через роль FaxSender", fax.contains("Sending fax to +380441234567"));
        Check.that("PrintService приймає й МФП", mfpPrint.contains("Printing: Звіт з МФП"));
    }
}
