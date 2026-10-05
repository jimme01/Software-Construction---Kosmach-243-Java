package task2;

import task1.InsuranceClaim;
import task1.InsuranceClaim.Approved;
import task1.InsuranceClaim.Rejected;
import task1.InsuranceClaim.Submitted;

import java.time.LocalDate;

public class ClaimRouter {

    /**
     * Маршрутизація заяви. Switch expression без default: компілятор сам перевіряє
     * вичерпність, бо InsuranceClaim — sealed. Деконструкція record-ів — у заголовку case,
     * додаткові умови над значеннями полів — через guard (when).
     */
    public static String route(InsuranceClaim claim) {
        return switch (claim) {
            case Submitted(var date, var amount) when amount > 500_000 ->
                    "Спрямувати на розслідування спеціального відділу";
            case Submitted(var date, var amount) when date.isBefore(LocalDate.now().minusDays(30)) ->
                    "Заява прострочена: ескалювати старшому страховому агенту";
            case Submitted(var date, var amount) ->
                    "Стандартна обробка заяви від " + date + " на суму " + amount;

            case Approved(var payout, var fullCoverage) when payout > 100_000 && fullCoverage ->
                    "Потребує фінального підпису керівника";
            case Approved(var payout, var fullCoverage) when !fullCoverage ->
                    "Часткове покриття: надіслати клієнту розрахунок утримань";
            case Approved(var payout, var fullCoverage) ->
                    "Виплатити " + payout;

            case Rejected(var reason, var appeal) when reason.equalsIgnoreCase("шахрайство") ->
                    "Передати матеріали до служби безпеки";
            case Rejected(var reason, var appeal) when appeal ->
                    "Повідомити клієнта про право на апеляцію";
            case Rejected(var reason, var appeal) ->
                    "Відмова остаточна: архівувати справу";
        };
    }
}
