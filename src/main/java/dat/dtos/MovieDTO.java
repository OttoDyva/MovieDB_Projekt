package dat.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieDTO {
    private int id;
    private String original_title;
    private List<Integer> genre_ids;
    private List<String> genres;
    private String original_language;
    private LocalDate release_date;
    private float vote_average;
    private float popularity;
    private String overview;
    private List<CreditDTO> credits;

    public List<String> genreHelper(Map<Integer, String> genres) {
        return genre_ids.stream()
                .map(genres::get)
                .collect(Collectors.toList());
    }
}
