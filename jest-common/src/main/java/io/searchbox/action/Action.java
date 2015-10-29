package io.searchbox.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.searchbox.client.JestResult;

import java.util.Map;

/**
 * @author Dogukan Sonmez
 */
public interface Action<T extends JestResult> {

    String getURI();

    String getRestMethodName();

    String getData(ObjectMapper gson) throws Exception;

    String getPathToResult();

    Map<String, Object> getHeaders();

    T createNewElasticSearchResult(String responseBody, int statusCode, String reasonPhrase, ObjectMapper mapper);
}
