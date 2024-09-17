package dat;

import dat.config.HibernateConfig;
import dat.config.MovieService;
import jakarta.persistence.EntityManagerFactory;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        MovieService.getMovieById(139);

        MovieService.getMovieByReleaseYear(1822);

        MovieService.getMovieByRating(1,9);

        MovieService.filterMoviesByRealeaseYear();

    }
}