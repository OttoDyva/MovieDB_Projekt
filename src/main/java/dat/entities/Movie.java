package dat.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@NamedQueries( {
        //@NamedQuery(name = "Movie.findBy", query = "SELECT e FROM Employee e WHERE e.name = :name"),
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
