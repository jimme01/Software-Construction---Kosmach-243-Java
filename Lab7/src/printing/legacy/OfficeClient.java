package printing.legacy;

/** Клієнт для демонстрації проблеми: у вихідному коді його не було, додано для наочності. */
public class OfficeClient {
    public void scanAndPrint(IMultiFunctionDevice device, String doc) {
        device.scanDocument();
        device.printDocument(doc);
    }
}
