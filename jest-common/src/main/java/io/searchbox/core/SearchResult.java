package io.searchbox.core;

import io.searchbox.client.JestResult;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.*;

/**
 * @author cihat keser
 */
public class SearchResult<S, E, A> extends JestResult<SearchResult.SearchResponse<S, E, A>> {

    public static final String EXPLANATION_KEY = "_explanation";
    public static final String HIGHLIGHT_KEY = "highlight";
    public static final String SORT_KEY = "sort";
    public static final String INDEX_KEY = "_index";
    public static final String TYPE_KEY = "_type";
    public static final String SCORE_KEY = "_score";

    public SearchResult(SearchResult<S, E, A> searchResult) {
        super(searchResult);
    }

    public SearchResult(ObjectMapper mapper) {
        super(mapper);
    }

    public Hit<S, E> getFirstHit() {
        return  response.hits.hits.isEmpty() ? null : response.hits.hits.get(0);
    }

    // NOTE: Facets dropped for this example.

    public A getAggregations() {
    	return response.aggregations;
    }
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class SearchResponse<S, E, A> {

        @JsonProperty("took")
        public Integer took;
        @JsonProperty("timed_out")
        public Boolean timedOut;
        @JsonProperty("_shards")
        public io.searchbox.core.Shards Shards;
        @JsonProperty("hits")
        public Hits<S, E> hits;
        @JsonProperty("aggregations")
        public A aggregations;
        @JsonIgnore
        public Map<String, Object> additionalProperties = new HashMap<String, Object>();
        
        @JsonAnyGetter
        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        @JsonAnySetter
        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }
        
        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(took).append(timedOut).append(Shards).append(hits).append(additionalProperties).toHashCode();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            if ((other instanceof SearchResponse) == false) {
                return false;
            }
            SearchResponse<?, ?, ?> rhs = ((SearchResponse<?, ?, ?>) other);
            return new EqualsBuilder().append(took, rhs.took).append(timedOut, rhs.timedOut).append(Shards, rhs.Shards).append(hits, rhs.hits).append(additionalProperties, rhs.additionalProperties).isEquals();
        }

    }
    
    public static class Hits<S, E> {
        @JsonProperty("total")
        private Integer total;
        @JsonProperty("max_score")
        private Integer maxScore;
        @JsonProperty("hits")
        private List<Hit<S, E>> hits = new ArrayList<Hit<S, E>>();
        @JsonIgnore
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this);
        }

        @JsonAnyGetter
        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        @JsonAnySetter
        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(total).append(maxScore).append(hits).append(additionalProperties).toHashCode();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            if ((other instanceof Hits) == false) {
                return false;
            }
            Hits<?, ?> rhs = ((Hits<?, ?>) other);
            return new EqualsBuilder().append(total, rhs.total).append(maxScore, rhs.maxScore).append(hits, rhs.hits).append(additionalProperties, rhs.additionalProperties).isEquals();
        }        
    }

    /**
     * Non immutable class, for this example.
     *
     * @param <S> type of source
     * @param <E> type of explanation
     * @author cihat keser
     */
    public static class Hit<S, E> {

    	@JsonProperty("_source")
        public S source;
    	@JsonProperty(EXPLANATION_KEY)
        public E explanation;
    	@JsonProperty(HIGHLIGHT_KEY)
        public Map<String, List<String>> highlight;
    	@JsonProperty(SORT_KEY)
        public List<String> sort;
    	@JsonProperty(INDEX_KEY)
        public String index;
    	@JsonProperty(TYPE_KEY)
        public String type;
    	@JsonProperty(SCORE_KEY)
        public Double score;

        public Hit() {
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder()
                    .append(source)
                    .append(explanation)
                    .append(highlight)
                    .append(sort)
                    .toHashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            if (obj.getClass() != getClass()) {
                return false;
            }

            Hit<?, ?> rhs = (Hit<?, ?>) obj;
            return new EqualsBuilder()
                    .append(source, rhs.source)
                    .append(explanation, rhs.explanation)
                    .append(highlight, rhs.highlight)
                    .append(sort, rhs.sort)
                    .isEquals();
        }
    }

}
