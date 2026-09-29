package task2;

/** Фільм: 8 закритих полів, доступ через getter/setter. */
public class Film {
    private String title;
    private String director;
    private String genre;
    private int releaseYear;
    private int durationMinutes;
    private int actorsCount;
    private double rating;
    private double ticketPrice;

    /** Конструктор без параметрів. */
    public Film() {
    }

    /** Конструктор з усіма параметрами. */
    public Film(String title, String director, String genre, int releaseYear,
                int durationMinutes, int actorsCount, double rating, double ticketPrice) {
        this.title = title;
        this.director = director;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.durationMinutes = durationMinutes;
        this.actorsCount = actorsCount;
        this.rating = rating;
        this.ticketPrice = ticketPrice;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public int getActorsCount() {
        return actorsCount;
    }

    public void setActorsCount(int actorsCount) {
        this.actorsCount = actorsCount;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    // ---------- власні методи ----------

    /** Скільки років минуло з виходу фільму. */
    public int getAge(int currentYear) {
        return currentYear - releaseYear;
    }

    /** Тривалість у годинах. */
    public double getDurationInHours() {
        return durationMinutes / 60.0;
    }

    /** Виручка від продажу заданої кількості квитків. */
    public double estimateRevenue(int ticketsSold) {
        return ticketsSold * ticketPrice;
    }

    @Override
    public String toString() {
        return String.format("Фільм «%s», режисер: %s, жанр: %s, рік: %d, тривалість: %d хв, "
                        + "акторів: %d, рейтинг: %.1f, ціна квитка: %.2f",
                title, director, genre, releaseYear, durationMinutes,
                actorsCount, rating, ticketPrice);
    }
}
