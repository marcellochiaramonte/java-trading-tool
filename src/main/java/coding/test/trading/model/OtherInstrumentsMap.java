package coding.test.trading.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OtherInstrumentsMap {
    private String instrumentName;
    private Double sumOfLast10Entries;

    @Override
    public String toString() {
        return "{ instrumentName: " + instrumentName + " sumOfLast10Entries " + sumOfLast10Entries + " } ";
    }
}
