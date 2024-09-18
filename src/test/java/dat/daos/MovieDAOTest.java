package dat.daos;

import dat.config.HibernateConfig;
import dat.dtos.MovieDTO;
import dat.entities.Movie;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MovieDAOTest {
    private EntityManagerFactory emf;
    private MovieDAO movieDAO;
    private GenreDAO genreDAO;
    private CreditDAO creditDAO;

    @BeforeEach
    public void setUp() {
        emf = HibernateConfig.getEntityManagerFactoryForTest();
        movieDAO = new MovieDAO(emf);
        genreDAO = new GenreDAO(emf);
        creditDAO = new CreditDAO(emf);

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
    }

    @Test
    void getAll() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void dtoToEntity() {
        MovieDTO movieDTO = MovieDTO.builder()
                .id(1)
                .original_title("Inception")
                .release_date(LocalDate.of(2010, 7, 16))
                .original_language("en")
                .genres(null)
                .credits(null)
                .build();

        Movie movieEntity = MovieDAO.dtoToEntity(movieDTO);

        assertNotNull(movieEntity);
        assertEquals(1, movieEntity.getId());
        assertEquals("Inception", movieEntity.getTitle());
        assertEquals(LocalDate.of(2010,7,16), movieEntity.getRealeaseDate());
        assertEquals("en", movieEntity.getLanguage());
    }


    @Test
    void createMovieFromDTO() {
        MovieDTO movieDTO = MovieDTO.builder()
                .id(1)
                .original_title("Inception")
                .release_date(LocalDate.of(2010, 7, 16))
                .original_language("en")
                .genres(null)
                .credits(null)
                .build();

        movieDAO.createMovieFromDTO(movieDTO);

        Movie savedMovie = movieDAO.getByID(1);
        assertNotNull(savedMovie);
        assertEquals("Inception", savedMovie.getTitle());
        assertEquals(LocalDate.of(2010,7,16), savedMovie.getRealeaseDate());
        assertEquals("en", savedMovie.getLanguage());

    }
}