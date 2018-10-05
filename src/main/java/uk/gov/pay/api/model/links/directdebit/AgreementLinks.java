package uk.gov.pay.api.model.links.directdebit;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import uk.gov.pay.api.model.PaymentConnectorResponseLink;
import uk.gov.pay.api.model.generated.Link;
import uk.gov.pay.api.model.generated.PostLink;

import java.util.List;

import static javax.ws.rs.HttpMethod.GET;

@ApiModel(value = "AgreementLinks", description = "self and next links of an Agreement")
public class AgreementLinks {

    private static final String SELF = "self";
    private static final String NEXT_URL = "next_url";
    private static final String NEXT_URL_POST = "next_url_post";

    @JsonProperty(SELF)
    private Link self;

    @JsonProperty(NEXT_URL)
    private Link nextUrl;

    @JsonProperty(NEXT_URL_POST)
    private PostLink nextUrlPost;

    @ApiModelProperty(value = SELF, dataType = "uk.gov.pay.api.model.links.Link")
    public Link getSelf() {
        return self;
    }

    @ApiModelProperty(value = NEXT_URL, dataType = "uk.gov.pay.api.model.links.Link")
    public Link getNextUrl() {
        return nextUrl;
    }

    @ApiModelProperty(value = NEXT_URL_POST, dataType = "uk.gov.pay.api.model.links.PostLink")
    public PostLink getNextUrlPost() {
        return nextUrlPost;
    }

    public void addKnownLinksValueOf(List<PaymentConnectorResponseLink> chargeLinks) {
        addNextUrlIfPresent(chargeLinks);
        addNextUrlPostIfPresent(chargeLinks);
    }

    private void addNextUrlPostIfPresent(List<PaymentConnectorResponseLink> chargeLinks) {
        chargeLinks.stream()
                .filter(link -> NEXT_URL_POST.equals(link.getRel()))
                .findFirst()
                .ifPresent(chargeLink -> 
                        this.nextUrlPost = 
                                new PostLink().href(chargeLink.getHref()).method(chargeLink.getMethod()).type(chargeLink.getType()).params(chargeLink.getParams()));
    }

    private void addNextUrlIfPresent(List<PaymentConnectorResponseLink> chargeLinks) {
        chargeLinks.stream()
                .filter(link -> NEXT_URL.equals(link.getRel()))
                .findFirst()
                .ifPresent(chargeLink -> this.nextUrl = new Link().href(chargeLink.getHref()).method(chargeLink.getMethod()));
    }

    public void addSelf(String href) {
        this.self = new Link().href(href).method(GET);
    }
}
