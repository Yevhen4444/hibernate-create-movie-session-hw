package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");

        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        final CinemaHallService cinemaHallService = (CinemaHallService)
                injector.getInstance(CinemaHallService.class);
        final MovieSessionService movieSessionService = (MovieSessionService)
                injector.getInstance(MovieSessionService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film about street racing, heists, and spies.");
        movieService.add(fastAndFurious);

        CinemaHall mainHall = new CinemaHall();
        mainHall.setDescription("Main Hall");
        cinemaHallService.add(mainHall);

        MovieSession session1 = new MovieSession();
        session1.setMovie(fastAndFurious);
        session1.setCinemaHall(mainHall);
        session1.setShowTime(LocalDateTime.of(2025, 12, 2, 18, 0));
        movieSessionService.add(session1);

        MovieSession session2 = new MovieSession();
        session2.setMovie(fastAndFurious);
        session2.setCinemaHall(mainHall);
        session2.setShowTime(LocalDateTime.of(2025, 12, 2, 21, 0));
        movieSessionService.add(session2);

        System.out.println("All movies:");
        movieService.getAll().forEach(System.out::println);

        System.out.println("\nAll cinema halls:");
        cinemaHallService.getAll().forEach(ch -> System.out.println(ch.getDescription()));

        System.out.println("\nTesting get methods:");
        System.out.println("CinemaHall by ID: "
                + cinemaHallService.get(mainHall.getId()).getDescription());
        System.out.println("MovieSession by ID: "
                + movieSessionService.get(session1.getId()).getShowTime());

        LocalDate date = LocalDate.of(2025, 12, 2);
        List<MovieSession> availableSessions = movieSessionService
                .findAvailableSessions(fastAndFurious.getId(), date);
        System.out.println("\nAvailable sessions on " + date + ":");
        availableSessions.forEach(s -> System.out.println(
                "Movie: " + s.getMovie().getTitle()
                       + ", Hall: " + s.getCinemaHall().getDescription()
                       + ", Time: " + s.getShowTime()
        ));
    }
}
