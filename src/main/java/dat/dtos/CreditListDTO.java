package dat.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreditListDTO {
    private int page;
    private List<CreditDTO> cast;
    private List<CreditDTO> crew;
}
