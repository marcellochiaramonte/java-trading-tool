package coding.test.trading.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReturnObject {

    String instrumentName;
    String operation;
    Double result;
    Long processingTimeMs;

}
