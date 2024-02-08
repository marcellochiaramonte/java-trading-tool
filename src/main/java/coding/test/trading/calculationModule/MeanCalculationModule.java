package coding.test.trading.calculationModule;

import coding.test.trading.model.Instrument;
import coding.test.trading.service.CsvParserService;
import coding.test.trading.service.DateUtils;

import java.time.LocalDate;
import java.util.OptionalDouble;

public class MeanCalculationModule {


    public static Double calculateMeanValue(String instrumentName, LocalDate startDate, LocalDate endDate) {

        OptionalDouble mean = CsvParserService.getInstrumentStream()
                .filter(i -> i.getInstrumentId().equals(instrumentName))
                .filter(i -> DateUtils.dateIsBetween(i.getLocalDate(), startDate, endDate))
                .mapToDouble(Instrument::getInstrumentValue)
                .average();

        if (mean.isPresent()) {
            return mean.getAsDouble();
        }

        return 0.0;

    }

}
