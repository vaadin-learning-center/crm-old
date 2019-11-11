
package com.vaadin.tutorial.crm.backend.service.restimport;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "seed",
    "results",
    "page",
    "version"
})
public class Info {

    @JsonProperty("seed")
    private String seed;
    @JsonProperty("results")
    private Integer results;
    @JsonProperty("page")
    private Integer page;
    @JsonProperty("version")
    private String version;

    @JsonProperty("seed")
    public String getSeed() {
        return seed;
    }

    @JsonProperty("seed")
    public void setSeed(String seed) {
        this.seed = seed;
    }

    @JsonProperty("results")
    public Integer getResults() {
        return results;
    }

    @JsonProperty("results")
    public void setResults(Integer results) {
        this.results = results;
    }

    @JsonProperty("page")
    public Integer getPage() {
        return page;
    }

    @JsonProperty("page")
    public void setPage(Integer page) {
        this.page = page;
    }

    @JsonProperty("version")
    public String getVersion() {
        return version;
    }

    @JsonProperty("version")
    public void setVersion(String version) {
        this.version = version;
    }

}
