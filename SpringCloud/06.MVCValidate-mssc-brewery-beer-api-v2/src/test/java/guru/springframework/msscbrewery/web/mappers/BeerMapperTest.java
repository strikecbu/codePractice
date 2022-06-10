package guru.springframework.msscbrewery.web.mappers;

import guru.springframework.msscbrewery.domain.Beer;
import guru.springframework.msscbrewery.web.model.v2.BeerDtoV2;
import guru.springframework.msscbrewery.web.model.v2.BeerStyleEnum;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class BeerMapperTest {

    @Test
    void beerDtoToBeer() {
        BeerDtoV2 drink = BeerDtoV2.builder()
                .beerName("GOOD to drink")
                .beerStyle(BeerStyleEnum.ALE)
                .upc(123L)
                .build();

        Beer beer = BeerMapper.INSTANCE.beerDtoToBeer(drink);
        assertEquals(drink.getBeerName(), beer.getBeerName());
        assertEquals(drink.getBeerStyle()
                .toString(), beer.getBeerStyle());
    }

    @Test
    void beerToBeerDtoV2() {
        Beer beer = Beer.builder()
                .beerName("Wow good")
                .beerStyle("ALE")
                .upc(555L)
                .build();

        BeerDtoV2 beerDtoV2 = BeerMapper.INSTANCE.beerToBeerDtoV2(beer);
        assertEquals(beer.getBeerName(), beerDtoV2.getBeerName());
        assertEquals(beer.getBeerStyle(),
                beerDtoV2.getBeerStyle()
                        .toString());
    }
}
