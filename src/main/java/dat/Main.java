package dat;

import dat.config.HibernateConfig;
import dat.daos.CreditDAO;
import dat.daos.GenreDAO;
import dat.daos.MovieDAO;
import jakarta.persistence.EntityManagerFactory;

import java.io.IOException;

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

        //movieDAO.createMovieFromDTO();

        //movieDAO.getAllMoviesWithCredits();

        //movieDAO.searchMovieWithCredits("lego");

        //movieDAO.createMovieFromDTO();

        //genreDAO.createGenreFromDTO();

        //creditDAO.createCreditFromDTO();

    }
}