package coding.test.trading.calculationModule;

import coding.test.trading.model.Instrument;
import coding.test.trading.model.InstrumentPriceModifier;
import coding.test.trading.repository.InstrumentPriceModifierRepository;
import coding.test.trading.service.CsvParserService;
import coding.test.trading.service.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.OptionalDouble;

@Service
public class MeanCalculationModule {

    @Autowired
    private InstrumentPriceModifierRepository repository;

    public Double calculateMeanValue(String instrumentName, LocalDate startDate, LocalDate endDate) {

        OptionalDouble mean = CsvParserService.getInstrumentStream()
                .filter(i -> i.getInstrumentId().equals(instrumentName))
                .filter(i -> DateUtils.dateIsBetween(i.getLocalDate(), startDate, endDate))
                .mapToDouble(Instrument::getInstrumentValue)
                .map(value->{
                    Double valueToReturn = value;
                    Optional<InstrumentPriceModifier> multiplier = repository.findByName(instrumentName);
                    return multiplier.map(instrumentPriceModifier -> value * instrumentPriceModifier.getMultiplier()).orElse(valueToReturn);
                })
                .average();

        if (mean.isPresent()) {
            return mean.getAsDouble();
        }

        return 0.0;

    }

}
