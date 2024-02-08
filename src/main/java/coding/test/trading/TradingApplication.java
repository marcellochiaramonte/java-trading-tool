package coding.test.trading;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TradingApplication {

	public static void main(String[] args) {

//		List<Instrument> dummyInstruments = List.of (
//				new Instrument("EURUSD", LocalDate.parse("2010-05-12"),1.04234)
//		);
//
//		System.out.println(dummyInstruments);

		SpringApplication.run(TradingApplication.class, args);

	}

}
