package coding.test.trading.service;

import coding.test.trading.model.Instrument;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;
import java.util.stream.Stream;

public class CsvParserService {

    public static Stream<Instrument> getInstrumentStream() {
        DateTimeFormatterBuilder formatterBuilder = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern("[dd-MMM-yyyy]");

        DateTimeFormatter formatter = formatterBuilder.toFormatter(Locale.ENGLISH);

        Stream<Instrument> instrumentStream = Stream.of();

        // Todo: using 'try'-with-resources statement closes the stream before being able to return it
        try {
            instrumentStream = Files.lines(Path.of("src/main/resources/example_input.txt"))
                    .parallel()
                    .map(line -> {
                        String[] values = line.split(",");
                        return new Instrument(values[0], LocalDate.parse(values[1], formatter), Double.parseDouble(values[2]));
                    })
                    .filter(i -> {
                        DayOfWeek dayOfWeek = i.getLocalDate().getDayOfWeek();
                        return !dayOfWeek.equals(DayOfWeek.SATURDAY) && !dayOfWeek.equals(DayOfWeek.SUNDAY);
                    });

        } catch (Exception e) {
            System.out.println("Error while parsing the file: " + e.getMessage());
        }

        return instrumentStream;
    }

}
