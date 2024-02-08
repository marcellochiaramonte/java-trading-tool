package coding.test.trading.calculationModule;

import coding.test.trading.model.Instrument;
import coding.test.trading.service.CsvParserService;
import coding.test.trading.service.DateUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class StDevCalculator {

    private Integer count = 0;
    private Double mean = 0.0;
    private Double dSquared = 0.0;

    public static StDevCalculator calculate(String instrumentName, LocalDate startDate, LocalDate endDate) {

        StDevCalculator calculator = new StDevCalculator();

        CsvParserService.getInstrumentStream()
                .filter(i -> i.getInstrumentId().equals(instrumentName))
                .filter(i -> DateUtils.dateIsBetween(i.getLocalDate(), startDate, endDate))
                .mapToDouble(Instrument::getInstrumentValue)
                .forEach(calculator::update);

        return calculator;

    }

    synchronized private void update(double newValue) {
        this.count++;

        double meanDifferential = (newValue - this.mean) / this.count;

        double newMean = this.mean + meanDifferential;

        double dSquaredIncrement =
                (newValue - newMean) * (newValue - this.mean);

        double newDSquared = this.dSquared + dSquaredIncrement;

        this.mean = newMean;

        this.dSquared = newDSquared;
    }


    public Double getPopulationVariance() {
        return this.dSquared / this.count;
    }

    public Double getPopulationStandardDeviation() {
        return Math.sqrt(this.getPopulationVariance());
    }

    public Double getSampleVariance() {
        return this.count > 1 ? this.dSquared / (this.count - 1) : 0;
    }

    public Double getSampleStandardDeviation() {
        return Math.sqrt(this.getSampleVariance());
    }

}
