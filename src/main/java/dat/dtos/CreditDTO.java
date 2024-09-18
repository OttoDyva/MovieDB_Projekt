package dat.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dat.entities.Movie;
import lombok.*;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreditDTO {
    private int id;
    private String job;
    private String name;
    private String known_for_department;
    private int movie_id;
    private List<Movie> movies;
}
