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
public class Movie {
    @Id
    private int id;
    private String title;
    private LocalDate realeaseDate;
    private String language;

    @OneToMany(mappedBy = "aMovie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Genre> genres;

    @OneToMany(mappedBy = "aMovie", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Credit> credits;
}
