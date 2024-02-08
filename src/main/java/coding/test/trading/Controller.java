package coding.test.trading;

import coding.test.trading.model.Instrument;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RestController
public class Controller {

    @GetMapping("/api/csv")
    private List<Instrument> getCSVFile() {


        DateTimeFormatterBuilder formatterBuilder = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern("[dd-MMM-yyyy]");

        DateTimeFormatter formatter = formatterBuilder.toFormatter(Locale.ENGLISH);

        System.out.println(LocalDate.parse("31-Dec-1996", formatter));

        List<Instrument> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/example_input.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                LocalDate localDate = LocalDate.parse(values[1], formatter);
                DayOfWeek dayOfWeek = localDate.getDayOfWeek();
                if(!dayOfWeek.equals(DayOfWeek.SATURDAY) && !dayOfWeek.equals(DayOfWeek.SUNDAY)){
                    records.add(new Instrument(values[0], localDate, Double.parseDouble(values[2])));
                }

            }
        } catch (IOException e) {
            System.out.println("Error while reading CSV file: " + e.getMessage());
        }

        return records;
    }
}
