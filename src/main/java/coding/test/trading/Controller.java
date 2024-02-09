package coding.test.trading;

import coding.test.trading.calculationModule.MeanCalculationModule;
import coding.test.trading.calculationModule.Sum10Calculator;
import coding.test.trading.calculationModule.StDevCalculator;
import coding.test.trading.model.Instrument;
import coding.test.trading.model.InstrumentPriceModifier;
import coding.test.trading.model.OtherInstrumentsMap;
import coding.test.trading.model.Result;
import coding.test.trading.repository.InstrumentPriceModifierRepository;
import coding.test.trading.service.CsvParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@RestController
public class Controller {

    @Autowired
    private InstrumentPriceModifierRepository repository;

    @Autowired
    private MeanCalculationModule meanCalculationModule;

    @Autowired
    private StDevCalculator stDevCalculator;

    @Autowired
    private Sum10Calculator sum10Calculator;



    @GetMapping("/api/modifiers")
    private List<InstrumentPriceModifier> getAllModifiers(){
        return (List<InstrumentPriceModifier>) repository.findAll();
    }

    @GetMapping("/api/input-data")
    private List<Instrument> getInputData() {
        return CsvParserService.getInstrumentStream().toList();
    }

    @GetMapping("/api/results")
    private List<Result> getResults() {
        // INSTRUMENT1
        long startTime = System.nanoTime();
        Double mean1 = meanCalculationModule.calculateMeanValue(
                "INSTRUMENT1",
                LocalDate.of(1996, 1, 1),
                LocalDate.of(2014, 12, 19)
        );
        long endTime = System.nanoTime();

        Long duration1 = (endTime - startTime) / 1_000_000;  //divide by 1_000_000 to get milliseconds.

        // INSTRUMENT2
        startTime = System.nanoTime();
        Double mean2 = meanCalculationModule.calculateMeanValue(
                "INSTRUMENT2",
                LocalDate.of(2014, 11, 1),
                LocalDate.of(2014, 11, 30)
        );
        endTime = System.nanoTime();

        Long duration2 = (endTime - startTime) / 1_000_000;  //divide by 1_000_000 to get milliseconds.


        // INSTRUMENT3
        startTime = System.nanoTime();
        StDevCalculator calculatedResults = stDevCalculator.calculate(
                "INSTRUMENT3",
                LocalDate.of(1996, 1, 1),
                LocalDate.of(2014, 12, 19)
        );
        endTime = System.nanoTime();

        Long duration3 = (endTime - startTime) / 1_000_000;  //divide by 1_000_000 to get milliseconds.


        // OTHER INSTRUMENTS
        startTime = System.nanoTime();
        List<OtherInstrumentsMap> sum4 = sum10Calculator.sumOtherInstruments();
        AtomicReference<String> s = new AtomicReference<>("");
        sum4.forEach(e->{
            s.set(s + e.toString());
        });
        endTime = System.nanoTime();

        Long duration4 = (endTime - startTime) / 1_000_000;  //divide by 1_000_000 to get milliseconds.


        return List.of(
                new Result(
                        "INSTRUMENT1",
                        "Mean over all time",
                        mean1.toString(),
                        duration1
                ),
                new Result(
                        "INSTRUMENT2",
                        "Mean on November 2014",
                        mean2.toString(),
                        duration2
                ),
                new Result(
                        "INSTRUMENT3",
                        "Std Deviation on all time",
                        calculatedResults.getPopulationStandardDeviation().toString(),
                        duration3
                ),
                new Result(
                        "OTHER_INSTRUMENTS",
                        "Sum the 10 most recent ones",
                        sum4.toString(),
                        duration4
                )
        );
    }
}
