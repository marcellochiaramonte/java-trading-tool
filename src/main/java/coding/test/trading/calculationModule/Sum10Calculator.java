package coding.test.trading.calculationModule;

import coding.test.trading.model.Instrument;
import coding.test.trading.model.OtherInstrumentsMap;
import coding.test.trading.service.CsvParserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/*
For entries other than INSTRUMENT1-2-3, add the last 10 elements
 */
@Service
public class Sum10Calculator {

    public List<OtherInstrumentsMap> sumOtherInstruments (){

        List<OtherInstrumentsMap> list = new ArrayList<>();

       ConcurrentMap<String, List<Instrument>> distinctInstruments = CsvParserService.getInstrumentStream()
               .collect(Collectors.groupingByConcurrent(Instrument::getInstrumentId));

       distinctInstruments.keySet().forEach(key->{

       Double sum = distinctInstruments.get(key).parallelStream()
               .sorted(Comparator.comparing(Instrument::getLocalDate).reversed())
               .limit(10)
               .mapToDouble(Instrument::getInstrumentValue)
               .sum();

           list.add(new OtherInstrumentsMap(key,sum));
       });

       return list;


    }

}