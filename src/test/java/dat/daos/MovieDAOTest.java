package dat.daos;

import dat.config.HibernateConfig;
import dat.dtos.MovieDTO;
import dat.entities.Movie;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


class MovieDAOTest {
    private EntityManagerFactory emf;
    private MovieDAO movieDAO;

    @BeforeEach
    public void setUp() {
        emf = HibernateConfig.getEntityManagerFactoryForTest();
        movieDAO = new MovieDAO(emf);

        wipeDatabase();
    }

    private void wipeDatabase() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Movie").executeUpdate();
            em.createQuery("DELETE FROM Credit ").executeUpdate();
            em.createQuery("DELETE FROM Genre").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    void getByID() {
        Movie movie = Movie.builder()
                .id(1)
                .title("Inception")
                .realeaseDate(LocalDate.of(2010, 7, 16))
                .language("en")
                .genres(null)
                .credits(null)
                .popularity(1)
                .vote_average(10)
                .build();
        movieDAO.create(movie);

        Movie savedMovie = movieDAO.getByID(1);

        assertEquals("Inception", savedMovie.getTitle());
    }

    @Test
    void getAll() {
        Movie movie1 = Movie.builder()
                .id(1)
                .title("Inception")
                .realeaseDate(LocalDate.of(2010, 7, 16))
                .language("en")
                .genres(null)
                .credits(null)
                .popularity(1)
                .vote_average(10)
                .build();
        Movie movie2 = Movie.builder()
                .id(2)
                .title("Inception 2")
                .realeaseDate(LocalDate.of(2020, 12, 31))
                .language("en")
                .genres(null)
                .credits(null)
                .popularity(1)
                .vote_average(10)
                .build();
        movieDAO.create(movie1);
        movieDAO.create(movie2);

        assertEquals(2, movieDAO.getAll().size());
    }

    @Test
    void create() {
        Movie movie = Movie.builder()
                .id(1)
                .title("Inception")
                .realeaseDate(LocalDate.of(2010, 7, 16))
                .language("en")
                .genres(null)
                .credits(null)
                .popularity(1)
                .vote_average(10)
                .build();
        movieDAO.create(movie);

        Movie savedMovie = movieDAO.getByID(1);
        assertNotNull(savedMovie);
        assertEquals("Inception", savedMovie.getTitle());
        assertEquals(LocalDate.of(2010,7,16), savedMovie.getRealeaseDate());
        assertEquals("en", savedMovie.getLanguage());
        assertEquals(1, savedMovie.getPopularity());
        assertEquals(10, savedMovie.getVote_average());
    }

    @Test
    void update() {
        Movie movie = Movie.builder()
                .id(1)
                .title("Inception")
                .realeaseDate(LocalDate.of(2010, 7, 16))
                .language("en")
                .genres(null)
                .credits(null)
                .popularity(1)
                .vote_average(10)
                .build();
        movieDAO.create(movie);

        Movie updatedMovie = movieDAO.getByID(1);
        updatedMovie.setTitle("Inception 2");
        updatedMovie.setRealeaseDate(LocalDate.of(2020, 12, 31));
        movieDAO.update(updatedMovie);

        updatedMovie = movieDAO.getByID(1);
        assertEquals("Inception 2", updatedMovie.getTitle());
        assertEquals(LocalDate.of(2020,12,31), updatedMovie.getRealeaseDate());
        assertEquals("en", updatedMovie.getLanguage());
        assertEquals(1, updatedMovie.getPopularity());
        assertEquals(10, updatedMovie.getVote_average());

    }

    @Test
    void delete() {
        Movie movie = Movie.builder()
                .id(1)
                .title("Inception")
                .realeaseDate(LocalDate.of(2010, 7, 16))
                .language("en")
                .genres(null)
                .credits(null)
                .popularity(1)
                .vote_average(10)
                .build();
        movieDAO.create(movie);

        movieDAO.delete(movie);
        assertNull(movieDAO.getByID(1));
    }

    @Test
    void findAverageMovieRating() {
        /*
        Movie movie1 = Movie.builder()
                .id(1)
                .title("Inception")
                .realeaseDate(LocalDate.of(2010, 7, 16))
                .language("en")
                .genres(null)
                .credits(null)
                .popularity(1)
                .vote_average(10)
                .build();
        Movie movie2 = Movie.builder()
                .id(2)
                .title("Inception 2")
                .realeaseDate(LocalDate.of(2020, 12, 31))
                .language("en")
                .genres(null)
                .credits(null)
                .popularity(1)
                .vote_average(5)
                .build();
        movieDAO.create(movie1);
        movieDAO.create(movie2);

        Set<Movie> movies = movieDAO.findAverageMovieRating();
        movies.add(movie1);
        movies.add(movie2);

        assertEquals(7.5, movieDAO.findAverageMovieRating());

         */
    }

    @Test
    void findTop10MostPopularMovies() {
        Movie movie1 = Movie.builder()
                .id(1)
                .title("Inception")
                .realeaseDate(LocalDate.of(2010, 7, 16))
                .language("en")
                .genres(null)
                .credits(null)
                .popularity(1)
                .vote_average(10)
                .build();
        Movie movie2 = Movie.builder()
                .id(2)
                .title("Inception 2")
                .realeaseDate(LocalDate.of(2020, 12, 31))
                .language("en")
                .genres(null)
                .credits(null)
                .popularity(2)
                .vote_average(5)
                .build();
        movieDAO.create(movie1);
        movieDAO.create(movie2);

        Set<Movie> movies = movieDAO.findTop10MostPopularMovies();
        movies.add(movie2);
        movies.add(movie1);

        assertEquals(2, movieDAO.findTop10MostPopularMovies().size());
    }

    @Test
    void dtoToEntity() {
    }


    @Test
    void createMovieFromDTO() {
       /* MovieDTO movieDTO = MovieDTO.builder()
                .id(1)
                .original_title("Inception")
                .rase_date(LocalDate.of(2010, 7, 16))
                .original_language("en")
                .genresele(null)
                .credits(null)
                .build();

        movieDAO.createMovieFromDTO();

        Movie savedMovie = movieDAO.getByID(1);
        assertNotNull(savedMovie);
        assertEquals("Inception", savedMovie.getTitle());
        assertEquals(LocalDate.of(2010,7,16), savedMovie.getRealeaseDate());
        assertEquals("en", savedMovie.getLanguage());

        */
    }
}