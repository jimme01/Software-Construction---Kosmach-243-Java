package task4.components;

import task4.Component;

@Component(name = "security", version = "1.1")
public class SecurityScreening {
    private String screeningMode;

    public void execute() {
        System.out.println("[SecurityScreening] перевірка безпеки, screeningMode = " + screeningMode);
    }

    @Override
    public String toString() {
        return "SecurityScreening{screeningMode=" + screeningMode + "}";
    }
}
