package dat.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@NamedQueries({
        //@NamedQuery(name = "Movie.findBy", query = "SELECT e FROM Employee e WHERE e.name = :name"),
        @NamedQuery(name = "Movie.top10BestByRating", query =  "SELECT m FROM Movie m ORDER BY m.vote_average DESC"),
        @NamedQuery(name = "Movie.worst10ByRating", query = "SELECT m FROM Movie m ORDER BY m.vote_average ASC"),
        @NamedQuery(name = "Movie.searchMovieByTitle", query = "SELECT m FROM Movie m WHERE LOWER(m.title) LIKE LOWER(:title)")
})
public class Movie {
    @Id
    private int id;
    private String title;
    private LocalDate realeaseDate;
    private String language;
    private float vote_average;
    private float popularity;

    @ElementCollection
    private List<String> genres;

    @ManyToMany
    @JoinTable(name = "movie_credit",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "credit_id")
    )
    private List<Credit> credits;

}
