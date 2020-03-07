package eu.cwsfe.app;

import eu.cwsfe.app.configuration.StaticAppWithCacheProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(StaticAppWithCacheProperties.class)
public class StaticApp {

    public static void main(String[] args) {
        SpringApplication.run(StaticApp.class, args);
    }

}
