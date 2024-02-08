package coding.test.trading;

import coding.test.trading.calculationModule.MeanCalculationModule;
import coding.test.trading.calculationModule.StDevCalculator;
import coding.test.trading.model.Instrument;
import coding.test.trading.model.ReturnObject;
import coding.test.trading.service.CsvParserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
public class Controller {

    @GetMapping("/api/input-data")
    private List<Instrument> getInputData() throws IOException {

        return CsvParserService.getInstrumentStream().toList();
    }

    @GetMapping("/api/results")
    private List<ReturnObject> getResults() {

        // INSTRUMENT1
        long startTime = System.nanoTime();
        Double mean1 = MeanCalculationModule.calculateMeanValue(
                "INSTRUMENT1",
                LocalDate.of(1996, 1, 1),
                LocalDate.of(2014, 12, 19)
        );
        long endTime = System.nanoTime();

        Long duration1 = (endTime - startTime) / 1_000_000;  //divide by 1_000_000 to get milliseconds.

        // INSTRUMENT2
        startTime = System.nanoTime();
        Double mean2 = MeanCalculationModule.calculateMeanValue(
                "INSTRUMENT2",
                LocalDate.of(2014, 11, 1),
                LocalDate.of(2014, 11, 30)
        );
        endTime = System.nanoTime();

        Long duration2 = (endTime - startTime) / 1_000_000;  //divide by 1_000_000 to get milliseconds.


        // INSTRUMENT3
        startTime = System.nanoTime();
        StDevCalculator calculatedResults = StDevCalculator.calculate(
                "INSTRUMENT3",
                LocalDate.of(1996, 1, 1),
                LocalDate.of(2014, 12, 19)
        );
        endTime = System.nanoTime();

        Long duration3 = (endTime - startTime) / 1_000_000;  //divide by 1_000_000 to get milliseconds.


        return List.of(
                new ReturnObject(
                        "INSTRUMENT1",
                        "Mean over all time",
                        mean1,
                        duration1
                ),
                new ReturnObject(
                        "INSTRUMENT2",
                        "Mean on November 2014",
                        mean2,
                        duration2
                ),
                new ReturnObject(
                        "INSTRUMENT3",
                        "Std Deviation on all time",
                        calculatedResults.getPopulationStandardDeviation(),
                        duration3
                )
        );

    }
}
