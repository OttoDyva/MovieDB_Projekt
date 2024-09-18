package dat.daos;

import dat.dtos.GenreDTO;
import dat.dtos.MovieDTO;
import dat.entities.Credit;
import dat.entities.Genre;
import dat.entities.Movie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GenreDAO implements IDAO<Genre> {
    EntityManagerFactory emf;
    public GenreDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    @Override
    public Genre getByID(Integer id) {
        try(EntityManager em = emf.createEntityManager()){
            return em.find(Genre.class, id);
        }
    }

    @Override
    public Set<Genre> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Genre> query = em.createQuery("SELECT g FROM Genre g", Genre.class);
            List<Genre> genreList = query.getResultList();
            return genreList.stream().collect(Collectors.toSet());
        }
    }

    @Override
    public void create(Genre genre) {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            em.persist(genre);
            em.getTransaction().commit();
        }
    }

    @Override
    public void update(Genre genre) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.merge(genre);
            em.getTransaction().commit();
        }
    }

    @Override
    public void delete(Genre genre) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Genre genreIWishToDestroy = em.find(Genre.class,genre.getId());
            em.remove(genreIWishToDestroy);
            em.getTransaction().commit();
        }
    }

    public static Genre dtoToEntity(GenreDTO genreDTO) {
        return Genre.builder()
                .id(genreDTO.getId())
                .name(genreDTO.getName())
                .build();
    }

    public void createGenreFromDTO(GenreDTO genreDTO) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Genre genreEntity = dtoToEntity(genreDTO);

            if (genreEntity.getId() != 0 && em.find(Genre.class, genreEntity.getId()) != null) {
                em.merge(genreEntity);
            } else {
                em.persist(genreEntity);
            }

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
