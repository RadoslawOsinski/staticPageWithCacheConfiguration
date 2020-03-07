package eu.cwsfe.app.cache;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.servlet.http.HttpServletRequest;

@Component
public class Cache1LastModifiedRequestMappingHandlerAdapter extends RequestMappingHandlerAdapter {

    private final DictionaryCache1Service dictionaryCache1Service;

    public Cache1LastModifiedRequestMappingHandlerAdapter(DictionaryCache1Service dictionaryCache1Service) {
        this.dictionaryCache1Service = dictionaryCache1Service;
    }

    @Override
    protected long getLastModifiedInternal(HttpServletRequest request, HandlerMethod handlerMethod) {
        return dictionaryCache1Service.getCacheLastUpdateInMs();
    }

    @Override
    protected boolean supportsInternal(HandlerMethod handlerMethod) {
        return handlerMethod != null && handlerMethod.hasMethodAnnotation(CachedDictionary1.class);
    }

}
