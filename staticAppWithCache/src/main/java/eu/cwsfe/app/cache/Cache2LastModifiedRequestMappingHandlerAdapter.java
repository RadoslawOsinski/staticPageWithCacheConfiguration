package eu.cwsfe.app.cache;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.servlet.http.HttpServletRequest;

@Component
public class Cache2LastModifiedRequestMappingHandlerAdapter extends RequestMappingHandlerAdapter {

    private final DictionaryCache2Service dictionaryCache2Service;

    public Cache2LastModifiedRequestMappingHandlerAdapter(DictionaryCache2Service dictionaryCache2Service) {
        this.dictionaryCache2Service = dictionaryCache2Service;
    }

    @Override
    protected long getLastModifiedInternal(HttpServletRequest request, HandlerMethod handlerMethod) {
        return dictionaryCache2Service.getCacheLastUpdateInMs();
    }

    @Override
    protected boolean supportsInternal(HandlerMethod handlerMethod) {
        return handlerMethod != null && handlerMethod.hasMethodAnnotation(CachedDictionary2.class);
    }

}
