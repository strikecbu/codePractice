package guru.springframework.msscbrewery.web.model.v2;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.UUID;

/**
 * Created by jt on 2019-04-23.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerDtoV2 {

    private UUID id;
    @NotBlank
    private String beerName;
    @NotNull
    private BeerStyleEnum beerStyle;
    @Positive
    @NotNull
    private Long upc;
}
