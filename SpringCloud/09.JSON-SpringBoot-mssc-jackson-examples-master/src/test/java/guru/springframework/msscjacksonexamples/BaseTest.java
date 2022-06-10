package guru.springframework.msscjacksonexamples;

import guru.springframework.msscjacksonexamples.model.BeerDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;

public class BaseTest {
    public BeerDto getDto() {
        return BeerDto.builder()
                .beerName("GOOG")
                .beerStyle("WOW")
                .createdDate(OffsetDateTime.now())
                .lastUpdatedDate(OffsetDateTime.now())
                .price(BigDecimal.valueOf(225.2))
                .upc(23423423L)
                .id(UUID.randomUUID())
                .localDate(LocalDate.now())
                .localDateTime(LocalDateTime.now())
                .build();
    }
}
