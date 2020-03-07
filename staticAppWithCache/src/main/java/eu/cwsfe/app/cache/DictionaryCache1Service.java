package eu.cwsfe.app.cache;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class DictionaryCache1Service {

    private IMap<String, CacheWithMetadata> cacheMap1;
    private Long cacheLastUpdateInMs;

    public DictionaryCache1Service(HazelcastInstance hazelcastInstance) {
        this.cacheMap1 = hazelcastInstance.getMap("cacheMap1");
        this.cacheLastUpdateInMs = Instant.now().getEpochSecond() * 1000;
//        CompletableFuture.runAsync(() -> {
//            //some optional lazy dictionary initialization?
//        });
    }

    void put(String name, CacheWithMetadata cacheWithMetadata) {
        cacheMap1.put(name, cacheWithMetadata);
        cacheLastUpdateInMs = cacheWithMetadata.lastModified().getEpochSecond() * 1000;
    }

    CacheWithMetadata get(String name) {
        return cacheMap1.get(name);
    }

    public Long getCacheLastUpdateInMs() {
        return cacheLastUpdateInMs;
    }

}
