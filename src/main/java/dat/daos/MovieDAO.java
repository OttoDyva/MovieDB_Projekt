package dat.daos;

import dat.config.MovieService;
import dat.dtos.CreditDTO;
import dat.dtos.MovieDTO;
import dat.entities.Credit;
import dat.entities.Movie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MovieDAO implements IDAO<Movie> {
    EntityManagerFactory emf;

    public MovieDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }


    @Override
    public Movie getByID(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Movie.class, id);
        }
    }

    @Override
    public Set<Movie> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Movie> query = em.createQuery("SELECT m FROM Movie m", Movie.class);
            List<Movie> movieList = query.getResultList();
            return movieList.stream().collect(Collectors.toSet());
        }
    }

    @Override
    public void create(Movie movie) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(movie);
            em.getTransaction().commit();
        }
    }

    @Override
    public void update(Movie movie) {
        try (EntityManager em = emf.createEntityManager()) {

            em.getTransaction().begin();
            em.merge(movie);
            em.getTransaction().commit();
        }
    }

    @Override
    public void delete(Movie movie) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Movie movieIWishToDestroy = em.find(Movie.class, movie.getId());
            em.remove(movieIWishToDestroy);
            em.getTransaction().commit();
        }
    }

    public Movie dtoToEntity(MovieDTO movieDTO) {
        return Movie.builder()
                .id(movieDTO.getId())
                .credits(movieDTO.getCredits())
                .title(movieDTO.getOriginal_title())
                .realeaseDate(movieDTO.getRelease_date())
                .vote_average(movieDTO.getVote_average())
                .popularity(movieDTO.getPopularity())
                .genres(movieDTO.getGenres())
                .language(movieDTO.getOriginal_language())
                .build();
    }


    public void createMovieFromDTO() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            List<CreditDTO> creditsWithMovies = MovieService.getDanishMovieFrom2019PlusWithCredits();
            List<MovieDTO> movieDTOS = MovieService.getDanishMovieFrom2019Plus("da");

            List<Movie> movieEntities = movieDTOS.stream()
                    .map(this::dtoToEntity)
                    .collect(Collectors.toList());

            for (Movie movieEntity : movieEntities) {
                Movie existingMovie = em.find(Movie.class, movieEntity.getId());
                if (existingMovie != null) {
                    em.merge(existingMovie);
                } else {
                    em.persist(movieEntity);
                }
            }

            for (CreditDTO creditDTO : creditsWithMovies) {
                CreditDAO creditDAO = new CreditDAO(emf);
                Credit creditEntity = creditDAO.dtoToEntity(creditDTO);
                Credit existingCredit = em.find(Credit.class, creditEntity.getId());
                if (existingCredit != null) {
                    em.merge(existingCredit);
                } else {
                    em.persist(creditEntity);
                }
            }

            em.getTransaction().commit();
        } catch (IOException | InterruptedException e) {
            em.getTransaction().rollback();
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
    }


}
