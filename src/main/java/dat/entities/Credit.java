package dat.entities;

import jakarta.persistence.*;
import lombok.*;

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

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie aMovie;
}
