package dat.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class Credit {
    @Id
    private int id;
    private String name;
    private String department;
    private String job;

    @ManyToMany
    @JoinTable(name = "movie_credits",
        joinColumns = @JoinColumn(name = "credit_id"),
        inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    private Set<Movie> movies;
}
