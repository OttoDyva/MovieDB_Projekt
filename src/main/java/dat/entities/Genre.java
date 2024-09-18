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
public class Genre {
    @Id
    private int id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie aMovie;
}
