package coding.test.trading.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class InstrumentPriceCachedValue {
    LocalDateTime timestamp;
    Double value;
}
