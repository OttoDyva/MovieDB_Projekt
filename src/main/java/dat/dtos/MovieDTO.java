package dat.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieDTO {
    private int id;
    private String original_title;
    private String title;
    private List<GenreDTO> genres;
    private String imdb_id;
    private String original_language;
    private String overview;
    private LocalDate release_date;
    private long revenue;
    private int runtime;
    private String tagline;
    private double vote_average;
    private int vote_count;
}
