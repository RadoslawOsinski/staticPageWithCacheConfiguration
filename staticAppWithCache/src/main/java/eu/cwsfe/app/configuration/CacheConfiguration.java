package eu.cwsfe.app.configuration;

import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfiguration {

//Testing manual approach to learn how spring and related RFC's works
//    @Bean
//    public FilterRegistrationBean<ShallowEtagHeaderFilter> filterRegistrationBean () {
//        FilterRegistrationBean<ShallowEtagHeaderFilter> registration = new FilterRegistrationBean<>();
//        registration.setFilter(new ShallowEtagHeaderFilter());
//        registration.addUrlPatterns("/**");
//        return registration;
//    }

}
