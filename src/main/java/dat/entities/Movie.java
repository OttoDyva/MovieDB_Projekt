package dat.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GeneratedColumn;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
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

    @ElementCollection
    private List<String> credits;

}
