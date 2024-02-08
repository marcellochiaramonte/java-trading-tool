package coding.test.trading.service;

import java.time.LocalDate;

public class DateUtils {
    public static Boolean dateIsBetween(LocalDate date, LocalDate startDate, LocalDate endDate){
        return (date.isAfter(startDate) && date.isBefore(endDate)) || date.equals(startDate) || date.equals(endDate);
    }
}
