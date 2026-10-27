package printing.legacy;

// Опис предметної області: Офісна периферійна техніка та документообіг.
// Завдання коду: Керування процесами друку, сканування, степлювання та відправки факсів через єдину систему управління.

public interface IMultiFunctionDevice {
    void printDocument(String doc);
    void scanDocument();
    void sendFax(String number, String doc);
    void staplePages(int count);
    void duplexPrint(String doc);
}
