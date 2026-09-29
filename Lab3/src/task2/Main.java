package task2;

import java.time.Year;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in).useLocale(Locale.US); // дробові числа через крапку

        // Конструктор без параметрів
        Film empty = new Film();
        System.out.println("Фільм за замовчуванням: " + empty);

        // Ввід даних для одного екземпляра
        System.out.println("\nВведіть дані фільму:");
        System.out.print("Назва: ");
        String title = sc.nextLine();
        System.out.print("Режисер: ");
        String director = sc.nextLine();
        System.out.print("Жанр: ");
        String genre = sc.nextLine();
        System.out.print("Рік випуску: ");
        int year = sc.nextInt();
        System.out.print("Тривалість (хв): ");
        int duration = sc.nextInt();
        System.out.print("Кількість акторів: ");
        int actors = sc.nextInt();
        System.out.print("Рейтинг (0..10): ");
        double rating = sc.nextDouble();
        System.out.print("Ціна квитка: ");
        double price = sc.nextDouble();

        // Конструктор з усіма параметрами
        Film film = new Film(title, director, genre, year, duration, actors, rating, price);

        // Демонстрація методів класу
        System.out.println("\n" + film);
        System.out.println("Вік фільму: " + film.getAge(Year.now().getValue()) + " р.");
        System.out.printf("Тривалість: %.2f год%n", film.getDurationInHours());
        System.out.printf("Виручка від 1000 квитків: %.2f%n", film.estimateRevenue(1000));

        // Зміна поля через setter і повторний розрахунок
        film.setTicketPrice(film.getTicketPrice() * 1.1);
        System.out.printf("Після підвищення ціни на 10%%: %.2f%n", film.estimateRevenue(1000));
    }
}
