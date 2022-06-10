package guru.springframework.msscbrewery.web.mappers;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class DateMapper {

    public Timestamp asTimestamp(LocalDateTime localDateTime) {
        return Timestamp.from(localDateTime.toInstant(localDateTime.atZone(ZoneId.systemDefault()).getOffset()));
    }

    public LocalDateTime asLocalDateTime(Timestamp timestamp) {
        return LocalDateTime.ofInstant(timestamp.toInstant(), ZoneOffset.systemDefault());
    }
}
