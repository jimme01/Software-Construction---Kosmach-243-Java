package printing.legacy;

public class BasicLaserPrinter implements IMultiFunctionDevice {
    @Override public void printDocument(String doc) { System.out.println("Printing: " + doc); }
    @Override public void duplexPrint(String doc) { System.out.println("Duplex printing: " + doc); }

    @Override public void scanDocument() { throw new UnsupportedOperationException("Basic laser printer has no scanner unit."); }
    @Override public void sendFax(String number, String doc) { throw new UnsupportedOperationException("Basic printer cannot send faxes."); }
    @Override public void staplePages(int count) { throw new UnsupportedOperationException("Basic printer has no stapler module."); }
}
