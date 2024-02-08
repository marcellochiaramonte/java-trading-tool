package coding.test.trading.service;

import coding.test.trading.model.InstrumentPriceModifier;
import coding.test.trading.repository.InstrumentPriceModifierRepository;
import coding.test.trading.model.InstrumentPriceCachedValue;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;

@NoArgsConstructor
@Service
public class InstrumentModifierCache {

    private static InstrumentModifierCache INSTANCE;

    HashMap<String, InstrumentPriceCachedValue> cache = new HashMap<>();
    @Autowired
    private InstrumentPriceModifierRepository repository;

    public synchronized Double getInstrumentPriceModifier(String instrument) {

        if (cache.containsKey(instrument) && cache.get(instrument).getTimestamp().isAfter(LocalDateTime.now().minusSeconds(2))) {
            // We can still use the cache
//            System.out.println("USING THE CACHE at " + cache.get(instrument).getTimestamp().toString());
            return cache.get(instrument).getValue();
        } else {
            // We need to query the DB
            Optional<InstrumentPriceModifier> modifierOptional = repository.findByName(instrument);
            System.out.println("QUERYING DB for " + instrument);
            if (modifierOptional.isPresent()) {
                // Saving the latest value to the cache with the current timestamp
                cache.put(instrument, new InstrumentPriceCachedValue(LocalDateTime.now(), modifierOptional.get().getMultiplier()));
                return modifierOptional.get().getMultiplier();
            } else {
                // If modifier for instrument was not found, return a neutral multiplier
                return 1.0;
            }
        }
    }
}



