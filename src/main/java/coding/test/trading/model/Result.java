package coding.test.trading.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Result {

    String instrumentName;
    String operation;
    String result;
    Long processingTimeMs;

}
