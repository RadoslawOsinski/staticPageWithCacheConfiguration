package eu.cwsfe.app.page;

import eu.cwsfe.app.configuration.StaticAppWithCacheProperties;
import org.springframework.core.env.Environment;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
public class ConfigurationController {

    private final String applicationUrl;
    private final String environment;

    public ConfigurationController(StaticAppWithCacheProperties staticAppWithCacheProperties, Environment environment) {
        this.applicationUrl = staticAppWithCacheProperties.getApplicationUrl();
        List<String> profiles = new ArrayList<>();
        profiles.addAll(Arrays.asList(environment.getActiveProfiles()));
        profiles.addAll(Arrays.asList(environment.getDefaultProfiles()));
        if (!profiles.isEmpty()) {
            this.environment = profiles.get(0);
        } else {
            this.environment = "dev";
        }
    }

    @GetMapping(value = "/configuration.js", produces = "application/javascript")
    public ResponseEntity<String> configuration() {
        String responseBody = """
                export default class Configuration {
                    
                  constructor() {
                    this.applicationUrl = '%s' 
                    this.environment = '%s'
                  }
                  
                  getConfiguration() {
                      return {
                        applicationUrl: this.applicationUrl,
                        environment: this.environment
                      };
                  }
                }
                """.formatted(applicationUrl, environment);

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(365, TimeUnit.DAYS).cachePrivate().mustRevalidate())
                .body(responseBody);
    }

}
