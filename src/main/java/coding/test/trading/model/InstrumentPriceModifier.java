package coding.test.trading.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="INSTRUMENT_PRICE_MODIFIER")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class InstrumentPriceModifier {
    @Id
    private int id;

    private String name;

    private Double multiplier;

}
