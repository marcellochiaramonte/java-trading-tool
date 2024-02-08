package coding.test.trading;

import coding.test.trading.calculationModule.MeanCalculationModule;
import coding.test.trading.calculationModule.StDevCalculator;
import coding.test.trading.model.Instrument;
import coding.test.trading.model.InstrumentPriceModifier;
import coding.test.trading.model.Result;
import coding.test.trading.repository.InstrumentPriceModifierRepository;
import coding.test.trading.service.CsvParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class Controller {

    @Autowired
    private InstrumentPriceModifierRepository repository;

    @Autowired
    private MeanCalculationModule meanCalculationModule;

    @Autowired
    private StDevCalculator stDevCalculator;



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

//        OtherCalculations.AddLast10Elements();


        return List.of(
                new Result(
                        "INSTRUMENT1",
                        "Mean over all time",
                        mean1,
                        duration1
                ),
                new Result(
                        "INSTRUMENT2",
                        "Mean on November 2014",
                        mean2,
                        duration2
                ),
                new Result(
                        "INSTRUMENT3",
                        "Std Deviation on all time",
                        calculatedResults.getPopulationStandardDeviation(),
                        duration3
                )
        );
    }
}
