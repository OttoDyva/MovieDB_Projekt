package dat.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
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
    private int movie_id;

    @ManyToMany
    @JoinTable(name = "movie_credit",
        joinColumns = @JoinColumn(name = "credit_id"),
        inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    private List<Movie> movies;
}
