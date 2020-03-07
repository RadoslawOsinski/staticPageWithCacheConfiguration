package eu.cwsfe.app.cache;

import java.io.Serializable;
import java.time.Instant;

record CacheWithMetadata(String eTag, Instant lastModified, String largeData) implements Serializable {
}
