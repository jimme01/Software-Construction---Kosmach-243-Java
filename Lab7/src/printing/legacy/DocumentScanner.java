package printing.legacy;

public class DocumentScanner implements IMultiFunctionDevice {
    @Override public void scanDocument() { System.out.println("Scanning physical document..."); }

    @Override public void printDocument(String doc) { throw new UnsupportedOperationException("Scanner cannot print documents."); }
    @Override public void sendFax(String number, String doc) { throw new UnsupportedOperationException("Scanner cannot send faxes."); }
    @Override public void staplePages(int count) { throw new UnsupportedOperationException("Scanner cannot staple pages."); }
    @Override public void duplexPrint(String doc) { throw new UnsupportedOperationException("Scanner cannot print."); }
}
