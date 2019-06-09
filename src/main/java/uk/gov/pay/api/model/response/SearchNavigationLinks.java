package uk.gov.pay.api.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

@ApiModel(value = "SearchNavigationLinks", description = "Links to navigate through pages")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchNavigationLinks {

    @JsonProperty(value = "self")
    private Link self;

    @JsonProperty(value = "first_page")
    private Link firstPage;

    @JsonProperty(value = "last_page")
    private Link lastPage;

    @JsonProperty(value = "prev_page")
    private Link prevPage;

    @JsonProperty(value = "next_page")
    private Link nextPage;

    public SearchNavigationLinks() {
    }

    public SearchNavigationLinks(Link self, Link firstPage, Link lastPage, Link prevPage, Link nextPage) {
        this.self = self;
        this.firstPage = firstPage;
        this.lastPage = lastPage;
        this.prevPage = prevPage;
        this.nextPage = nextPage;
    }

    public Link getSelf() {
        return self;
    }

    public Link getFirstPage() {
        return firstPage;
    }

    public Link getLastPage() {
        return lastPage;
    }

    public Link getPrevPage() {
        return prevPage;
    }

    public Link getNextPage() {
        return nextPage;
    }

    public SearchNavigationLinks withSelfLink(String href) {
        this.self = new Link(href);
        return this;
    }
    public SearchNavigationLinks withPrevLink(String href) {
        this.prevPage = new Link(href);
        return this;
    }
    public SearchNavigationLinks withNextLink(String href) {
        this.nextPage = new Link(href);
        return this;
    }
    public SearchNavigationLinks withFirstLink(String href) {
        this.firstPage = new Link(href);
        return this;
    }
    public SearchNavigationLinks withLastLink(String href) {
        this.lastPage = new Link(href);
        return this;
    }
}
