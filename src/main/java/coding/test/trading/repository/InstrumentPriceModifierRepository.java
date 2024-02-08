package coding.test.trading.repository;

import coding.test.trading.model.InstrumentPriceModifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstrumentPriceModifierRepository extends CrudRepository<InstrumentPriceModifier, Long> {

    Optional<InstrumentPriceModifier> findByName(String name);

}
