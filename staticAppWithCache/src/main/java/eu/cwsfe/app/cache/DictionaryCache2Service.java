package eu.cwsfe.app.cache;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class DictionaryCache2Service {

    private IMap<String, CacheWithMetadata> cacheMap2;
    private Long cacheLastUpdateInMs;

    public DictionaryCache2Service(HazelcastInstance hazelcastInstance) {
        this.cacheMap2 = hazelcastInstance.getMap("cacheMap2");
        this.cacheLastUpdateInMs = Instant.now().getEpochSecond() * 1000;
//        CompletableFuture.runAsync(() -> {
//            //some optional lazy dictionary initialization?
//        });
    }

    void put(String name, CacheWithMetadata cacheWithMetadata) {
        cacheMap2.put(name, cacheWithMetadata);
        cacheLastUpdateInMs = cacheWithMetadata.lastModified().getEpochSecond() * 1000;
    }

    CacheWithMetadata get(String name) {
        return cacheMap2.get(name);
    }

    public Long getCacheLastUpdateInMs() {
        return cacheLastUpdateInMs;
    }

}
