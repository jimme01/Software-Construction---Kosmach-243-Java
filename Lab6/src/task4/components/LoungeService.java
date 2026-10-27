package task4.components;

public class LoungeService {
    private String guestCount;

    public void execute() {
        System.out.println("[LoungeService] обслуговування VIP-залу, guestCount = " + guestCount);
    }

    @Override
    public String toString() {
        return "LoungeService{guestCount=" + guestCount + "}";
    }
}
