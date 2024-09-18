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
    @JoinColumn(name = "movie_id")
    private Set<Movie> movies;
}
