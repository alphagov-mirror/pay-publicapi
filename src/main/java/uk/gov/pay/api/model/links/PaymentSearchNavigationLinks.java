package uk.gov.pay.api.model.links;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import uk.gov.pay.api.model.generated.Link;

@ApiModel(value = "PaymentSearchNavigationLinks", description = "Links to navigate through pages")
public class PaymentSearchNavigationLinks {

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

    public PaymentSearchNavigationLinks withSelfLink(String href) {
        this.self = new Link().href(href);
        return this;
    }
    public PaymentSearchNavigationLinks withPrevLink(String href) {
        this.prevPage = new Link().href(href);
        return this;
    }
    public PaymentSearchNavigationLinks withNextLink(String href) {
        this.nextPage = new Link().href(href);
        return this;
    }
    public PaymentSearchNavigationLinks withFirstLink(String href) {
        this.firstPage = new Link().href(href);
        return this;
    }
    public PaymentSearchNavigationLinks withLastLink(String href) {
        this.lastPage = new Link().href(href);
        return this;
    }
}
