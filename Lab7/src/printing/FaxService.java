package printing;

public class FaxService {
    private final FaxSender faxSender;

    public FaxService(FaxSender faxSender) {
        this.faxSender = faxSender;
    }

    public void sendContract(String number, String doc) {
        faxSender.sendFax(number, doc);
    }
}
