package dat;

import dat.config.HibernateConfig;
import dat.config.MovieService;
import dat.daos.CreditDAO;
import dat.daos.GenreDAO;
import dat.daos.MovieDAO;
import dat.entities.Genre;
import dat.entities.Movie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("moviedb");
        //MovieService.getMovieById(139);

        //MovieService.getMovieByReleaseYear(2023);

        //MovieService.getMovieByRating(1,9);

        //MovieService.filterMoviesByRealeaseYear();

        //MovieService.getMovieCreditsByMovieID(280217);

        //MovieService.getDanishMovieFrom2019Plus("da");

        //MovieService.getAllTheDamnGenres();

        MovieDAO movieDAO = new MovieDAO(emf);

        GenreDAO genreDAO = new GenreDAO(emf);

        CreditDAO creditDAO = new CreditDAO(emf);

        //movieDAO.top10ByRating();

        //movieDAO.worst10ByRating();

        //movieDAO.searchByTitle(" ");

        movieDAO.createMovieFromDTO();
        //movieDAO.createMovieFromDTO();

        //movieDAO.createMovieFromDTO();

        //genreDAO.createGenreFromDTO();

        //creditDAO.createCreditFromDTO();

        //movieDAO.findAverageMovieRating();

        //movieDAO.findTop10MostPopularMovies();

        //movieDAO.delete(movieDAO.getByID(607528));

        //movieDAO.update(movieDAO.getByID(519465).setTitle("Fuck"));

        // Update metoden:
        /*Movie m = movieDAO.getByID(833339);
        m.setTitle("Speak no Evil Fuck");
        movieDAO.update(m);
        System.out.println(m.getTitle());
         */

        // Create metoden
        /*Movie newMovie = Movie.builder()
                .id(1)
                .title("Ny film")
                .realeaseDate(LocalDate.of(2024, 10, 19))
                .language("da")
                .vote_average(9)
                .popularity(80)
                .build();

        movieDAO.create(newMovie);

         */

        movieDAO.getAll();
    }
}