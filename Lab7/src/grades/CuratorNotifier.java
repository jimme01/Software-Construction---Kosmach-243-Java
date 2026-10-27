package grades;

public class CuratorNotifier {
    public void send(String curatorEmail, String studentName, String transcript) {
        if (curatorEmail == null || curatorEmail.isBlank()) {
            return;
        }
        System.out.println("Надсилання пошти куратору " + curatorEmail + " через mail.univ.local");
        System.out.println("Тема: Успішність студента " + studentName);
        System.out.println("Тіло: " + transcript);
    }
}
