package task4.components;

import task4.Component;

@Component(name = "catering", version = "1.0")
public class CateringService {
    private String menuType;

    public void execute() {
        System.out.println("[CateringService] завантаження харчування, menuType = " + menuType);
    }

    @Override
    public String toString() {
        return "CateringService{menuType=" + menuType + "}";
    }
}
