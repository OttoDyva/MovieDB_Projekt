package dat.daos;

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

public class CreditDAO implements IDAO<Credit>{
    EntityManagerFactory emf;
    public CreditDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    @Override
    public Credit getByID(Integer id) {
        try(EntityManager em = emf.createEntityManager()){
            return em.find(Credit.class, id);
        }
    }

    @Override
    public Set<Credit> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Credit> query = em.createQuery("SELECT c FROM Credit c", Credit.class);
            List<Credit> creditList = query.getResultList();
            return creditList.stream().collect(Collectors.toSet());
        }
    }

    @Override
    public void create(Credit credit) {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(credit);
            em.getTransaction().commit();
        }

    }

    @Override
    public void update(Credit credit) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.merge(credit);
            em.getTransaction().commit();
        }
    }

    @Override
    public void delete(Credit credit) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Credit creditIWishToDestroy = em.find(Credit.class,credit.getId());
            em.remove(creditIWishToDestroy);
            em.getTransaction().commit();
        }
    }

    public static Credit dtoToEntity(Credit creditDTO) {
        return Credit.builder()
                .id(creditDTO.getId())
                .name(creditDTO.getName())
                .department(creditDTO.getDepartment())
                .job(creditDTO.getJob())
                .build();
    }

    public void createCreditFromDTO(Credit creditDTO) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Credit creditEntity = dtoToEntity(creditDTO);

            if (creditEntity.getId() != 0 && em.find(Movie.class, creditEntity.getId()) != null) {
                em.merge(creditEntity);
            } else {
                em.persist(creditEntity);
            }

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
