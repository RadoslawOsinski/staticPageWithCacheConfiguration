package eu.cwsfe.app.cache;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import java.time.Instant;

@RestController
public class CacheController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheController.class);

    private final DictionaryCache1Service dictionaryCache1Service;
    private final DictionaryCache2Service dictionaryCache2Service;

    public CacheController(DictionaryCache1Service dictionaryCache1Service,
                           DictionaryCache2Service dictionaryCache2Service
    ) {
        this.dictionaryCache1Service = dictionaryCache1Service;
        this.dictionaryCache2Service = dictionaryCache2Service;
    }

    /**
     * Add to cache helper. GET is used instead of PUT because easier tests in browser
     */
    @GetMapping("/cache/put/{name}/{value}")
    public ResponseEntity<String> putToCache(@PathVariable String name, @PathVariable String value) {
        String etag = createEtagForString(name + value);
        CacheWithMetadata cacheWithMetadata = new CacheWithMetadata(etag, Instant.now(), value);
        dictionaryCache1Service.put(name, cacheWithMetadata);
        LOGGER.info("Put to cache: {}", cacheWithMetadata);
        return ResponseEntity.status(HttpStatus.OK)
                .body(cacheWithMetadata.largeData());
    }

    /**
     * Caching example with update time + eTag approach
     * ETag allows greater cache precision below 1 second.
     */
    @CachedDictionary1
    @GetMapping("/cache/{name}")
    public ResponseEntity<String> getFromCache(@PathVariable String name, ServletWebRequest swr) {
        CacheWithMetadata cacheWithMetadata = dictionaryCache1Service.get(name);
        if (swr.checkNotModified(cacheWithMetadata.eTag(), cacheWithMetadata.lastModified().getEpochSecond() * 1000)) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED)
                    .eTag(cacheWithMetadata.eTag())
                    .build();
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .eTag(cacheWithMetadata.eTag())
                    .body(cacheWithMetadata.largeData());
        }
    }

    /**
     * Add to cache helper. GET is used instead of PUT because easier tests in browser
     */
    @GetMapping("/cache2/put/{name}/{value}")
    public ResponseEntity<String> putToCache2(@PathVariable String name, @PathVariable String value) {
        CacheWithMetadata cacheWithMetadata = new CacheWithMetadata(null, Instant.now(), value);
        dictionaryCache2Service.put(name, cacheWithMetadata);
        LOGGER.info("Put to cache 2: {}", cacheWithMetadata);
        return ResponseEntity.status(HttpStatus.OK)
                .body(cacheWithMetadata.largeData());
    }

    /**
     * Caching example with update time only approach
     */
    @CachedDictionary2
    @GetMapping("/cache2/{name}")
    public ResponseEntity<String> getFromCache2(@PathVariable String name, ServletWebRequest swr) {
        CacheWithMetadata cacheWithMetadata = dictionaryCache2Service.get(name);
        if (swr.checkNotModified(cacheWithMetadata.lastModified().getEpochSecond() * 1000)) {
            return null;
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(cacheWithMetadata.largeData());
        }
    }

    /**
     * MD5 hash is chosen instead of SHA* because we are optimizing response time.
     * Collisions may occur on huge data sets.
     */
    private String createEtagForString(String value) {
//        return "\"0" + DigestUtils.sha256Hex(value) + '"';
        return "\"0" + DigestUtils.md5Hex(value) + '"';
    }

}
