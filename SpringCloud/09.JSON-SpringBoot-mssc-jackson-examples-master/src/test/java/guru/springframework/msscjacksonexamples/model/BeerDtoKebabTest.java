package guru.springframework.msscjacksonexamples.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import guru.springframework.msscjacksonexamples.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.time.ZoneId;

@JsonTest
@ActiveProfiles("snake")
class BeerDtoKebabTest extends BaseTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testSerialize() throws JsonProcessingException {
        BeerDto dto = getDto();
        String asString = objectMapper.writeValueAsString(dto);

        System.out.println(asString);
    }

    @Test
    void testDeserialize() throws IOException {

        String str = "{\"beer_name\":\"GOOG\",\"beer_style\":\"WOW\",\"upc\":23423423,\"price\":\"225.2\",\"created_date\":\"2022-06-10 23:42:51\",\"last_updated_date\":\"2022-06-10T23:42:51.172592+08:00\",\"local_date_time\":\"2022-06-10 23:42:51\",\"local_date\":\"**20220610**\",\"beerId\":\"5bba7609-7ade-4287-908e-a294f7a7c428\"}\n";
//        String str = "{\"beer_name\":\"GOOG\",\"beer_style\":\"WOW\",\"upc\":23423423,\"price\":\"225.2\",\"created_date\":\"2022-06-10 23:39:11\",\"last_updated_date\":\"2022-06-10T23:39:11.360421+08:00\",\"local_date\":\"**20220610**\",\"beerId\":\"699e7859-1455-4a30-ac57-159cac514928\"}\n";
//        String str = "{\"beer_name\":\"GOOG\",\"beer_style\":\"WOW\",\"upc\":23423423,\"price\":\"225.2\",\"created_date\":\"2022-06-10T23:33:28.686627+08:00\",\"last_updated_date\":\"2022-06-10T23:33:28.686677+08:00\",\"local_date\":\"**20220610**\",\"beerId\":\"044ca282-0830-4e18-9b73-58c62762cb7f\"}\n";
        BeerDto beerDto = objectMapper.readValue(str, BeerDto.class);
        System.out.println(beerDto);
        assert beerDto.getBeerName() != null;
        assert beerDto.getId() != null;
    }
//    @Test
//    void name2() throws IOException {
//
//        String str2 = "{\"id\":\"06330710-4669-45a2-9647-119a27b57a06\",\"beer_name\":\"GOOG\",\"beer_style\":\"WOW\",\"upc\":23423423,\"price\":225.2,\"created_date\":\"2022-06-10T22:36:47.882263+08:00\",\"last_updated_date\":\"2022-06-10T22:36:47.882347+08:00\"}\n";
//
//        BeerDto beerDto2 = objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
//                .readValue(str2, BeerDto.class);
//
//        System.out.println(beerDto2);
//        assert beerDto2.getBeerName() != null;
//    }
}
