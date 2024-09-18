package dat.daos;

import dat.config.HibernateConfig;
import dat.dtos.GenreDTO;
import dat.dtos.MovieDTO;
import dat.entities.Genre;
import dat.entities.Movie;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/*

class GenreDAOTest {
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
        GenreDTO genreDTO = GenreDTO.builder()
                .id(1)
                .name("Action")
                .build();

        Genre genreEntity = GenreDAO.dtoToEntity(genreDTO);

        assertNotNull(genreEntity);
        assertEquals(1, genreEntity.getId());
        assertEquals("Action", genreEntity.getName());
    }

    @Test
    void createGenreFromDTO() {
        GenreDTO genreDTO = GenreDTO.builder()
                .id(1)
                .name("Action")
                .build();

        genreDAO.createGenreFromDTO(genreDTO);

        Genre savedGenre = genreDAO.getByID(1);
        assertNotNull(savedGenre);
        assertEquals("Action", savedGenre.getName());

    }
}

 */