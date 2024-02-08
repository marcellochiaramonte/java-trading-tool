package coding.test.trading.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Instrument {
    private String instrumentId;
    private LocalDate localDate;
    private Double instrumentValue;
}
