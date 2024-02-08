package coding.test.trading.model;

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
