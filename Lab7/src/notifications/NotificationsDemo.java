package notifications;

import common.Check;
import common.OutputCapture;

public class NotificationsDemo {
    private static final String LONG_SMS = "Шановний клієнте! Нагадуємо, що термін дії вашого тарифного плану "
            + "спливає завтра. Щоб уникнути відключення послуг, поповніть рахунок до кінця доби "
            + "або скористайтеся автоплатежем у мобільному застосунку.";

    private static final String[][] CASES = {
            {"EMAIL", "student@univ.local", "Новий рахунок", "Ваш рахунок готовий до сплати."},
            {"SMS", "+380501234567", "Код підтвердження", LONG_SMS},
            {"PUSH", "device-token-42", "Акція", "Знижка 20% діє сьогодні!"},
            {"TELEGRAM", "123456789", "Нагадування", "Іспит завтра о 9:00."}
    };

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        System.out.println("=== Завдання 2. OCP: сповіщення ===");
        notifications.legacy.NotificationSender legacySender = new notifications.legacy.NotificationSender();
        NotificationSender sender = new NotificationSender(
                new EmailChannel(), new SmsChannel(), new PushChannel(), new TelegramChannel());

        for (String[] row : CASES) {
            String channel = row[0];
            notifications.legacy.NotificationMessage legacyMessage =
                    new notifications.legacy.NotificationMessage(row[1], row[2], row[3], "NORMAL");
            NotificationMessage message = new NotificationMessage(row[1], row[2], row[3], "NORMAL");

            String before = OutputCapture.capture(() -> legacySender.send(
                    legacyMessage, notifications.legacy.NotificationChannel.valueOf(channel)));
            String after = OutputCapture.capture(() -> sender.send(message, channel));

            System.out.print(after);
            Check.that(channel + ": вивід однаковий до й після", before.equals(after));
        }

        System.out.println();
        NotificationMessage message = new NotificationMessage("+380501234567", "Привіт", "Тест каналу", "NORMAL");
        Check.that("невідомий канал: " + Check.errorOf(() -> sender.send(message, "SLACK")),
                Check.errorOf(() -> sender.send(message, "SLACK"))
                        .equals("UnsupportedOperationException: Канал сповіщення SLACK не підтримується."));

        System.out.println("\nДодаємо канал VIBER: нові файли, жодного рядка у NotificationSender не змінено");
        sender.register(new ViberChannel());
        String viber = OutputCapture.capture(() -> sender.send(message, "viber"));
        System.out.print(viber);
        Check.that("VIBER працює після register()", viber.contains("[VIBER] Phone: +380501234567"));
    }
}
