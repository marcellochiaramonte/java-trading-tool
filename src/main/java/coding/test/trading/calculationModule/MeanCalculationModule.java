package coding.test.trading.calculationModule;

import coding.test.trading.model.Instrument;
import coding.test.trading.service.CsvParserService;
import coding.test.trading.service.DateUtils;
import coding.test.trading.service.InstrumentModifierCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.OptionalDouble;

@Service
public class MeanCalculationModule {

    @Autowired
    private InstrumentModifierCache instrumentModifierCache;

    public Double calculateMeanValue(String instrumentName, LocalDate startDate, LocalDate endDate) {

        OptionalDouble mean = CsvParserService.getInstrumentStream()
                .filter(i -> i.getInstrumentId().equals(instrumentName))
                .filter(i -> DateUtils.dateIsBetween(i.getLocalDate(), startDate, endDate))
                .mapToDouble(Instrument::getInstrumentValue)
                .map(value -> value * instrumentModifierCache.getInstrumentPriceModifier(instrumentName))
                .average();

        if (mean.isPresent()) {
            return mean.getAsDouble();
        }

        return 0.0;

    }

}
