package dat.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieListDTO {
    private int page;
    private List<MovieDTO> results;
    private List<CreditListDTO> credits;
}
