package coding.test.trading.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name="INSTRUMENT_PRICE_MODIFIER")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class InstrumentPriceModifier {
    @Id
    private int id;
    private String name;
    private Double multiplier;

}
