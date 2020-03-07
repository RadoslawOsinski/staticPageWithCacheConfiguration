package eu.cwsfe.app.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotEmpty;

@Configuration
@ConfigurationProperties("static.app")
public class StaticAppWithCacheProperties {

    @NotEmpty
    private String applicationUrl;

    public String getApplicationUrl() {
        return applicationUrl;
    }

    public void setApplicationUrl(String applicationUrl) {
        this.applicationUrl = applicationUrl;
    }
}
